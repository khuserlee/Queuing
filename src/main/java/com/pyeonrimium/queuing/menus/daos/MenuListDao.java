package com.pyeonrimium.queuing.menus.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.menus.domains.entities.Menu;

@Repository
public class MenuListDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Menu> findByStoreId(int storeId) {

		String sql = "SELECT * FROM menus WHERE store_id = ?";
		List<Menu> menus = null;

		try {
			menus = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Menu.class), storeId);
		} catch (DataAccessException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return menus;
//       return jdbcTemplate.query(sql, new Object[]{storeId}, (rs, rowNum) -> {
//
//            Menu menu = new Menu();
//
//            menu.setId(rs.getInt("menu_id"));
//
//            menu.setName(rs.getString("name"));   
//	
//            menu.setDescription(rs.getString("description"));
//
//            menu.setPrice(rs.getInt("price")); //<< int 인데 double해도되나?
//
//            return menu;
//
//        } );

	}
}
