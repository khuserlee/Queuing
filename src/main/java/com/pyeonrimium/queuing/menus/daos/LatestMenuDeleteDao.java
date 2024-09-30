package com.pyeonrimium.queuing.menus.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LatestMenuDeleteDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    public void deleteMenu(int selectedmenuId) {

	String sql = "DELETE FROM menus WHERE menu_id = ?";

    jdbcTemplate.update(sql, selectedmenuId); 

	    }

}
