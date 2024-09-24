package com.pyeonrimium.queuing.menus.daos;

import javax.security.auth.x500.X500Principal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.SqlCall;

import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;

public class ForUpdateMenuJspDao {
	
public ForUpdateMenu ForUpdateMenuJspGetThatWantedMenu(String selectedMenuId) {
String sql = "SELECT menu_name, menu_price, menu_description FROM menus WHERE storeId = ? AND menuId = ?";

ForUpdateMenu menu = JdbcTemplate.queryForObject(sql, new Object[]{storeId, menuId}, (rs, rowNum) -> {
   
    ForUpdateMenu m = new ForUpdateMenu();
    m.setName(rs.getString("menu_name"));
    m.setPrice(rs.getDouble("menu_price"));
    m.setDescription(rs.getString("menu_description"));
    return m;
}  ) ;

return menu;
				
	}

}
