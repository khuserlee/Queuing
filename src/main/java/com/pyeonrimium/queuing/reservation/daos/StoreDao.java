package com.pyeonrimium.queuing.reservation.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDao {
    @Autowired
    private  JdbcTemplate jdbcTemplate;
    
    public String getStoreName(long storeId) {
    		String sql = "SELECT name FROM stores WHERE store_id = storeId";
    		String storeName = null;

    		try {
    			storeName = jdbcTemplate.queryForObject(sql, new RowMapper<String>() {
		            @Override
		            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		                return rs.getString("name");
		            }
		        }, storeId);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    		return storeName;
    }
}

