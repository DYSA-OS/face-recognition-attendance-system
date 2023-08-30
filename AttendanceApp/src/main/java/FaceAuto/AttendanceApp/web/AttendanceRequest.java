package FaceAuto.AttendanceApp.web;

import FaceAuto.AttendanceApp.domain.AttendanceStatus;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceRequest {
    private String name;
    private String timestamp;
}
