package FaceAuto.AttendanceApp.web;

import FaceAuto.AttendanceApp.domain.*;
import FaceAuto.AttendanceApp.service.*;
import com.github.sarxos.webcam.Webcam;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import java.io.ByteArrayOutputStream;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/professor")
@Slf4j
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private AttendanceService attendanceService;


    /**
     * 교수님 가입 : 폼데이터로 처리
     */
    @PostMapping("/sign-up")
    public String signUpProfessor(@Valid ProfessorForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러가 있는 경우 에러 정보를 저장하고 리다이렉트
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/professor/sign-up";
        }

        Professor professor = new Professor();
        professor.setName(form.getName());
        professor.setUniv(form.getUniv());
        professor.setEmployeeId(form.getEmployeeId());
        professor.setEmail(form.getEmail());

        String profilePicture = null;
        MultipartFile profilePictureFile = form.getProfilePictureFile();
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            profilePicture = fileStorageService.storeFile(profilePictureFile, professor.getName()); //이름으로 저장
            professor.setProfilePicture(profilePicture);
        }

        Long professorId = professorService.join(professor);

        return "redirect:/professor/" + professorId;
//        return "data/" + profilePicture; // 업로드한 파일 이름을 클라이언트로 리턴
    }
    @GetMapping(value = "/sign-up")
    public String showSignUpForm(Model model) {
        // 폼 데이터와 에러 정보를 모델에 추가하여 리다이렉트된 페이지에서 활용
        List<String> universities = Arrays.asList("군산대학교", "전북대학교", "전주교육대학교", "전주대학교", "우석대학교", "목포대학교", "순천대학교", "전남대학교", "광주교육대학교", "조선대학교", "호남대학교"); // 대학교 목록 설정
        model.addAttribute("universities", universities); // 대학교 목록 모델에 추가
        model.addAttribute("form", new ProfessorForm());
        return "professor/sign-up";
    }


    /**
     * 교수 디테일샷
     */
    @GetMapping(value = "/{professorId}")
    public String showProfessorDetails(@PathVariable Long professorId, Model model) {
        Professor professor = professorService.findOne(professorId);
        if (professor == null) {
            return "redirect:/"; // 교수가 존재하지 않으면 홈으로 리다이렉트
        }
        List<Course> courses = professor.getCourses();
        model.addAttribute("professor", professor);
        model.addAttribute("courses", courses);
        return "professor/professor-details";
    }


    /**
     * 과목 생성
     */
    @GetMapping("/{professorId}/add-course")
    public String showAddCourseForm(@PathVariable Long professorId, Model model) {
        Professor professor = professorService.findOne(professorId); // 교수 정보 가져오기

        model.addAttribute("professor", professor); // 모델에 교수 객체 추가
        model.addAttribute("courseForm", new CourseForm());
        return "professor/add-course";
    }

    @PostMapping("/{professorId}/add-course")
    public String addCourse(@PathVariable Long professorId, @Valid @ModelAttribute("courseForm") CourseForm courseForm,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/professor/" + professorId + "/add-course";
        }

        Course course = new Course();
        course.setCourseName(courseForm.getCourseName());

        List<String> sIds = courseForm.getSIds();
        List<Student> students = new ArrayList<>();

        for (String sId : sIds) {
            Student student = studentService.findBySId(sId);
            students.add(student);
        }

        //과목에 학생들을 추가(연관성)
        courseService.addCourseWithStudents(course, students);

        //교수님 페이지에 과목을 추가(연관성)
        professorService.addCoursesToProfessor(professorId, course);

        return "redirect:/professor/" + professorId; // 교수님 페이지로 리다이렉트
    }

    /**
     * 과목 상세 페이지
     */
    @GetMapping("/{professorId}/course/{courseId}")
    public String showCourseDetails(@PathVariable Long professorId, @PathVariable Long courseId, Model model) {
        Course course = professorService.findCourseById(courseId);
        Professor professor = professorService.findOne(professorId); // 교수 정보 가져오기

        if (course == null || !course.getProfessor().getId().equals(professorId)) {
            return "redirect:/professor/" + professorId;
        }

        List<Student> students = courseService.findStudentsByCourseId(courseId);

        model.addAttribute("professor", professor); // 모델에 교수 객체 추가
        model.addAttribute("course", course);
        model.addAttribute("students", students);

        return "professor/course-details";
    }

    /**
     * 성적 입력
     */
    @GetMapping("/{professorId}/course/{courseId}/grade")
    public String showGradeForm(@PathVariable Long professorId, @PathVariable Long courseId, Model model) {
        Professor professor = professorService.findOne(professorId); // 교수 정보 가져오기
        Course course = professorService.findCourseById(courseId);

        if (course == null || !course.getProfessor().getId().equals(professorId)) {
            return "redirect:/professor/" + professorId;
        }

        List<Student> students = courseService.findStudentsByCourseId(courseId);
        Map<Long, String> studentGradesMap = new HashMap<>(); //학생별 성적을 담을 Map 초기화

        for (Student student : students) {
            studentGradesMap.put(student.getId(), ""); //학생별 빈 성적 값으로 초기화
        }

        model.addAttribute("professor", professor); // 모델에 교수 객체 추가
        model.addAttribute("course", course);
        model.addAttribute("students", students);
        model.addAttribute("studentGradesMap", studentGradesMap);
        System.out.println("studentGradesMap = " + studentGradesMap);

        return "professor/grade-form";
    }
    @PostMapping("/{professorId}/course/{courseId}/grade")
    public String saveGrades(@PathVariable Long professorId, @PathVariable Long courseId, @RequestParam Map<Long, String> gradeMap) {
        System.out.println("gradeMap = " + gradeMap);
        System.out.println("gradeMap.entrySet() = " + gradeMap.entrySet());
        for (Map.Entry<Long, String> entry : gradeMap.entrySet()) {
            System.out.println("entry = " + entry);
            System.out.println("entry.getKey() = " + entry.getKey());
            System.out.println("entry.getValue() = " + entry.getValue());
//            Long studentId = entry.getKey();
            Long studentId = Long.parseLong(String.valueOf(entry.getKey()));
            String grade = entry.getValue();

            //점수 저장(서비스 코드에 트랙잭션 필수)
            professorService.gradeStudent(professorId, studentId, courseId, grade);
//            studentCourseService.saveStudentGrade(studentId, courseId, grade);
        }

        return "redirect:/professor/" + professorId + "/course/" + courseId;
    }


    /**
     * 출석 체크
     */
    @GetMapping("/{professorId}/course/{courseId}/mark-attendance")
    public String showAttendancePage(@PathVariable Long professorId, @PathVariable Long courseId, Model model) {
        Professor professor = professorService.findOne(professorId);
        Course course = courseService.findOne(courseId);

        List<Student> students = courseService.findStudentsByCourseId(courseId);

        model.addAttribute("professor", professor);
        model.addAttribute("course", course);
        model.addAttribute("students", students);

        return "professor/attendance"; // HTML 파일명
    }
    @PostMapping("/{professorId}/course/{courseId}/mark-attendance")
    public String markAttendance(@PathVariable Long professorId, @PathVariable Long courseId, @RequestBody List<AttendanceRequest> jsonDataList) throws IOException {
        List<Student> students = courseService.findStudentsByCourseId(courseId);
        System.out.println("jsonDataList = " + jsonDataList);
        String name1 = jsonDataList.get(0).getName();
        String timestamp1 = jsonDataList.get(0).getTimestamp();
        System.out.println("name1 = " + name1);
        System.out.println("timestamp1 = " + timestamp1);
        if (jsonDataList != null) {
            System.out.println("if 1번째 통과");
            System.out.println("name2 = " + name1);
            System.out.println("timestamp2 = " + timestamp1);
            for (AttendanceRequest jsonData : jsonDataList) {
                String name = jsonData.getName();
                String timestamp = jsonData.getTimestamp();

                log.info("studentName={}, timeStamp={}", name, timestamp);

                Student student3 = studentService.findByName(name);
                System.out.println("student3 = " + student3);

                if (studentService.findByName(name) != null){
                    System.out.println("if 2번째 통과");
                    Student student = studentService.findByName(name);
                    System.out.println("student 학생 있냐고= " + student.getName());
                    //출석 저장
                    professorService.markStudentAttendance(professorId, student.getId(), courseId, timestamp, AttendanceStatus.PRESENT);
                    System.out.println("save success!");

                    for (Student student1 : students) {
                        System.out.println("for문을 드뎌 통과");
                        String name2 = student1.getName();
                        System.out.println("name");
                        if (name == name2) { //과목을 수강하는 학생들 중에 출석이 완료된 학생은 리스트에서 삭제하기!
                            System.out.println("if 3번째 통과");
                            System.out.println("name = " + name);
                            System.out.println("student1.getName() = " + student1.getName());
                            
                            students.remove(student1);
                            System.out.println("제대로 삭제가 되었니ㅠㅠ students = " + students);
                            break;
                        }
                    }
                }
                else{
                    System.out.println("no name student...pass");
                }
            }

            for(Student student : students){ //출석 안한 학생은 ABSENT 처리하기
                professorService.markStudentAttendance(professorId, student.getId(), courseId, "-", AttendanceStatus.ABSENT);
            }
        }

        return "redirect:/professor/" + professorId + "/course/" + courseId + "/attendance-result";
    }

    /**
     * 출석결과 조회
     */
    @GetMapping("/{professorId}/course/{courseId}/attendance-result")
    public String showAttendanceResult(@PathVariable Long professorId, @PathVariable Long courseId, Model model) {
        // Flash 속성으로부터 students 가져오기
//        List<Student> AbsentStudents = (List<Student>) model.getAttribute("students");
//        //결석한 학생
//        model.addAttribute("AbsentStudents", AbsentStudents);
        Professor professor = professorService.findOne(professorId);
        List<Attendance> attendances = attendanceService.findAttendanceByCourseId(courseId);

        Course course = courseService.findOne(courseId);
        List<Student> students = courseService.findStudentsByCourseId(courseId);

        model.addAttribute("professor", professor);
        model.addAttribute("attendances", attendances);
        model.addAttribute("course", course);
        model.addAttribute("students", students);

        return "professor/attendance-result";
    }


//    String input_str = "r"; // 테스트를 위해 'r'을 사용
//        if (input_str.equals("r")) {
//            // 웹캠 캡처 로직 추가
//            byte[] capturedImage = captureWebcamImage();
//
//            // 이미지 전송 및 결과 수신
//            JSONObject jsonResponse = sendImageToPythonServer(capturedImage);
//            if (jsonResponse != null) {
//                System.out.println("Received JSON response: " + jsonResponse);
//                String recognizedName = jsonResponse.getString("name");
//                String timestamp = jsonResponse.getString("timestamp");
//
//                // Student 객체 조회 (recognizedName으로 학생 정보 조회 필요)
//                Student student = studentService.findByName(recognizedName);
//
//                // 출석 기록
//                professorService.markStudentAttendance(professorId, student.getId(), courseId, LocalDate.parse(timestamp), request.getStatus());
//            } else {
//                System.out.println("Failed to receive JSON response");
//            }
//        }

    private JSONObject sendImageToPythonServer(byte[] imageBytes) {
        try {
            String pythonServerUrl = "http://192.168.0.25:5001"; // Python 서버 주소
            HttpPost httpPost = new HttpPost(pythonServerUrl + "/process_attendance");
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // 이미지 바이트 배열을 요청 본문에 설정
            ByteArrayEntity entity = new ByteArrayEntity(imageBytes);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private byte[] captureWebcamImage() throws IOException {
        // 웹캠 캡처 로직 추가
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage capturedImage = webcam.getImage();

        // 이미지를 바이트 배열로 변환
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(capturedImage, "jpg", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        webcam.close();

        return imageBytes;
    }

}
