package com.pyeonrimium.queuing.menus.daos;

import java.util.ArrayList;
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


	/**
	 * 메뉴 조회하기
	 * @param menuId 메뉴 고유 번호
	 * @return 조회 결과
	 */
	public Menu findMenuByMenuId(Long menuId) {
		
		String sql = "SELECT * FROM menus WHERE menu_id = ?;";
		Menu menu = null;
		
		try {
			menu = jdbcTemplate.queryForObject(sql,
											BeanPropertyRowMapper.newInstance(Menu.class),
											menuId);
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return menu;
	}
	

	/**
	 * 메뉴 목록 조회하기
	 * @param storeId 가게 고유 번호
	 * @return
	 */
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

	
	/**
	 * 새 메뉴 정보 등록하기
	 * @param menu 메뉴 정보
	 * @return 성공 여부
	 */
	public boolean insertNewMenu(Menu menu) {

		String sql = "INSERT INTO menus (store_id, name, description, price, menu_order) VALUES (?, ?, ?, ?, ?);";
		
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(menu.getStoreId()));
		args.add(menu.getName());
		args.add(menu.getDescription());
		args.add(String.valueOf(menu.getPrice()));
		args.add(String.valueOf(menu.getMenuOrder()));

		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, args.toArray());
			
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result > 0;
	}
	
	
	/**
	 * 메뉴 삭제
	 * @param menuId 메뉴 고유 ID
	 * @return 성공 여부
	 */
	public boolean deleteMenu(Long menuId) {

		String sql = "DELETE FROM menus WHERE menu_id = ?;";
		int rowsAffected = -1;
		
		try {
			rowsAffected = jdbcTemplate.update(sql, menuId);
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rowsAffected > 0;
	}
}
