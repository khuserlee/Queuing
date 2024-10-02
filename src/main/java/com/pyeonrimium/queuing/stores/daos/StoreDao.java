package com.pyeonrimium.queuing.stores.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;

@Repository
public class StoreDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public StoreEntity addStore(StoreEntity storeEntity) {
		System.out.println("[StoreDao] addStore()");
		return null;
	}
	
	public StoreEntity findStore(Long storeId) {
		System.out.println("[StoreDao] findStore()");
		
		String sql = "SELECT * FROM stores WHERE store_id = ?";
		StoreEntity storeEntity = null;

		try {
			// DB에서 sql 구문을 실행한 후
			// 결과를 받아와서
			// storeEntity에 저장
			storeEntity = jdbcTemplate.queryForObject(sql,
					BeanPropertyRowMapper.newInstance(StoreEntity.class),
					storeId);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// 결과를 반환
		return storeEntity;
	}

}
