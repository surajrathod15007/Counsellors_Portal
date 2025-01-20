package com.demo.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enquiry 
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer enqId;
  
  private String name;
  
  private String email;
  
  private String phno;
  
  private String mode;
  
  private String course;
  
  private String status;
  
  @CreationTimestamp
  private LocalDate createdDate;
  
  @UpdateTimestamp
  private LocalDate updatedDate;
  
  @ManyToOne
  @JoinColumn(name="counsellorId")
  private Counsellor counsellor;

public Integer getEnqId() {
	return enqId;
}

public void setEnqId(Integer enqId) {
	this.enqId = enqId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPhno() {
	return phno;
}

public void setPhno(String phno) {
	this.phno = phno;
}

public String getMode() {
	return mode;
}

public void setMode(String mode) {
	this.mode = mode;
}

public String getCourse() {
	return course;
}

public void setCourse(String course) {
	this.course = course;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public LocalDate getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(LocalDate createdDate) {
	this.createdDate = createdDate;
}

public LocalDate getUpdatedDate() {
	return updatedDate;
}

public void setUpdatedDate(LocalDate updatedDate) {
	this.updatedDate = updatedDate;
}

public Counsellor getCounsellor() {
	return counsellor;
}

public void setCounsellor(Counsellor counsellor) {
	this.counsellor = counsellor;
} 
  
  
}
