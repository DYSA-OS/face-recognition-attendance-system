//package FaceAuto.AttendanceApp.web;
//
//import FaceAuto.AttendanceApp.domain.Course;
//import FaceAuto.AttendanceApp.domain.Professor;
//import FaceAuto.AttendanceApp.domain.Student;
//import FaceAuto.AttendanceApp.repository.CourseRepository;
//import FaceAuto.AttendanceApp.repository.StudentCourseRepository;
//import FaceAuto.AttendanceApp.service.CourseService;
//import FaceAuto.AttendanceApp.service.ProfessorService;
//import FaceAuto.AttendanceApp.service.StudentService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.Errors;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class ProfessorControllerTest {
//
//    @Autowired
//    private ProfessorController professorController;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private ProfessorService professorService;
//
//    @Autowired
//    private StudentService studentService;
//
//    @Autowired
//    private StudentCourseRepository studentCourseRepository;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Test
//    @Rollback
//    void testAddCourse() {
//        // Given
//        Professor professor = new Professor();
//        professor.setName("Test Professor");
//        professor.setEmail("test@example.com");
//        Long professorId = professorService.join(professor); //테스트용 교수님 가입 시키고
//
//        CourseForm courseForm = new CourseForm();
//        courseForm.setCourseName("Test Course");
//        courseForm.setSIds(Collections.singletonList("S12345")); // 학번 한개로 테스트
//
//        // Mock the HttpServletRequest and HttpServletResponse
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        MockHttpServletResponse response = new MockHttpServletResponse();
//
//        // Set form data and other required attributes
//        request.setParameter("courseName", courseForm.getCourseName());
//        request.setParameter("sIds", courseForm.getSIds().toArray(new String[0]));
//
//        // When
//        String redirect = professorController.addCourse(professorId, courseForm, new Errors() {}, request, response);
//
//        // Then
//        assertThat(redirect).isEqualTo("redirect:/professor/" + professorId);
//
//        List<Course> courses = courseService.findCourses();
//        assertThat(courses).hasSize(1);
//
//        Course addedCourse = courses.get(0);
//        assertThat(addedCourse.getCourseName()).isEqualTo("Test Course");
//
//        List<Student> studentsInCourse = studentCourseRepository.findStudentsByCourseId(addedCourse.getId());
//        assertThat(studentsInCourse).hasSize(1); // Assuming 1 student with the given student ID
//    }
//
//}