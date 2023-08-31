package FaceAuto.AttendanceApp.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CourseForm {
    @NotEmpty(message = "과목명은 필수 입력사항입니다.")
    private String courseName;
    private List<String> sIds; //학번
}
