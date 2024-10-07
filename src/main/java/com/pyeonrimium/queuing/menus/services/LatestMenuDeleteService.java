package com.pyeonrimium.queuing.menus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.LatestMenuDeleteDao;

@Service
public class LatestMenuDeleteService {
	
	@Autowired
    private LatestMenuDeleteDao menuDAO;
	
	public void deleteMenu(long selectedMenuId) {

        menuDAO.deleteMenu(selectedMenuId);
}


}

