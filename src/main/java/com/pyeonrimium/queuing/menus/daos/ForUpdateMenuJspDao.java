package com.pyeonrimium.queuing.menus.daos;

import javax.security.auth.x500.X500Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.SqlCall;

import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;

public class ForUpdateMenuJspDao {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
public ForUpdateMenu ForUpdateMenuJspGetThatWantedMenu(String selectedMenuId,String storeId) {
String sql = "SELECT name, price, description FROM menus WHERE storeId = ? AND menuId = ?";

ForUpdateMenu menu = jdbcTemplate.queryForObject(sql, new Object[]{selectedMenuId, storeId}, (rs, rowNum) -> {
   //storeId와 menuId 받기
    ForUpdateMenu m = new ForUpdateMenu();
    m.setName(rs.getString("name"));
    m.setPrice(rs.getDouble("price"));
    m.setDescription(rs.getString("description"));
    return m;
}  ) ;

return menu;
				
	}

}
