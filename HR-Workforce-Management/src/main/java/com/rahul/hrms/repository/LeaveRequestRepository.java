package com.rahul.hrms.repository;

import com.rahul.hrms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	
	List<LeaveRequest> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);

	
	boolean existsByEmployeeIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long employeeId,
			LeaveStatus status, LocalDate end, LocalDate start);
}
