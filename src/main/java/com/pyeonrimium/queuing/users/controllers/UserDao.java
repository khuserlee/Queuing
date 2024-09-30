package com.pyeonrimium.queuing.users.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pyeonrimium.queuing.users.domains.dtos.Find_idRequest;
import com.pyeonrimium.queuing.users.domains.dtos.ProfileUpdateRequest;

@Component
public class UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;	
	
	// 회원가입 (아이디 중복 조회)
	public boolean isUserMember(String id) {
		System.out.println("[UserDao] isUserMember()");
		
		String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, id);
		
		return result > 0 ? true : false; 
	}
	
	// 회원가입 (계정 생성)
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
	
	// 로그인
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
	
	
	// 아이디 찾기 (이름, 전화번호)
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
	
	// 비밀번호 찾기 (이름, 전화번호, 아이디)
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
	
	// 임시 비밀번호 생성 후 비밀번호 업데이트
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

	public ProfileUpdateRequest selectProfileUpdateRequest(String id) {
	    System.out.println("[UserDao] selectProfileUpdateRequest()");
	    String sql = "SELECT id, name, address, phone FROM users WHERE id = ?";
	    try {
	        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
	            ProfileUpdateRequest profileRequest = new ProfileUpdateRequest();
	            profileRequest.setId(rs.getString("id"));
	            profileRequest.setName(rs.getString("name"));
	            profileRequest.setAddress(rs.getString("address"));
	            profileRequest.setPhone(rs.getString("phone"));
	            return profileRequest;
	        });
	    } catch (DataAccessException e) {
	        e.printStackTrace();
	        throw new RuntimeException("프로필 정보 조회 중 오류가 발생했습니다.", e);
	    }
	}

	public int updateProfile(ProfileUpdateRequest profileUpdateRequest) {
	    System.out.println("[UserDao] updateProfile()");
	    String sql = "UPDATE users SET name = ?, address = ?, phone = ? WHERE id = ?";
	    try {
	        return jdbcTemplate.update(sql, 
	            profileUpdateRequest.getName(), 
	            profileUpdateRequest.getAddress(), 
	            profileUpdateRequest.getPhone(),
	            profileUpdateRequest.getId()
	        );
	    } catch (DataAccessException e) {
	        e.printStackTrace();
	        throw new RuntimeException("프로필 업데이트 중 오류가 발생했습니다.", e);
	    }
	}
}
