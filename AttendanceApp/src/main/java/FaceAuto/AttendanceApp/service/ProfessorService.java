package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.*;
import FaceAuto.AttendanceApp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final인 필드만 생성자를 생성(롬복)
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentCourseRepository studentCourseRepository;

    /**
     *  교수님 추가
     */
    @Transactional
    public Long join(Professor professor) {
        professorRepository.save(professor);
        return professor.getId(); //id를 리턴
    }

    /**
     * 교수님 전체 조회
     */
    public List<Professor> findProfessors() {
        return professorRepository.findAll();
    }

    /**
     * id로 교수님 찾기
     */
    public Professor findOne(Long id) {
        return professorRepository.findOne(id);
    }


    /**
     * 교수님 아이디로 개설 강좌 전체 찾기
     */
    public List<Course> findCoursesByProfessor(Long id) {
        Professor professor = findOne(id);
        if (professor != null) {
            return professor.getCourses();
        }
        else{
            return Collections.emptyList();
        }
    }

    //
    public Course findCourseById(Long courseId) {
        return courseRepository.findOne(courseId);
    }


    /**
     * 교수님 강좌 추가하기
     */
    @Transactional
    public void addCoursesToProfessor(Long id, Course course) {
        Professor professor = findOne(id);
        if (professor != null) {
            professor.addCourse(course);
//            courseRepository.save(course); //추가한건데, course에도 저장을 해줘야하는거 아닌가..?
            professorRepository.save(professor);
        }
    }


    /**
     * 학생 성적 메기기
     */
    @Transactional
    public void gradeStudent(Long professorId, Long studentId, Long courseId, String grade) {
        StudentCourse studentCourse = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId);
        studentCourseRepository.addGrade(studentCourse, grade);
    }

    /**
     * 학생 출석 체크하기
     */
    @Transactional
    public void markStudentAttendance(Long professorId, Long studentId, Long courseId, String timestamp, AttendanceStatus status) {
        Course course = courseRepository.findOne(courseId);
        Student student = studentRepository.findOne(studentId);

        Attendance attendance = new Attendance();
        attendance.setCourse(course);
        attendance.setStudent(student);
        attendance.setTimestamp(timestamp);
        attendance.setStatus(status);

        attendanceRepository.save(attendance);
    }
}
