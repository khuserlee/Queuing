package com.pyeonrimium.queuing.menus.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;

@Repository
public class ForUpdateMenuJspDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ForUpdateMenu ForUpdateMenuJspGetThatWantedMenu(long selectedMenuId, int storeId) {
		String sql = "SELECT * FROM menus WHERE store_id = ? AND menu_id = ?";
//SELECT ID 가 연루된곳 > jsp에서 menu.id 가 있음. SELECT문엔 안쓰는 이유가 menuId로 찾으니까.
//데이터베이스에 꺼내온 데이터를 담는 ForUpdateMenu  필드에는 id가 없어야함. 근데 jsp에는 있어야함. 
//이걸 어케 처리해야하지? Select 뭐 냐 에 따라 데이터베이스에서 꺼내온 걸 담는 FoeUpdateMenu 필드랑은 동일해야하지 않나..?
//

		ForUpdateMenu menu = jdbcTemplate.queryForObject(sql, new Object[] { storeId, selectedMenuId },
				(rs, rowNum) -> {
					// 익명 클래스를 줄인거로 알고있는데. 람다식.매개변수에 그냥 rs만 써도되나? 왜지? rowNum은 그냥 무조건 써야되는건가?
					ForUpdateMenu m = new ForUpdateMenu();
					m.setName(rs.getString("name"));
					m.setPrice(rs.getInt("price"));
					m.setDescription(rs.getString("description"));
					m.setId(rs.getInt("menu_id"));
					return m; // 여기서 m을 return하는데 밑에 return menu는 뭐지? m을 menu에 대입해서 그 menu를 반환해라?
				});

		return menu;
//-----------------------------------------------------------------------------
//List<Menu> menus = new ArrayList<Menu>(); 이거 변형시켜야함, ForUpdateMenu menu= null;
		/*
		 * try { menus = jdbcTemplate.query(sql,
		 * BeanPropertyRowMapper.newInstance(Menu.class), storeId); } catch
		 * (DataAccessException e) {
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * return menus;
		 */ // 를 변형시켜야함.
// 왜 try catch를 쓰는가?

	}

}
