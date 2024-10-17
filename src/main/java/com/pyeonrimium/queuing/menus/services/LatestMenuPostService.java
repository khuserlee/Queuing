package com.pyeonrimium.queuing.menus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.LatestMenuPostDao;
import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;

@Service
public class LatestMenuPostService {

	@Autowired
	private LatestMenuPostDao menuPostDao;

	public void saveMenu(WillBePostedMenu menu) {

		menuPostDao.save(menu);
	}

}
