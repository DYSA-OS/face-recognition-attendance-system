package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.Student;
import FaceAuto.AttendanceApp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //Junit5에서 스프링 확장을 사용하도록 설정
@SpringBootTest //스프링부트를 띄우고 테스트
@Transactional //반복가능한 테스트 지원, 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백

class StudentServiceTest {
    @Autowired StudentService studentService;
    @Autowired StudentRepository studentRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Student student = new Student();
        student.setName("kim");
        student.setSId("202116266");
//        student.setEmail("codkan20@gmail.com");
//        student.setProfilePicture("sola/pic");

        //when
        Long saveId = studentService.join(student);

        //then
        assertEquals(student, studentRepository.findOne(saveId));
        System.out.println("student = " + student.getName());
    }



}