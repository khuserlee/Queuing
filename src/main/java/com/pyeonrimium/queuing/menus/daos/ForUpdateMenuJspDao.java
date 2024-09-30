package com.pyeonrimium.queuing.menus.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;

public class ForUpdateMenuJspDao {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
public ForUpdateMenu ForUpdateMenuJspGetThatWantedMenu(int selectedMenuId,int storeId) {
String sql = "SELECT name, price, description FROM menus WHERE storeId = ? AND menuId = ?";
// selectedMenuId=menuId 할 필요 없나?
ForUpdateMenu menu = jdbcTemplate.queryForObject(sql, new Object[]{selectedMenuId, storeId}, (rs, rowNum) -> { // 다른 Dao와 다르다. 
   //storeId와 menuId 받기                     // 익명 클래스를 줄인거로 알고있는데. 람다식.매개변수에 그냥 rs만 써도되나? 왜지? rowNum은 그냥 무조건 써야되는건가?
    ForUpdateMenu m = new ForUpdateMenu();
    m.setName(rs.getString("name"));
    m.setPrice(rs.getInt("price"));
    m.setDescription(rs.getString("description"));
    m.setId(rs.getInt("menu_id"));
    return m; // 여기서 m을 return하는데 밑에 return menu는 뭐지? m을 menu에 대입해서 그 menu를 반환해라? 
}  ) ;

return menu;
				
	}

}
