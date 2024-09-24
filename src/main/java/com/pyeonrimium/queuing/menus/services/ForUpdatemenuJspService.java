package com.pyeonrimium.queuing.menus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pyeonrimium.queuing.menus.daos.ForUpdateMenuJspDao;
import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;

@Service
public class ForUpdatemenuJspService {
	
	@Autowired
	ForUpdateMenuJspDao MenuDao 
	
	public ForUpdateMenu ForUpdateMenuJspGetThatWantedMenu(String selectedMenuId) {
		
	MenuDao.ForUpdateMenuJspGetThatWantedMenu(selectedMenuId);
	
 };
 
 }
