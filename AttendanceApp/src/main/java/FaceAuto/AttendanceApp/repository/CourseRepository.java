package FaceAuto.AttendanceApp.repository;

import FaceAuto.AttendanceApp.domain.Course;
import FaceAuto.AttendanceApp.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    @PersistenceContext
    private final EntityManager em;

    public Course save(Course course) {
        if (course.getId() == null) {
            em.persist(course); //id가 없으면 신규로 보고 persist 진행
        }
        else {
            em.merge(course);
        }
        return course;
    }

    public Course findOne(Long id) {
        return em.find(Course.class, id);
    }

    public List<Course> findAll() {
        return em.createQuery("select c from Course c", Course.class).getResultList();
    }

    /**
     * 과목 이름으로 찾기
     */
    public List<Course> findByName(String courseName) {
        return em.createQuery("select c from Course c where c.courseName = :name", Course.class)
                .setParameter("name", courseName).getResultList();
    }
}
