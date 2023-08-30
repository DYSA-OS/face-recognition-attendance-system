//package FaceAuto.AttendanceApp.web;
//
//import FaceAuto.AttendanceApp.domain.Course;
//import FaceAuto.AttendanceApp.domain.Student;
//import FaceAuto.AttendanceApp.service.ProfessorService;
//import FaceAuto.AttendanceApp.service.StudentService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequiredArgsConstructor
//
//public class CourseController {
//
//    private final ProfessorService professorService;
//    private final StudentService studentService;
//
//    @GetMapping("/professor/{professorId}/add-course")
//    public String showAddCourseForm(Model model) {
//        model.addAttribute("courseForm", new CourseForm());
//        return "add-course";
//    }
//
//    @PostMapping("/professor/{professorId}/add-course")
//    public String addCourse(@Valid @ModelAttribute("courseForm") CourseForm courseForm,
//                            BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//            return "redirect:/professor/add-course";
//        }
//
//        Course course = new Course();
//        course.setCourseName(courseForm.getCourseName());
//
//        String[] studentIds = courseForm.getStudentIds().split(",");
//        for (String studentId : studentIds) {
//            Student student = studentService.findOne(Long.parseLong(studentId));
////            if (student != null) {
////                course.addStudent(student);
////            }
//        }
//
//        Long professorId = 1L;
//        professorService.addCoursesToProfessor(professorId, course);
//
//        return "redirect:/professor/" + professorId; // 교수님 페이지로 리다이렉트
//    }
//}
