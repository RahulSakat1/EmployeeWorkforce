package com.rahul.hrms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="leave_requests")
public class LeaveRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Employee employee;
    @NotNull private LocalDate startDate;
    @NotNull private LocalDate endDate;
    @Column(length=1000) private String reason;
    @Enumerated(EnumType.STRING) private LeaveStatus status=LeaveStatus.PENDING;
    private String reviewedBy;
    private LocalDateTime createdAt=LocalDateTime.now();
    public LeaveRequest() {}
    public Long getId(){return id;} public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;}
    public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;}
    public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;}
    public String getReason(){return reason;} public void setReason(String v){reason=v;}
    public LeaveStatus getStatus(){return status;} public void setStatus(LeaveStatus v){status=v;}
    public String getReviewedBy(){return reviewedBy;} public void setReviewedBy(String v){reviewedBy=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
