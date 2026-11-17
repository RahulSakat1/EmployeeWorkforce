package com.rahul.hrms.service;

import com.rahul.hrms.dto.LeaveRequestDto;
import com.rahul.hrms.model.*;
import com.rahul.hrms.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveService {
	private final LeaveRequestRepository leaves;
	private final EmployeeRepository employees;

	public LeaveService(LeaveRequestRepository l, EmployeeRepository e) {
		leaves = l;
		employees = e;
	}

	@Transactional
	public LeaveRequest apply(LeaveRequestDto r) {
	
		if (r.endDate().isBefore(r.startDate()))
		
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be on or after start date");
		
		Employee e = employees.findById(r.employeeId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
		
		if (leaves.existsByEmployeeIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(e.getId(),
				LeaveStatus.PENDING, r.endDate(), r.startDate()))
		
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Overlapping pending leave request exists");
		
		LeaveRequest l = new LeaveRequest();
		
		l.setEmployee(e);
		l.setStartDate(r.startDate());
		l.setEndDate(r.endDate());
		l.setReason(r.reason());
		
		return leaves.save(l);
	}

	public List<LeaveRequest> list(Long employeeId) {
		
		return employeeId == null ? leaves.findAll() : leaves.findByEmployeeIdOrderByCreatedAtDesc(employeeId);
	}

	@Transactional
	public LeaveRequest review(Long id, LeaveStatus status, String reviewer) {
		
		if (status == LeaveStatus.PENDING)
		
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review status must be APPROVED or REJECTED");
		
		LeaveRequest l = leaves.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave request not found"));
		
		if (l.getStatus() != LeaveStatus.PENDING)
		
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Leave request has already been reviewed");
		
		l.setStatus(status);
		l.setReviewedBy(reviewer);
		
		return leaves.save(l);
	}
}
