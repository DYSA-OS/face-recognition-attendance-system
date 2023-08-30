package FaceAuto.AttendanceApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Professor {
    /**
     * 교수는 이름, 교번, 이메일주소, 사진을 갖고
     * 강의하는 과목리스트를 가진다.
     */
    @Id
    @GeneratedValue //한개씩 올라가는 데이터일때
    @Column(name="professor_id")
    private Long id;

    private String name; //교수 이름

    private String employeeId; //교번

    private String email; //이메일주소

    private String univ; //학교

    //이미지 파일의 경로나 URL을 문자열로 저장
    private String profilePicture; //교수 사진

    //교수와 강의은 일대다! 관계의 주인은 교수이다.
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();


//    <----연관관계 메서드---->
    //교수가 수업을 개설 할때 사용
    public void addCourse(Course course) {
        courses.add(course);
        course.setProfessor(this);
    }

    //교수가 학생 성적을 기입 할때 사용
//    public void gradeStudent(Student student, Course course, String grade) {
//        Grade gradeEntry = new Grade();
//        gradeEntry.setStudent(student);
//        gradeEntry.setCourse(course);
//        gradeEntry.setGrade(grade);
//
//        student.addGrade(gradeEntry);
//        course.addGrade(gradeEntry);
//    }

    //교수가 학생의 출석을 기록 할때 사용
//    public void markAttendance(Student student, Course course, LocalDate date) {
//        Attendance attendance = new Attendance();
//        attendance.setStudent(student);
//        attendance.setCourse(course);
//        attendance.setDate(date);
//
//        student.addAttendance(attendance);
//        course.addAttendance(attendance);
//    }


}
