package FaceAuto.AttendanceApp.repository;

import FaceAuto.AttendanceApp.domain.Course;
import FaceAuto.AttendanceApp.domain.Grade;
import FaceAuto.AttendanceApp.domain.Student;
import FaceAuto.AttendanceApp.domain.StudentCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentCourseRepository {

    @PersistenceContext //엔티티 매니저 주입
    private final EntityManager em;


    public void save(StudentCourse studentCourse) {
        em.persist(studentCourse);
    }

    public List<Student> findStudentsByCourseId(Long courseId) { //제대로 실행됨
        return em.createQuery(
                        "SELECT sc.student FROM StudentCourse sc WHERE sc.course.id = :courseId", Student.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public List<Course> findCoursesByStudentId(Long studentId) {
        return em.createQuery("select sc.course from StudentCourse sc where sc.student.id = :studentId", Course.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    public StudentCourse findByStudentIdAndCourseId(Long studentId, Long courseId) {
        return em.createQuery("SELECT sc FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.course.id = :courseId", StudentCourse.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .getSingleResult();
    }

    public List<StudentCourse> findByStudent(Student student) {
        return em.createQuery(
                        "SELECT sc FROM StudentCourse sc WHERE sc.student = :student", StudentCourse.class)
                .setParameter("student", student)
                .getResultList();
    }

    //성적 추가하기
    public void addGrade(StudentCourse studentCourse, String gradeValue) {
        Grade grade = new Grade();
        grade.setGrade(gradeValue);
        grade.setStudentCourse(studentCourse);

        studentCourse.getGrades().add(grade);
        em.merge(studentCourse);
    }

}