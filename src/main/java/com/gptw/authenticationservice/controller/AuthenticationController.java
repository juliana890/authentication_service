package com.gptw.authenticationservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {
	
	 @RequestMapping(value="/index", method=RequestMethod.GET)
	 public ModelAndView getIndex(){
	    ModelAndView mv = new ModelAndView("index");
	    return mv;
	 }

}
