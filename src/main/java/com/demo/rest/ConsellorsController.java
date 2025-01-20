package com.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.dto.Dashboard;
import com.demo.entity.Counsellor;
import com.demo.service.CounsellorService;
import com.demo.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ConsellorsController 
{
	@Autowired
	CounsellorService counsellorService;
	
	@Autowired
	EnquiryService enqService;
	
	@GetMapping("/logout")
	public String logOut(HttpServletRequest req, Model model)
	{
	   HttpSession ses=req.getSession(false);
	   ses.invalidate();
	   return "redirect:/";
	}     
	
    @GetMapping("/")
	public String openLogin(Model model)
	{
    	model.addAttribute("counsellor", new Counsellor());  
		return "Login";
	}
    
    @GetMapping("open_register")
    public String openRegister(@ModelAttribute Counsellor counsellor)
    {
    	return "Register";  
    }
    
    @PostMapping("/register_counsellor")
    public String saveCounsellor(@ModelAttribute Counsellor counsellor, Model model)
    {
      boolean status= counsellorService.saveCounsellor(counsellor);
      if(status)
      {
    	  model.addAttribute("smsg", "Counsellor Saved");  
      }
      else
      {
    	  model.addAttribute("emsg", "Counsellor Failed To Save");
      }
      return "Register";  
    }
    
    @PostMapping("/handle_login")
    public String getCounsellor(@ModelAttribute Counsellor counsellor, HttpServletRequest req , Model model)
    {
       Counsellor c= counsellorService.getCounsellor(counsellor.getEmail(), counsellor.getPwd()); 
       if(c==null)
       {
    	 model.addAttribute("emsg", "Invalid Credentials");
    	 return "Login";
       }
       else
       {
    	 // Set Counsellor Id in Session  
    	 HttpSession ses=req.getSession(true);       // Always New Session
    	 ses.setAttribute("cid", c.getCounsellorId());
    	 
    	 
    	 Dashboard dbInfo=enqService.getDashboardInfo(c.getCounsellorId());
    	 model.addAttribute("dashboard", dbInfo);
    	 return "Dashboard";
    	 
    	 /*
    	 U should remove this duplicate Logic and directly Return REDirect Dashboard Page.
    	 */
    	 
    	 //return "Dashboard";  
       }
    }
    
    
    @GetMapping("/dashboard")
    public String buildDashboard(HttpServletRequest req, Model model)
    {
      HttpSession ses=req.getSession(false);
      Integer cid=(Integer) ses.getAttribute("cid");
      
      Dashboard dbInfo=enqService.getDashboardInfo(cid);
      model.addAttribute("dashboard", dbInfo);
      return "Dashboard";	
    }
}
