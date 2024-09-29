package com.pyeonrimium.queuing.menus.daos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.menus.domains.entities.Menu;

import java.util.List;

@Repository
public class MenuListDao {
	
@Autowired
private JdbcTemplate jdbcTemplate;

public List<Menu> findByStoreId(String storeId) {

        String sql = "SELECT * FROM menus WHERE store_id = ?";

       return jdbcTemplate.query(sql, new Object[]{storeId}, (rs, rowNum) -> {

            Menu menu = new Menu();

            menu.setId(rs.getString("menu_id"));

            menu.setName(rs.getString("name"));   
	
            menu.setDescription(rs.getString("description"));

            menu.setPrice(rs.getDouble("price")); //<< int 인데 double해도되나?

            return menu;

        } );
	

}
}
