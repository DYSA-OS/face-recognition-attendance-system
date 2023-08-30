package FaceAuto.AttendanceApp.repository;

import FaceAuto.AttendanceApp.domain.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProfessorRepository {

    @PersistenceContext
    private final EntityManager em;

    /**
     * 교수 정보 저장하기
     */
    public Professor save(Professor professor){
        em.persist(professor);
        return professor;
    }

    /**
     * id로 교수 찾기
     */
    public Professor findOne(Long id) {
        return em.find(Professor.class, id);
    }

    /**
     * 교수 전체 찾기
     */
    public List<Professor> findAll() {
        return em.createQuery("select p from Professor p", Professor.class).getResultList();
    }

    /**
     * 교수님 이름으로 찾기
     */
    public List<Professor> findByName(String name){
        return em.createQuery("select p from Professor p where p.name = :name", Professor.class)
                .setParameter("name", name).getResultList();
    }

}
