package FaceAuto.AttendanceApp.service;

import FaceAuto.AttendanceApp.domain.Attendance;
import FaceAuto.AttendanceApp.domain.Course;
import FaceAuto.AttendanceApp.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final인 필드만 생성자를 생성(롬복)
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public List<Attendance> findAttendanceByCourseId(Long courseId) {
        return attendanceRepository.findByCourseId(courseId);
    }

}
