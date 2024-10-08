package com.pyeonrimium.queuing.stores.daos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;

@Repository
public class StoreDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional
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
				
				args.clear();
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
	
	/**
	 * DB에서 가게 정보 불러오기
	 * @param storeId 가게 고유 번호
	 * @return 조회된 가게 정보
	 */
	public StoreEntity findStoreByStoreId(Long storeId) {
		String sql = "SELECT * FROM stores WHERE store_id = ?";
		StoreEntity storeEntity = null;

		try {
			// DB에서 sql 구문을 실행한 후 결과를 받아오기
			storeEntity = jdbcTemplate.queryForObject(sql,
					BeanPropertyRowMapper.newInstance(StoreEntity.class),
					storeId);
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return storeEntity;
	}

	/**
	 * DB에서 가게 정보 불러오기
	 * @param userId 유저 고유 번호
	 * @return 조회된 가게 정보
	 */
	public Long findStoreByUserId(Long userId) {
		// 등록된 정보가 여러 개일 수 있음
		// LIMIT 으로 반환할 행의 개수를 제한
		// 여기서는 1개로 제한
		String sql = "SELECT store_id FROM stores WHERE user_id = ? LIMIT 1;";
		Long storeId = null;
		
		try {
			// 가게 고유 번호만 조회
			storeId = jdbcTemplate.queryForObject(sql, new Object[]{userId}, Long.class);
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return storeId;
	}

	public List<StoreEntity> getNearbyStores(BigDecimal latitude, BigDecimal longitude, int radius) {
		String sql = "SELECT * " +
					"FROM (" +
						"SELECT *, " +
							"(6371000 * " +
								"acos( " +
									"cos(radians(?)) * " +
									"cos(radians(latitude)) * " +
									"cos(radians(longitude) - radians(?)) + " +
									"sin(radians(?)) * " +
									"sin(radians(latitude)) " +
								") " +
							") AS distance " +
						"FROM stores" + 
					") AS subquery " +
					"WHERE distance <= ? " +
					"ORDER BY distance " +
					"LIMIT 10;";

		List<StoreEntity> nearbyStores = null;
		
		List<String> args = new ArrayList<>();
		args.add(latitude.toString());
		args.add(longitude.toString());
		args.add(latitude.toString());
		args.add(String.valueOf(radius));
		
		try {
			nearbyStores
				= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(StoreEntity.class), args.toArray());
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nearbyStores;
	}
}
