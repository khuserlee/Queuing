package com.pyeonrimium.queuing.menus.daos;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class MenuPostDao {
	   @Autowired

	    private JdbcTemplate jdbcTemplate;

	​

	    // 메뉴 저장 쿼리 실행

	    public void save(WillBePostedMenu menu) {

	        String sql = "INSERT INTO menus(테이블이름인거지?) (name, price, description) VALUES (?, ?, ?)";

	        jdbcTemplate.update(sql, menu.getName(), menu.getPrice(), menu.getDescription());

	    }
	
	
	
	
	
	
	
	
	

	 // 전체 메뉴 목록 불러오기

	    public List<Menu> findAll() {

	        String sql = "SELECT * FROM menus";

	        return jdbcTemplate.query(sql, new MenuRowMapper());

	    }

	    

	 // RowMapper를 사용해 ResultSet을 Menu 객체로 매핑

	    private static class MenuRowMapper implements RowMapper<Menu> {

	        @Override

	        public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {

	            Menu menu = new Menu();

	            menu.setId(rs.getLong("menu_id"));

	            menu.setName(rs.getString("name"));

	            menu.setPrice(rs.getDouble("price"));

	            menu.setDescription(rs.getString("description"));

	            return menu;

	        }

	    }

	    

	}
	


