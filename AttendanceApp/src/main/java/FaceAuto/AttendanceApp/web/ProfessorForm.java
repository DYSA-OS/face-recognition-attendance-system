package FaceAuto.AttendanceApp.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfessorForm {
    @NotEmpty(message = "이름은 필수 입력사항 입니다.")
    private String name;
    private String univ; //학교
    private String employeeId;
    private String email;
    private MultipartFile profilePictureFile; // 프로필 사진 파일

}
