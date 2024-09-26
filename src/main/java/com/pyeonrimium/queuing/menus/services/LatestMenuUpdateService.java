package com.pyeonrimium.queuing.menus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.LatestMenuUpdateDao;
import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;

@Service
public class LatestMenuUpdateService {
	
	@Autowired
	LatestMenuUpdateDao menuUpdateDao;
	
	
	public void updateMenu(WillBeUpdatedMenu menu) {
		
		menuUpdateDao.updateMenu(menu);
	}

}
