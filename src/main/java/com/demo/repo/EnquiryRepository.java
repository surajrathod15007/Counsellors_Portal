package com.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.entity.Enquiry;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry,Integer> 
{
	@Query(
			value="select count(*) from enquiry where counsellor_id=:id",
			nativeQuery=true
		  )
    public Long getEnquiries(Integer id);  
	
	@Query(
			value="select count(*) from enquiry where counsellor_id=:id and status=:status",
			nativeQuery=true
		  )
	public Long getOpenEnquiries(Integer id, String status);
}
