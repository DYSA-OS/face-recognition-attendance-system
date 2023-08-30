package FaceAuto.AttendanceApp.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentForm {
    @NotEmpty(message = "이름은 필수 입력사항 입니다.")
    private String name;
    private String univ;
    private String major;
    private String sId; //학번
    private String email;
    private MultipartFile profilePictureFile; // 프로필 사진 파일
}
