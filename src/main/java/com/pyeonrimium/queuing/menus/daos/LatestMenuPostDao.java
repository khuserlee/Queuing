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

		String sql = "INSERT INTO menus (name, price, description, store_id) VALUES (?, ?, ?,?)";// values 이거 뭐임?

		jdbcTemplate.update(sql, menu.getName(), menu.getPrice(), menu.getDescription(), menu.getStoreId());

	} // 메뉴 id는 자동 할당?

}
