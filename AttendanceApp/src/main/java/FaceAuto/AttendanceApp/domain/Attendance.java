package FaceAuto.AttendanceApp.domain;

import FaceAuto.AttendanceApp.web.AttendanceRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

//출결 엔티티
@Entity
@Getter @Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; //강의 출결 상태, PRESENT LATE ABSENT

    private String timestamp; //출석시간 체크용도
//    private LocalDate date; //날짜
//    private LocalTime time; //시간

//	강의와 출결은 1:N 관계
//	하나의 강의에는 여러 학생의 출결이 기록될 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

//  학생과 출결은 1:N 관계
//  하나의 학생은 여러 강의에 출결을 할 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;


}
