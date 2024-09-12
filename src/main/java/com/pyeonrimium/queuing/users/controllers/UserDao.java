package com.pyeonrimium.queuing.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;	
	
	public boolean isUserMember(String id) {
		System.out.println("[UserDao] isUserMember()");
		
		String sql = "SELECT COUNT(*) FROM users "
				+ "WHERE id = ?";
	// 테이블 이름 확인 필요
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, id);
		
		return result > 0 ? true : false; 
	}
	
	public int insertUserAccount(SignupRequestModel signupRequestModel) {
		System.out.println("[UserDao] insertUserAccount()");
		String sql = "INSERT INTO users(id, "
											+ "password, "
											+ "name, "
											+ "address, "
											+ "phone, ";
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql,
					signupRequestModel.getId(),
					signupRequestModel.getPassword(),
					// passwordencoder 필요한지?
					signupRequestModel.getName(),
					signupRequestModel.getAddress(),
					signupRequestModel.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
