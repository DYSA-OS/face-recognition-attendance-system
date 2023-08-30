package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.Course;
import FaceAuto.AttendanceApp.domain.Student;
import FaceAuto.AttendanceApp.domain.StudentCourse;
import FaceAuto.AttendanceApp.repository.CourseRepository;
import FaceAuto.AttendanceApp.repository.StudentCourseRepository;
import FaceAuto.AttendanceApp.repository.StudentRepository;
import FaceAuto.AttendanceApp.service.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CourseServiceTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Test
    @Transactional
    @Rollback
    public void testFindStudentsByCourseId() {
        // Given
        Course course = new Course();
        Student student1 = new Student();
        student1.setName("학생 1");
        Student student2 = new Student();
        student2.setName("학생 2");

        courseRepository.save(course);
        studentRepository.save(student1);
        studentRepository.save(student2);

        StudentCourse studentCourse1 = new StudentCourse();
        studentCourse1.setStudent(student1);
        studentCourse1.setCourse(course);
        studentCourseRepository.save(studentCourse1);

        StudentCourse studentCourse2 = new StudentCourse();
        studentCourse2.setStudent(student2);
        studentCourse2.setCourse(course);
        studentCourseRepository.save(studentCourse2);

        // When
        List<Student> students = courseService.findStudentsByCourseId(course.getId());

        // Then
        assertEquals(2, students.size());
        assertEquals("학생 1", students.get(0).getName());
        assertEquals("학생 2", students.get(1).getName());
    }

    @Test
    @Transactional
    @Rollback
    public void testAddCourseWithStudents() {
        // Given
        Course course = new Course();
        course.setCourseName("Test Course"); //과목 추가

        Student student1 = new Student();
        student1.setName("Student 1"); //학생 1 추가

        Student student2 = new Student();
        student2.setName("Student 2"); //학생 2 추가

        studentService.join(student1); //학생들 가입ing (필수로 가입하는 코드가 있어야함! 안그러면 db에 없으니까ㅠ)
        studentService.join(student2);

        List<Student> students = new ArrayList<>(); //학생들 리스트 만들기
        students.add(student1);
        students.add(student2);

        // When
        courseService.addCourseWithStudents(course, students); //여기에 과목을 db에 저장하는 코드도 포함되어 있음!

        // Then
        List<Student> studentsInCourse = studentCourseRepository.findStudentsByCourseId(course.getId());
        assertThat(studentsInCourse).hasSize(2);
        assertThat(studentsInCourse).contains(student1, student2);
    }

}
