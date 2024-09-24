package com.pyeonrimium.queuing.users.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pyeonrimium.queuing.users.domains.dtos.Find_idRequest;

@Component
public class UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;	
	
	public boolean isUserMember(String id) {
		System.out.println("[UserDao] isUserMember()");
		
		String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, id);
		
		return result > 0 ? true : false; 
	}
	
	public int insertUserAccount(SignupRequest signupRequest) {
		System.out.println("[UserDao] insertUserAccount()");
		String sql = "INSERT INTO users (id, password, name, address, phone) VALUES (?, ?, ?, ?, ?)";;
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql,
					signupRequest.getId(),
					signupRequest.getPassword(),
					// passwordencoder 필요한지?
					signupRequest.getName(),
					signupRequest.getAddress(),
					signupRequest.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public LoginRequest selectUser(LoginRequest loginRequest) {
		System.out.println("[UserrDao] selectUser()");
		
		String sql = "SELECT * FROM users WHERE id = ?";
		
		List<LoginRequest> loginRequests = new ArrayList<LoginRequest>();
		
		try {
			RowMapper<LoginRequest> rowMapper = BeanPropertyRowMapper.newInstance(LoginRequest.class);
			loginRequests = jdbcTemplate.query(sql, rowMapper, loginRequest.getId());
			if(loginRequests.size() > 0) {
				if(!loginRequest.getPassword().equals(loginRequests.get(0).getPassword())) {loginRequests.clear();
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return loginRequests.size() > 0 ? loginRequests.get(0) : null;
	}
	
	
	// SELECT
	
	// 아이디 찾기 용
	public Find_idRequest selectUser(String name, String phone) {
		
		System.out.println("[UserDao] selectUser()");
	    
	    String sql = "SELECT * FROM users WHERE name = ? AND phone = ?";
	    
	    List<Find_idRequest> find_idRequests = new ArrayList<>();
	    
	    try {
	    	RowMapper<Find_idRequest> rowMapper = BeanPropertyRowMapper.newInstance(Find_idRequest.class);
	    	find_idRequests = jdbcTemplate.query(sql, rowMapper, name, phone);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return find_idRequests.size() > 0 ? find_idRequests.get(0) : null;
	}
	
	// 비밀번호 찾기 용
	public Find_passwordRequest selectUser(String name, String phone, String id) {
		System.out.println("[UserDao] selectUser() - id: " + id + ", name: " + name + ", phone: " + phone);
		System.out.println("[UserDao] selectUser()");
		
		String sql = "SELECT * FROM users WHERE id = ? AND name = ? AND phone = ?";
		
		List<Find_passwordRequest> find_passwordRequests = new ArrayList<Find_passwordRequest>();
		
		try {
			RowMapper<Find_passwordRequest> rowMapper = BeanPropertyRowMapper.newInstance(Find_passwordRequest.class);
			find_passwordRequests = jdbcTemplate.query(sql, rowMapper, id, name, phone);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return find_passwordRequests.size() > 0 ? find_passwordRequests.get(0) : null;
	}
	
	
	// UPDATE
	public int updatePassword(String id, String newPassword) {
		System.out.println("[UserDao] updatePassword()");
		
		String sql = "UPDATE users SET password = ? WHERE id = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, newPassword, id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("id:" + id);
		System.out.println("newPassword: " + newPassword);
		System.out.println("result: " + result);
		
		return result;
	}

}
