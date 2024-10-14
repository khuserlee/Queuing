package com.pyeonrimium.queuing.menus.daos;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.menus.domains.entities.Menu;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MenuDao {

	private final JdbcTemplate jdbcTemplate;

	public List<Menu> findMenusByStoreId(Long storeId) {
		
		String sql = "SELECT * FROM menus WHERE store_id = ?;";
		List<Menu> results = null;

		try {
			results = jdbcTemplate.query(sql,
					BeanPropertyRowMapper.newInstance(Menu.class),
					storeId);
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

}
