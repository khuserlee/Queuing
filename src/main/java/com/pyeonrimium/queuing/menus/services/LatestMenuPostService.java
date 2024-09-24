package com.pyeonrimium.queuing.menus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.MenuPostDao;
import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;

@Service
public class LatestMenuPostService {
	
	@Autowired
	private MenuPostDao menuPostDao;
	
	public void saveMenu(WillBePostedMenu menu) {
		
		menuPostDao.save(menu);
	}

}
