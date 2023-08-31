package FaceAuto.AttendanceApp.domain;

//중간테이블을 이용한 student와 course 다대다 매핑은 19일 오후 12이후 수정건이다...!
//뭔가 잘못되면 이전 커밋으로 얼른 돌아가자...

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_course")
@Getter @Setter
public class StudentCourse {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL)
    private List<Grade> grades = new ArrayList<>();


//    <-- 연관 관계 설정 -->
    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setStudentCourse(this);
    }
}
