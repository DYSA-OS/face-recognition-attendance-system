package FaceAuto.AttendanceApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//성적 엔티티
@Entity
@Getter @Setter
public class Grade {
    /**
     * 성적은 성적등급이 나오고,
     * 한개의 강의에서는 여러개의 성적을 가질 수 있다. ex)강다영 A+, 김솔아 A+
     * 한명의 학생은 여러개의 성적을 가질 수 있다. ex) 자료구조 A+,
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grade; //성적. A+, A, B+, B, C+, F

// 	강의와 성적은 1:N 관계다.
// 	하나의 강의에는 여러 학생의 성적이 기록될 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

//  학생과 성적은 1:N 관계입니다.
//  한명의 학생은 여러 강의의 성적이 기록될 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_course_id")
    private StudentCourse studentCourse;
//    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL)
//    private List<StudentCourse> studentCourses = new ArrayList<>();


}
