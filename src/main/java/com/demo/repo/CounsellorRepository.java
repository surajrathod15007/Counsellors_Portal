package com.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Counsellor;

@Repository
public interface CounsellorRepository extends JpaRepository<Counsellor,Integer> 
{
   public Counsellor findByEmail(String email);	
   public Counsellor findByEmailAndPwd(String email, String pwd);
}
