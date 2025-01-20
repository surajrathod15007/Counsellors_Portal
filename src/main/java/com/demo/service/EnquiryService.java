package com.demo.service;

import java.util.List;

import com.demo.dto.Dashboard;
import com.demo.entity.Enquiry;

public interface EnquiryService 
{
   // get Dashboard Page	
   public Dashboard getDashboardInfo(Integer counsellorId);
   
   // save enquiry
   public boolean addEnquiry(Enquiry enquiry, Integer counsellorId);
   
   // view enquiry + Filter
   public List<Enquiry> getEnquiries(Enquiry enquiry, Integer counsellorId);
   
   // edit
   public Enquiry getEnquiry (Integer enqId);   
}
