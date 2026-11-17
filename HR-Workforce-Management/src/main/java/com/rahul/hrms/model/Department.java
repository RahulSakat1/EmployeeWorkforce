package com.rahul.hrms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="departments", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class Department {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Size(max=100) @Column(nullable=false, unique=true) private String name;
    @Size(max=500) private String description;
    public Department() {}
    public Department(String name, String description) { this.name=name; this.description=description; }
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
