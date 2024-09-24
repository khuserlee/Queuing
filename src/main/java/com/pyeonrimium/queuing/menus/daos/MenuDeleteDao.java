package com.pyeonrimium.queuing.menus.daos;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

@Repository
public class MenuDeleteDao {
	
    @Autowired

    private JdbcTemplate jdbcTemplate;

​

    public void deleteMenu(String menuId) {

        String sql = "DELETE FROM menus WHERE menu_id = ?";

        jdbcTemplate.update(sql, menuId);

    }



    

    public List<Menu> findAllMenus() {

        String sql = "SELECT * FROM menus";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Menu menu = new Menu();

            menu.setMenuId(rs.getString("menu_id"));

            menu.setName(rs.getString("name"));

            menu.setDescription(rs.getString("description"));

            menu.setPrice(rs.getBigDecimal("price"));

            return menu;

        });

    }

    
	
	

}
