package FaceAuto.AttendanceApp.repository;

import FaceAuto.AttendanceApp.domain.Grade;
import FaceAuto.AttendanceApp.domain.StudentCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GradeRepository {
    @PersistenceContext
    private final EntityManager em;

    // 학생과 강의를 기준으로 성적 조회
    public Grade findByStudentCourse(StudentCourse studentCourse) {
        return em.createQuery(
                        "SELECT g FROM Grade g WHERE g.studentCourse = :studentCourse", Grade.class)
                .setParameter("studentCourse", studentCourse)
                .getSingleResult();
    }

}
