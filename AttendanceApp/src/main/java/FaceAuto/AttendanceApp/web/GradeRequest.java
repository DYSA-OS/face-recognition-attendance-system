package FaceAuto.AttendanceApp.web;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class GradeRequest {
    private List<StudentGrade> studentGrades;

    @Data
    public static class StudentGrade {
        private Long studentId;
        private Long courseId;
        private String grade;
    }
}
