package FaceAuto.AttendanceApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//강의 엔티티
@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName; //강의 이름

//  교수와 강의은 1:N 관계
//  하나의 교수가 여러 강의을 담당할 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor; //담당 교수님

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades = new ArrayList<>();

    //강의와 출석은 일대다! 관계의 주인은 강의에게 있다.
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudentCourse> studentCourses = new ArrayList<>();

    //    <---연관관계 메서드--->
    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
        attendance.setCourse(this);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setCourse(this);
    }

}
