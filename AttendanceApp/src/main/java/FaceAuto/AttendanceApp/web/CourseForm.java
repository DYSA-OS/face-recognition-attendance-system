package FaceAuto.AttendanceApp.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CourseForm {
    @NotEmpty(message = "과목명은 필수 입력사항입니다.")
    private String courseName;
    private List<String> sIds; //학번
//    private String studentIds; // 학생들의 학번을 쉼표로 구분하여 입력받기 위한 문자열 필드
}
