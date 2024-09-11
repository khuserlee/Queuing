package com.pyeonrimium.queuing.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;	
	
	public boolean isUserMember(String u_m_id) {
		System.out.println("[UserDao] isUserMember()");
		
		String sql = "SELECT COUNT(*) FROM tbl_user_member "
				+ "WHERE u_m_id = ?";
	// 테이블 이름 확인 필요
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, u_m_id);
		
		return result > 0 ? true : false; 
	}
	
	public int insertUserAccount(SignupRequestDTO signupRequestDTO) {
		System.out.println("[UserDao] insertUserAccount()");
		String sql = "INSERT INTO tbl_user_member(u_m_id, "
												+ "u_m_pw, "
												+ "u_m_name, "
												+ "u_m_address, "
												+ "u_m_phone, ";
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql,
					signupRequestDTO.getId(),
					signupRequestDTO.getPassword(),
					// passwordencoder 필요한지?
					signupRequestDTO.getName(),
					signupRequestDTO.getAddress(),
					signupRequestDTO.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
