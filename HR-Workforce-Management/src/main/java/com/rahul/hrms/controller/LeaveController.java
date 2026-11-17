package com.rahul.hrms.controller;
import com.rahul.hrms.dto.LeaveRequestDto;
import com.rahul.hrms.model.*;
import com.rahul.hrms.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/leaves")
public class LeaveController {
 private final LeaveService service;
 public LeaveController(LeaveService s){service=s;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public LeaveRequest apply(@Valid @RequestBody LeaveRequestDto r){return service.apply(r);}
 @GetMapping public List<LeaveRequest> list(@RequestParam(required=false) Long employeeId){return service.list(employeeId);}
 @PatchMapping("/{id}/review") @PreAuthorize("hasAnyRole('ADMIN','HR')") public LeaveRequest review(@PathVariable Long id,@RequestParam LeaveStatus status,Authentication auth){return service.review(id,status,auth.getName());}
}
