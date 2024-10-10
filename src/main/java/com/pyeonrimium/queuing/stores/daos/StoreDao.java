package com.pyeonrimium.queuing.stores.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;

@Repository
public class StoreDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	public StoreEntity addStore(StoreEntity storeEntity) {
		System.out.println("[StoreDao] addStore()");
		String sql = "INSERT INTO stores (user_id, name, "
						+ "address, " + "description, "
						+ "phone, " + "start_time, "
						+ "end_time, " + "closed_day) "
						+ "VALUES(?, ?,?,?,?,?,?,?)";
		
		StoreEntity newEntity = null;
		int result = -1;
		
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(storeEntity.getUserId()));
		args.add(storeEntity.getName());
		args.add(storeEntity.getAddress());
		args.add(storeEntity.getDescription());
		args.add(storeEntity.getPhone());
		args.add(storeEntity.getStartTime().toString());
		args.add(storeEntity.getEndTime().toString());
		args.add(storeEntity.getClosedDay());
		
		
		try {
			result = jdbcTemplate.update(sql, args.toArray());
			
			if (result > 0) {
				sql = "SELECT * FROM stores WHERE user_id = ? AND name = ? AND address = ?";
				
				args = new ArrayList<String>();
				args.add(String.valueOf(storeEntity.getUserId()));
				args.add(storeEntity.getName());
				args.add(storeEntity.getAddress());
				
				newEntity = jdbcTemplate.queryForObject(sql,
						BeanPropertyRowMapper.newInstance(StoreEntity.class),
						args.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newEntity;
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

	@Transactional
	public StoreEntity updateStore(StoreEntity storeEntity) {
	    System.out.println("[StoreDao] updateStore()");
	    String sql = "UPDATE stores SET name = ?, address = ?, description = ?, " +
	                 "phone = ?, start_time = ?, end_time = ?, closed_day = ? " +
	                 "WHERE store_id = ?";
	    
	    int result = -1;
	    
	    List<Object> args = new ArrayList<>();
	    args.add(storeEntity.getName());
	    args.add(storeEntity.getAddress());
	    args.add(storeEntity.getDescription());
	    args.add(storeEntity.getPhone());
	    args.add(storeEntity.getStartTime().toString());
	    args.add(storeEntity.getEndTime().toString());
	    args.add(storeEntity.getClosedDay());
	    args.add(storeEntity.getStoreId());
	    
	    try {
	        result = jdbcTemplate.update(sql, args.toArray());
	        
	        if (result > 0) {
	            return findStore(storeEntity.getStoreId());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
}
