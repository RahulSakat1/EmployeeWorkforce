package com.rahul.hrms.controller;
import com.rahul.hrms.dto.DepartmentRequest;
import com.rahul.hrms.model.Department;
import com.rahul.hrms.repository.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController @RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentRepository repo;
    public DepartmentController(DepartmentRepository repo){this.repo=repo;}
    @GetMapping public List<Department> list(){return repo.findAll();}
    @GetMapping("/{id}") public Department get(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found"));}
    @PostMapping @PreAuthorize("hasAnyRole('ADMIN','HR')") public Department create(@Valid @RequestBody DepartmentRequest r){
      if(repo.existsByNameIgnoreCase(r.name())) throw new ResponseStatusException(HttpStatus.CONFLICT,"Department name already exists");
      return repo.save(new Department(r.name().trim(),r.description()));
    }
    @PutMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN','HR')") public Department update(@PathVariable Long id,@Valid @RequestBody DepartmentRequest r){
      Department d=get(id); if(!d.getName().equalsIgnoreCase(r.name())&&repo.existsByNameIgnoreCase(r.name())) throw new ResponseStatusException(HttpStatus.CONFLICT,"Department name already exists");
      d.setName(r.name().trim());d.setDescription(r.description());return repo.save(d);
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void delete(@PathVariable Long id){repo.delete(get(id));}
}
