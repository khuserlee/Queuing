package com.pyeonrimium.queuing.menus.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;
import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;

public class LatestMenuUpdateDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	 public void updateMenu(WillBeUpdatedMenu menu) {
		 
		 String sql = "UPDATE menus SET name = ?, description = ?, price = ? WHERE menu_id = ?";
		 jdbcTemplate.update(sql, menu.getName(), menu.getPrice(),  menu.getDescription(), menu.getId());
	 }

}
