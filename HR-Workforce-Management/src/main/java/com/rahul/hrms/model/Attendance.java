package com.rahul.hrms.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="attendance", uniqueConstraints=@UniqueConstraint(columnNames={"employee_id","work_date"}))
public class Attendance {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="employee_id") private Employee employee;
    @Column(name="work_date",nullable=false) private LocalDate workDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    public Attendance() {}
    public Long getId(){return id;} public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;}
    public LocalDate getWorkDate(){return workDate;} public void setWorkDate(LocalDate v){workDate=v;}
    public LocalDateTime getCheckIn(){return checkIn;} public void setCheckIn(LocalDateTime v){checkIn=v;}
    public LocalDateTime getCheckOut(){return checkOut;} public void setCheckOut(LocalDateTime v){checkOut=v;}
    public long getWorkedMinutes(){return checkIn!=null && checkOut!=null ? java.time.Duration.between(checkIn,checkOut).toMinutes() : 0;}
}
