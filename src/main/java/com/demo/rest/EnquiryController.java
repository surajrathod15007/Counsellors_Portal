package com.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entity.Counsellor;
import com.demo.entity.Enquiry;
import com.demo.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController   // add_eq, //save_eq, // view_eq, // filter_eq, // edit and update
{
	
	@Autowired
	EnquiryService enquiryService; 
	
	@GetMapping("/display_enquiry") // to display enq page
	  public String displayEnquiry(Model model)   //For Form Binding  We are Sending Enq Obj To UI 
	  {
		model.addAttribute("enquiry", new Enquiry());   
		return "AddEnquiry";  
	  }
	 
	/*  In the Form there is no data to enter Consellor Id, But who is adding eq, which counsellor
	 *  adding enq this is IMP, so how to get Counsellor Id(By Using Session) already counsellor
	 *  logged in , so get the id from session by using HttpServletReq.
	 *  and getSession(false):- Means dont create new Session Object whatever sess is already 
	 *  created give that existing session only
	 * */
	
	 @PostMapping("/save_enquiry")
	 public String saveEnquiry(@ModelAttribute Enquiry enquiry, HttpServletRequest req, Model model)
	 {
		HttpSession ses=req.getSession(false);
		Integer cid=(Integer)ses.getAttribute("cid");
		
		boolean status=enquiryService.addEnquiry(enquiry, cid);
		if(status)
		{
			model.addAttribute("smsg", "Enquiry Saved");
		}
		else
		{
			model.addAttribute("emsg", "Enquiry Failed To Save");
		}
		
		model.addAttribute("enquiry", new Enquiry()); //after enq saved Same Page Should load 
		                    // to load same page Form Binding required
		return "AddEnquiry";
	 }
	
	 
	/* view all enquiries: 
	 * Here we set Empty new Enquriy() Object: Because getEnquiries(Enquiry eq,Counsellor cid); 
	 * expecting counsellorId and EnquiryBinding Object which is Entity Object
	 * initialy when we go for getEnquiries Enquiry Object will not available because i need to 
	 * display all enquires belongs to particular counsellor and initialy we dont want any filter
	 * thats why passing empty dummy enqs obj
	 * IMP: in the below getEnquiries Meth page loaded first time,Filters Not Available thats why
	 *      we are passing new Object But 
	 * IMP_2: in the filtersEnquires()meth Filters are Came to Enquriy Object thats we we used 
	 *        actual Enquiry Object  
	 * */
	 
	@GetMapping("/enquiries")
	public String getEnquiries(HttpServletRequest req, Model model)
	{
		HttpSession ses=req.getSession(false);  // false means existing ses is providing 
		                                        // true means new ses is created
		Integer cid=(Integer)ses.getAttribute("cid");
		
		List<Enquiry>  list=enquiryService.getEnquiries(new Enquiry(), cid);
		// As Above we use Empty new Enquiry(), Object because no fileter aply by default all enqs came of cid
		model.addAttribute("enqs", list);    
		
		model.addAttribute("enq", new Enquiry());     
		
	/* why we sending empty object in Model Scope: we need to mapp that dropdown or filter to 
	 *  to enquiry i.e Binding obje  
		*/
		return "DisplayEnquiry";     
		
		// when this meth execute this page loaded 
	}
	
	
	@PostMapping("/filter_enquiry")
	public String filterEnquiry(@ModelAttribute("enq") Enquiry enq, HttpServletRequest req, Model model)
	{
		HttpSession ses=req.getSession(false);
		Integer cid=(Integer)ses.getAttribute("cid");
		
		List<Enquiry> list=enquiryService.getEnquiries(enq, cid);
		for(Enquiry eq:list)
		{
			System.out.println(eq.getCourse()+" "+eq.getMode()+" "+eq.getStatus());
		}
		
		model.addAttribute("enqs", list);
		
		//model.addAttribute("enq", new Enquiry());  
		return "DisplayEnquiry";  
		
		//
	}
	
	
	/* to edit eq dont need counsellor id bcoz already eq id we will get that particular id we 
	 * need to edit 
	 * in 3 places We need counsellor_Id: 1. AddEq 2.FilterEq 3. getEnqs
	 * 
	 * RequestParam: this is qury parameter means:any data want to send from UI to server in URL
	 * then go for qury Parameter, to read quryParameters use Annotation-@Requestparam
	 */
	
	
	@GetMapping("/edit_equiry/{eqId}")
	public String editEnquiry(@PathVariable("eqId") Integer eqId, Model model)
	{
		Enquiry enquiry=enquiryService.getEnquiry(eqId);
		model.addAttribute("enquiry", enquiry); // sending enquiry object to poping obj data in editable mode
		
		System.out.println("Im Here");         
		return "AddEnquiry";
	}
	
}	
	
	
	
	
	
	
	
	
	
