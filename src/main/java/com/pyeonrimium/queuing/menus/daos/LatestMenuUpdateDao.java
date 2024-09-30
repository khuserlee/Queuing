package com.pyeonrimium.queuing.menus.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;

@Repository
public class LatestMenuUpdateDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	 public void updateMenu(WillBeUpdatedMenu menu) {
		 
		 String sql = "UPDATE menus SET name = ?, description = ?, price = ? WHERE menu_id = ?";
		 jdbcTemplate.update(sql, menu.getName(), menu.getPrice(),  menu.getDescription(), menu.getId());
	 }

}
