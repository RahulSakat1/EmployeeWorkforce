package com.rahul.hrms.service;

import com.rahul.hrms.dto.EmployeeRequest;
import com.rahul.hrms.model.*;
import com.rahul.hrms.repository.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

	private final EmployeeRepository employees;
	private final DepartmentRepository departments;

	public EmployeeService(EmployeeRepository e, DepartmentRepository d) {
		employees = e;
		departments = d;
	}

	public Page<Employee> list(int page, int size, String sortBy, String direction) {
		if (size < 1 || size > 100)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "size must be between 1 and 100");
		
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		return employees.findAll(PageRequest.of(Math.max(page, 0), size, sort));
	}

	public Employee get(Long id) {
		return employees.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found: " + id));
	}

	@Transactional
	public Employee create(EmployeeRequest r) {
		if (employees.existsByEmailIgnoreCase(r.email()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
		
		if (employees.existsByEmployeeCodeIgnoreCase(r.employeeCode()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee code already exists");
		
		Employee e = new Employee();
		apply(e, r);
		
		return employees.save(e);
	}

	@Transactional
	public Employee update(Long id, EmployeeRequest r) {
	
		Employee e = get(id);
	
		if (employees.findByEmailIgnoreCase(r.email()).filter(x -> !x.getId().equals(id)).isPresent())
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
		
		apply(e, r);
		
		return employees.save(e);
	}

	private void apply(Employee e, EmployeeRequest r) {
		e.setEmployeeCode(r.employeeCode());
		e.setFirstName(r.firstName());
		e.setLastName(r.lastName());
		e.setEmail(r.email().trim().toLowerCase());
		e.setPhone(r.phone());
		e.setJobTitle(r.jobTitle());
		e.setJoiningDate(r.joiningDate());
		
		if (r.departmentId() == null)
			e.setDepartment(null);
		else
			e.setDepartment(departments.findById(r.departmentId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found")));
	}

	@Transactional
	public void deactivate(Long id) {
		
		Employee e = get(id);
		
		e.setActive(false);
		
		employees.save(e);
	}
}
