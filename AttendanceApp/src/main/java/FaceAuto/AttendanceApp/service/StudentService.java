package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.*;
import FaceAuto.AttendanceApp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true) //영속성 컨텍스트, 데이터 변경이 없는 읽기 전용 메서드
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;
    private final AttendanceRepository attendanceRepository;

    /**
     * 학생 등록
     */
    @Transactional //db에 등록!
    public Long join(Student student) {
        studentRepository.save(student);
        return student.getId();
    }

    /**
     * 전체 학생 조회
     */
    public List<Student> findStudents() {
        return studentRepository.findAll();
    }

    /**
     * id로 학생 찾기
     */
    public Student findOne(Long id) {
        return studentRepository.findOne(id);
    }

    /**
     * 학번(sId)로 학생 찾기
     */
    public Student findBySId(String sId) {
        return studentRepository.findBySId(sId);
    }

    /**
     * 이름으로 학생 찾기
     */
    public Student findByName(String name) {
        return studentRepository.findByName(name);
    }

    /**
     * 학생 아이디로 수강하는 전체 과목 찾기
     */
    public List<Course> findCoursesByStudentId(Long studentId) {
        return studentCourseRepository.findCoursesByStudentId(studentId);
    }

    /**
     * 전체 성적 가져오기
     */
    public List<Grade> findStudentGrades(Long studentId) {
        //학생 찾기
        Student student = studentRepository.findOne(studentId);
        //학생 한명이 듣는 여러개의 강의 조회하기
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(student);

        //여러개의 강의에서 그 학생의 성적을 알아내고, 리스트에 넣어 저장하기
        List<Grade> grades = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            grades.add(gradeRepository.findByStudentCourse(studentCourse));
        }


        return grades;
    }

    /**
     * 과목 당 성적 가져오기
     */
    public Grade findStudentGrade(Long studentId, Long courseId) {

        StudentCourse studentCourse = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId);

        return gradeRepository.findByStudentCourse(studentCourse);
    }

    /**
     * 출석기록 가져오기
     */
    public Attendance findStudentAttendance(Long studentId, Long courseId, String timestamp) {
        return attendanceRepository.findByStudentIdAndCourseIdAndTimeStamp(studentId, courseId, timestamp);
    }

    /**
     * 학생별 과목별 출석기록 전체 가져오기
     */
    public List<Attendance> findStudentAttendancesForCourse(Long studentId, Long courseId) {
        return attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
    }


}
