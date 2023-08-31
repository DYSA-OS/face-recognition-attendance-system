package FaceAuto.AttendanceApp.web;

import FaceAuto.AttendanceApp.domain.Attendance;
import FaceAuto.AttendanceApp.domain.Course;
import FaceAuto.AttendanceApp.domain.Grade;
import FaceAuto.AttendanceApp.domain.Student;
import FaceAuto.AttendanceApp.service.CourseService;
import FaceAuto.AttendanceApp.service.FileStorageService;
import FaceAuto.AttendanceApp.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/student")
@CrossOrigin(origins = "*")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 학생 가입
     */
    @PostMapping("/sign-up")
    public String signUpStudent(@Valid StudentForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            //유효성 검사 에러가 있으면 에러 정보 저장하고 리다이렉트 시킴
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/student/sign-up";
        }

        Student student = new Student();
        student.setName(form.getName());
        student.setUniv(form.getUniv());
        student.setMajor(form.getMajor());
        student.setSId(form.getSId());
        student.setEmail(form.getEmail());
        //사진 추가
        MultipartFile profilePictureFile = form.getProfilePictureFile();
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            String profilePicture = fileStorageService.storeFile(profilePictureFile, student.getName()); //이름으로 저장
            student.setProfilePicture(profilePicture);
        }

        Long studentId = studentService.join(student);
        return "redirect:/student/" + studentId;

    }
    @GetMapping("/sign-up")
    public String SingUpStudent(Model model) {
        List<String> universities = Arrays.asList("군산대학교", "전북대학교", "전주교육대학교", "전주대학교", "우석대학교", "목포대학교", "순천대학교", "전남대학교", "광주교육대학교", "조선대학교", "호남대학교"); // 대학교 목록 설정
        model.addAttribute("universities", universities); // 대학교 목록 모델에 추가
        model.addAttribute("form", new StudentForm());
        return "student/sign-up";
    }

    /**
     * 학생 디테일샷
     */
    @GetMapping("/{studentId}")
    public String showStudentDetails(@PathVariable Long studentId, Model model) {
        Student student = studentService.findOne(studentId);
        if (student == null) {
            return "redirect:/"; // 학생이 존재하지 않으면 홈으로 리다이렉트
        }
        List<Course> registeredCourses = studentService.findCoursesByStudentId(studentId);

        model.addAttribute("student", student);
        model.addAttribute("registeredCourses", registeredCourses);
//        System.out.println("registeredCourses. = " + registeredCourses.get(0).getProfessor().getName());
        return "student/student-details";
    }

    /**
     * 과목 들어가서 성적 + 출결 상황 보기
     */
    @GetMapping("/{studentId}/course/{courseId}")
    public String showStudentGradesAndAttendance(@PathVariable Long studentId, @PathVariable Long courseId, Model model) {
        Student student = studentService.findOne(studentId);
        Course course = courseService.findOne(courseId);
//        Grade grade = null;

        // 학생의 과목 점수 조회
        Grade grade = studentService.findStudentGrade(studentId, courseId); //이거 한 학생의 전체 과목 점수 리스트임;;

        // 학생의 출결 기록 조회
        List<Attendance> attendances = studentService.findStudentAttendancesForCourse(studentId, courseId);

        model.addAttribute("student", student);
        model.addAttribute("course", course);
        model.addAttribute("grade", grade);
        model.addAttribute("attendances", attendances);

        return "student/student-grades-attendances";
    }


}
