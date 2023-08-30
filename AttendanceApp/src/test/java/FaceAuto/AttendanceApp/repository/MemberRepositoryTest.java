package FaceAuto.AttendanceApp.repository;

import FaceAuto.AttendanceApp.domain.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class) //Junit5에서 스프링 확장을 사용하도록 설정
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    StudentRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {
        Student student = new Student();
        student.setName("sola");
        student.setSId("202116266");
        student.setProfilePicture("/data/sola"); //이미지 경로 설정...
        Long savedId = memberRepository.save(student).getId();

        Student findStudent = memberRepository.findOne(savedId);

        Assertions.assertThat(findStudent.getId()).isEqualTo(student.getId());
        Assertions.assertThat(findStudent.getSId()).isEqualTo(student.getSId());
        Assertions.assertThat(findStudent).isEqualTo(student); //JPA 엔티티 동일성 보장
    }
}