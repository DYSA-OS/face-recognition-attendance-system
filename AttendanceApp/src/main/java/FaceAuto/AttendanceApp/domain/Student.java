package FaceAuto.AttendanceApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {
    /**
     * 학생은 이름, 학번, 이메일주소, 사진을 가지고
     * 출석리스트와 성적리스트를 가진다.
     */
    @Id
    @GeneratedValue //한개씩 올라가는 데이터일때
    @Column(name="student_id")
    private Long id;

    private String name; //학생 이름

    private String univ; //학교

    private String major; //전공

    private String sId; //학번

    private String email; //이메일 주소

    //이미지 파일의 경로나 URL을 문자열로 저장
    private String profilePicture; //학생 사진



    // 학생과 출석은 일대다! 관계의 주인은 학생이다.
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    // 학생과 성적은 일대다! 관계의 주인은 학생이다.
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentCourse> studentCourses = new ArrayList<>();

    //<---연관관계 메서드--->
    public List<Attendance> getAttendances() {
        //수정불가능한 읽기전용 리스트 생성
        return Collections.unmodifiableList(attendances);
    }

    public List<Grade> getGrades() {
        return Collections.unmodifiableList(grades);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setStudent(this);
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
        attendance.setStudent(this);
    }

}
