package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.*;
import FaceAuto.AttendanceApp.repository.AttendanceRepository;
import FaceAuto.AttendanceApp.repository.CourseRepository;
import FaceAuto.AttendanceApp.repository.ProfessorRepository;
import FaceAuto.AttendanceApp.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProfessorServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    //테스트 완룡
//    @Test
//    @Transactional
//    public void testMarkStudentAttendance() {
//        System.out.println("Test: Mark Student Attendance");
//
//        Professor professor = createProfessor();
//
//        Student student = createStudent();
//
//        Course course = createCourse(professor);
//
//        LocalDate attendanceDate = LocalDate.of(2023, 8, 10);
//        AttendanceStatus status = AttendanceStatus.PRESENT;
//
//        professorService.markStudentAttendance(professor.getId(), student.getId(), course.getId(), attendanceDate, status);
//        System.out.println("Marked Attendance for Student: " + student.getName() + " in Course: " + course.getCourseName() + " on " + attendanceDate);
//
//        Attendance attendance = attendanceRepository.findByStudentIdAndCourseIdAndDate(student.getId(), course.getId(), attendanceDate);
//
//        assertNotNull(attendance);
//        assertEquals(student.getId(), attendance.getStudent().getId());
//        assertEquals(course.getId(), attendance.getCourse().getId());
//        assertEquals(attendanceDate, attendance.getDate());
//        assertEquals(status, attendance.getStatus());
//    }

    private Professor createProfessor() {
        Professor professor = new Professor();
        professor.setName("Kim");
        professor.setEmail("professor@example.com");
        professor.setEmployeeId("12345"); // 교번 설정
        professor.setProfilePicture("professor.jpg"); // 본인사진 파일명 설정

        return professorRepository.save(professor);
    }

    private Student createStudent() {
        Student student = new Student();
        student.setName("Park");
        student.setEmail("student@example.com");
        student.setSId("20230001"); // 학번 설정
        student.setProfilePicture("student.jpg"); // 본인사진 파일명 설정
        // ... 다른 필드 설정
        return studentRepository.save(student);
    }

    private Course createCourse(Professor professor) {
        Course course = new Course();
        course.setCourseName("algorithm");
        course.setProfessor(professor); // Pass the professor here
        // ... 다른 필드 설정
        return courseRepository.save(course);
    }

}