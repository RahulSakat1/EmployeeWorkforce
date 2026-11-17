package com.rahul.hrms.controller;
import com.rahul.hrms.model.*;
import com.rahul.hrms.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@RestController @RequestMapping("/api/v1/attendance")
public class AttendanceController {
 private final AttendanceRepository attendance; private final EmployeeRepository employees;
 public AttendanceController(AttendanceRepository a,EmployeeRepository e){attendance=a;employees=e;}
 @PostMapping("/check-in") @ResponseStatus(HttpStatus.CREATED) public Attendance checkIn(@RequestParam Long employeeId){
   Employee e=employees.findById(employeeId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found"));
   LocalDate today=LocalDate.now();
   if(attendance.findByEmployeeIdAndWorkDate(employeeId,today).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Attendance already exists for today");
   Attendance a=new Attendance();a.setEmployee(e);a.setWorkDate(today);a.setCheckIn(LocalDateTime.now());return attendance.save(a);
 }
 @PatchMapping("/check-out") public Attendance checkOut(@RequestParam Long employeeId){
   Attendance a=attendance.findByEmployeeIdAndWorkDate(employeeId,LocalDate.now()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Check in first"));
   if(a.getCheckOut()!=null) throw new ResponseStatusException(HttpStatus.CONFLICT,"Already checked out");
   a.setCheckOut(LocalDateTime.now());return attendance.save(a);
 }
 @GetMapping public List<Attendance> report(@RequestParam Long employeeId,@RequestParam LocalDate start,@RequestParam LocalDate end){
   if(end.isBefore(start)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"end must be on or after start");
   return attendance.findByEmployeeIdAndWorkDateBetweenOrderByWorkDateDesc(employeeId,start,end);
 }
}
