package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/savings/createMyRule")
@SessionAttributes("userId")
public class CreateMyRuleController {
	private Long userId;
	
	@ModelAttribute
    public void setUserId(@SessionAttribute("userId") Long userId) {
        this.userId = userId;
    }
	
	

}
