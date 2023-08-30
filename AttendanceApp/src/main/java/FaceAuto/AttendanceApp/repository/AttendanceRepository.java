package FaceAuto.AttendanceApp.repository;

import FaceAuto.AttendanceApp.domain.Attendance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public Attendance save(Attendance attendance) {
        if (attendance.getId() == null) {
            em.persist(attendance);
            return attendance;
        } else {
            return em.merge(attendance);
        }
    }

//    @Transactional
    public Attendance findByStudentIdAndCourseIdAndTimeStamp(Long studentId, Long courseId, String timeStamp) {
        String jpql = "SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.course.id = :courseId AND a.timestamp = :timeStamp";
        return em.createQuery(jpql, Attendance.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .setParameter("timeStamp", timeStamp)
                .getSingleResult();
    }

    public List<Attendance> findByStudentIdAndCourseId(Long studentId, Long courseId) {
        String jpql = "SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.course.id = :courseId";
        return em.createQuery(jpql, Attendance.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public List<Attendance> findByCourseId(Long courseId) {
        String jpql = "SELECT a FROM Attendance a WHERE a.course.id = :courseId";
        return em.createQuery(jpql, Attendance.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}
