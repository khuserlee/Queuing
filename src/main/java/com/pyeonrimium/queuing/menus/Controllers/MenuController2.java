package com.pyeonrimium.queuing.menus.Controllers;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController2 {
	
	  

	   ​

	       @GetMapping("/menus")

	       public String getMenus(@RequestParam("ownerId") String ownerId, Model model) {

	           List<Menu> menus = menuService.findAllByOwnerId(ownerId);

	           model.addAttribute("menus", menus); // 

	           return "menuList"; // menuList.jsp로 포워드

	       }

	       
	   ​
	
	

}
