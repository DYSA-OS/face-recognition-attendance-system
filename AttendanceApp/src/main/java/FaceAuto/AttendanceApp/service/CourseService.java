package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.Course;
import FaceAuto.AttendanceApp.domain.Student;
import FaceAuto.AttendanceApp.domain.StudentCourse;
import FaceAuto.AttendanceApp.repository.CourseRepository;
import FaceAuto.AttendanceApp.repository.StudentCourseRepository;
import FaceAuto.AttendanceApp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    /**
     * 전체 과목 조회
     */
    public List<Course> findCourses() {
        return courseRepository.findAll();
    }

    /**
     * 해당 과목을 듣는 학생 전체 조회하기
     */
    public List<Student> findStudentsByCourseId(Long courseId) {
        return studentCourseRepository.findStudentsByCourseId(courseId);
    }
//    public List<Student> findStudentsByCourseId(Long courseId){
//        Course course = courseRepository.findOne(courseId);
//        List<Student> students = new ArrayList<>();
//        for (StudentCourse studentCourse : course.getStudentCourses()) {
//            students.add(studentCourse.getStudent());
//        }
//        return students;
//    }

    /**
     * id로 과목 찾기
     */
    public Course findOne(Long id) {
        return courseRepository.findOne(id);
    }

    /**
     * 강의와 해당 강의를 수강하는 학생들간의 관계를 설정 및 저장
     */
    @Transactional
    public void addCourseWithStudents(Course course, List<Student> students) {
        Course savedCourse = courseRepository.save(course); //과목 저장
        log.info("savedCourse.getId() = " + savedCourse.getId());

        for (Student student : students) {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStudent(student);
            studentCourse.setCourse(savedCourse);
            studentCourseRepository.save(studentCourse);
        }

    }
}
