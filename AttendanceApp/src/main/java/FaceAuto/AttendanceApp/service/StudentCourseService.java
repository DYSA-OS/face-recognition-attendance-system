package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.Course;
import FaceAuto.AttendanceApp.domain.Grade;
import FaceAuto.AttendanceApp.domain.Student;
import FaceAuto.AttendanceApp.domain.StudentCourse;
import FaceAuto.AttendanceApp.repository.CourseRepository;
import FaceAuto.AttendanceApp.repository.ProfessorRepository;
import FaceAuto.AttendanceApp.repository.StudentCourseRepository;
import FaceAuto.AttendanceApp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentCourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    @Transactional
    public void saveStudentGrade(Long studentId, Long courseId, String grade) {
        Student student = studentRepository.findOne(studentId);
        Course course = courseRepository.findOne(courseId);

        // 성적을 저장하는 로직 (예시로 성적을 student와 course 객체에 저장하는 것으로 가정)
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
//        studentCourse.setGrade(grade);

        studentCourseRepository.save(studentCourse);
    }
}
