package com.pyeonrimium.queuing.menus.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;

@Repository
public class LatestMenuPostDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(WillBePostedMenu menu) {

		String sql = "INSERT INTO menus (store_id, name, description, price, menu_order) VALUES (?, ?, ?, ?, 0)";// values 이거 뭐임?

		jdbcTemplate.update(sql, menu.getStoreId(), menu.getName(), menu.getDescription(), menu.getPrice());

	}

}
