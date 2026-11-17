package com.rahul.hrms.repository;
import com.rahul.hrms.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
 Optional<Attendance> findByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);
 List<Attendance> findByEmployeeIdAndWorkDateBetweenOrderByWorkDateDesc(Long employeeId, LocalDate start, LocalDate end);
}
