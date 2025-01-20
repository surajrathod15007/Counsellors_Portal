package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entity.Counsellor;
import com.demo.repo.CounsellorRepository;

@Service
public class CounsellorServiceImpl implements CounsellorService
{
    
	@Autowired
	CounsellorRepository counsellorRepository;
	
	
	
	@Override
	public boolean saveCounsellor(Counsellor counsellor) 
	{
	  Counsellor findByEmail=counsellorRepository.findByEmail(counsellor.getEmail());
	  if(findByEmail!=null)
	  {
		  return false;
	  }
	  else
	  {
		  Counsellor savedCounsellor=counsellorRepository.save(counsellor); 
		  return savedCounsellor.getCounsellorId()!=null;  
	  }		
	}

	@Override
	public Counsellor getCounsellor(String email, String password) 
	{
	  return counsellorRepository.findByEmailAndPwd(email, password);	  
	}

}
