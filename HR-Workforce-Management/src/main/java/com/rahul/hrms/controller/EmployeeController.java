package com.rahul.hrms.controller;
import com.rahul.hrms.dto.EmployeeRequest;
import com.rahul.hrms.model.Employee;
import com.rahul.hrms.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){this.service=service;}
    @GetMapping public Page<Employee> list(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="10") int size,
      @RequestParam(defaultValue="id") String sortBy,@RequestParam(defaultValue="asc") String direction){return service.list(page,size,sortBy,direction);}
    @GetMapping("/{id}") public Employee get(@PathVariable Long id){return service.get(id);}
    @PostMapping @PreAuthorize("hasAnyRole('ADMIN','HR')") public Employee create(@Valid @RequestBody EmployeeRequest r){return service.create(r);}
    @PutMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN','HR')") public Employee update(@PathVariable Long id,@Valid @RequestBody EmployeeRequest r){return service.update(id,r);}
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void deactivate(@PathVariable Long id){service.deactivate(id);}
}
