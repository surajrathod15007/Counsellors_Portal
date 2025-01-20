package com.demo.service;

import com.demo.entity.Counsellor;

public interface CounsellorService 
{
   public boolean saveCounsellor(Counsellor counsellor);
   
   public Counsellor getCounsellor(String email, String password);
}
