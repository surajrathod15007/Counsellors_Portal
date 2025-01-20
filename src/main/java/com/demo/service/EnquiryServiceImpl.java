package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.demo.dto.Dashboard;
import com.demo.entity.Counsellor;
import com.demo.entity.Enquiry;
import com.demo.repo.CounsellorRepository;
import com.demo.repo.EnquiryRepository;

@Service
public class EnquiryServiceImpl implements EnquiryService 
{

	@Autowired
	EnquiryRepository enquiryRepository;
	
	@Autowired
	CounsellorRepository counsellorRepository;
	
	@Override
	public Dashboard getDashboardInfo(Integer counsellorId) 
	{
		// this is one way to retrieve data from BD
		Long totalEnq=enquiryRepository.getEnquiries(counsellorId);
		Long openEnq=enquiryRepository.getOpenEnquiries(counsellorId, "open");;
		Long lostEnq=enquiryRepository.getOpenEnquiries(counsellorId, "lost");
		Long enrolledEnq=enquiryRepository.getOpenEnquiries(counsellorId, "enrolled");
		
		Dashboard d=new Dashboard();
		d.setTotalEnqs(totalEnq);
		d.setOpenEnqs(openEnq);
		d.setLostEnqs(lostEnq);
		d.setEnrolledEnqs(enrolledEnq);
		
		return d;
	}

	@Override
	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId) 
	{
	  Counsellor counsellor=counsellorRepository.findById(counsellorId).orElseThrow();	
	  enquiry.setCounsellor(counsellor);  //Association for FK 	
	  
	  Enquiry savedEnq= enquiryRepository.save(enquiry);
	  return savedEnq.getEnqId()!=null; 
	}

	@Override
	public List<Enquiry> getEnquiries(Enquiry enquiry, Integer counsellorId) 
	{
	    //Counsellor counsellor = counsellorRepository.findById(counsellorId).orElseThrow();
		//enquiry.setCounsellor(counsellor);
		
		Counsellor counsellor=new Counsellor();
		counsellor.setCounsellorId(counsellorId); 
		
		// This is for adding filter values to entity(we not used this method parameter: Enquiry enquiry because qury construction not happens properly)
		//what is problem with above binding obj Enquiry enquiry is: if we passed that directly then even if we selected
		// only course i selected still data JPA considering all fields in qury construction.
		Enquiry searchedCriteria=new Enquiry();
		searchedCriteria.setCounsellor(counsellor);
		
		if(null!=enquiry.getCourse() && !"".equals(enquiry.getCourse()))
		{
		   searchedCriteria.setCourse(enquiry.getCourse());	
		}
		
		if(null!=enquiry.getMode() && !"".equals(enquiry.getMode()))
		{
		   searchedCriteria.setMode(enquiry.getMode());	
		}
		
		if(null!=enquiry.getStatus() && !"".equals(enquiry.getStatus()))
		{
		   searchedCriteria.setStatus(enquiry.getStatus());	
		}
		
		
		//Dynamic Queiry Creation based on data in given obj.
		Example<Enquiry> of=Example.of(searchedCriteria);
		return enquiryRepository.findAll(of);
	}

	@Override
	public Enquiry getEnquiry(Integer enqId)  
	{		
		return enquiryRepository.findById(enqId).orElseThrow();
	}

}
