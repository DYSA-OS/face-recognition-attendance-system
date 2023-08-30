package FaceAuto.AttendanceApp.repository;

import FaceAuto.AttendanceApp.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentRepository {

    @PersistenceContext //엔티티 매니저 주입
    private final EntityManager em;

    /**
     * 학생 정보 저장하기
     */
    public Student save(Student student) {
        em.persist(student);
        return student; //return
    }

    /**
     * id로 학생 찾기
     */
    public Student findOne(Long id) {
//        System.out.println("userid = " + userid); //userid로 찾으려고 해보았지만, 실패
//        System.out.println(em.find(Member.class, userid));
        return em.find(Student.class, id);
    }

    /**
     * 학생 전체 찾기
     */
    public List<Student> findAll() {
        return em.createQuery("select s from Student s", Student.class).getResultList();
    }

    /**
     * 학생 이름으로 찾기
     */
    public Student findByName(String name) { //중복되는 이름은 없다고 가정
        return em.createQuery("select s from Student s where s.name = :name", Student.class)
                .setParameter("name", name).getSingleResult();
    }

    /**
     * 학번(sId)로 학생 찾기
     */
    public Student findBySId(String sId) {
        return em.createQuery("select s from Student s where s.sId = :sId", Student.class)
                .setParameter("sId", sId)
                .getSingleResult(); // 결과가 하나인 경우
    }






}
