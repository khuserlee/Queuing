package com.pyeonrimium.queuing.reservation.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;

@Repository
public class ReservationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean addReservation(ReservationEntity reservationEntity) {
		String sql = "INSERT INTO reservations ("
				+ "user_id, store_id, user_name, store_name, "
				+ "reservation_number, reservation_date, reservation_time, party_size, "
				+ "request, status, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		final String status = "예약 생성"; // 고정된 문자열로 상태 설정
		int result = -1;

		try {
			result = jdbcTemplate.update(sql,
					reservationEntity.getUserId(), reservationEntity.getStoreId(),
					reservationEntity.getUserName(), reservationEntity.getStoreName(),
					reservationEntity.getReservationNumber(), reservationEntity.getReservationDate(),
					reservationEntity.getReservationTime(), reservationEntity.getPartySize(),
					reservationEntity.getRequest(), // 요청 내용
					status, // 고정된 status 값 사용
					reservationEntity.getCreatedAt() // 현재 시간으로 'created_at' 설정
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result > 0; // 결과가 0보다 크면 true 반환
	}

	/**
	 * 예약 정보 조회하기
	 * 
	 * @param userId 유저 고유 번호
	 * @return 조회된 예약 정보 목록
	 */
	public List<ReservationEntity> getReservationsByUserId(Long userId) {
		String sql = "SELECT * FROM reservations WHERE user_id = ?";
		List<ReservationEntity> reservations = null;

		// 예외 처리
		try {
			reservations = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ReservationEntity.class), userId);
		} catch (DataAccessException e) {
			System.out.println(e.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reservations;
	}
	
	
	// 예약된 정보 업데이트 하기
	public boolean updateReservation(ReservationEntity reservationEntity) {
		String sql = "UPDATE reservations SET user_id = ?, store_id = ?, reservation_number = ?, "
				+ "reservation_date = ?, reservation_time = ?, party_size = ?, request = ?, "
				+ "status = ?, modified_at = ? WHERE reservation_id = ?;";
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE reservations SET ")
			.append("user_id = ?, ")
			.append("store_id = ?, ")
			.append("reservation_number = ?, ")
			.append("reservation_date = ?, ")
			.append("reservation_time = ?, ")
			.append("party_size = ?, ")
			.append("request = ?, ")
			.append("status = ?, ")
			.append("modified_at = ?, ")
			.append("WHERE reservation_id = ?;");

		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(reservationEntity.getUserId()));
		args.add(String.valueOf(reservationEntity.getStoreId()));
		args.add(reservationEntity.getReservationNumber());
		args.add(reservationEntity.getReservationDate().toString()); 
		args.add(reservationEntity.getReservationTime().toString());
		args.add(String.valueOf(reservationEntity.getPartySize())); 
		args.add(reservationEntity.getRequest());
		args.add(reservationEntity.getStatus());
		args.add(LocalDateTime.now().toString());
		args.add(String.valueOf(reservationEntity.getReservationId())); 
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (DataAccessException e) {
			System.out.println(e.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result > 0;
	}
	
	public ReservationEntity getReservationsByReservationId(Long reservationId) {
		String sql = "SELECT * FROM reservations WHERE reservation_id = ?";
		ReservationEntity reservations = null;

		// 예외 처리
		try {
			reservations = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(ReservationEntity.class), reservationId);
		} catch (DataAccessException e) {
			System.out.println(e.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reservations;
	}
	

//	// 예약된 정보 reservationId로 찾아오기
//	public ReservationEntity updateReservation(Long reservationId) {
//		String sql = "UPDATE reservations SET user_id = ?, store_id = ?, reservation_number = ?, "
//				+ "reservation_date = ?, reservation_time = ?, party_size = ?, request = ?, "
//				+ "status = ?, updated_at = ? WHERE reservation_id = ?;";
//
//		List<ReservationEntity> reservations = null;
//
//		try {
//			reservations = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ReservationEntity.class),
//					reservations);
//		} catch (DataAccessException e) {
//			System.out.println(e.getClass().getSimpleName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return reservations;
//	}

	public ReservationEntity findByReservationNumber(String reservationNumber) {
		System.out.println("[DAO] reservationNumber: " + reservationNumber);
		String sql = "SELECT * FROM reservations WHERE reservation_number = ?;";
		ReservationEntity reservationEntity = null;
		
		try {
			reservationEntity = jdbcTemplate.queryForObject(sql,
					BeanPropertyRowMapper.newInstance(ReservationEntity.class),
					reservationNumber);
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reservationEntity;
	}

	public boolean deleteReservationByNumber(String reservationNumber) {

		String sql = "DELETE FROM reservations where reservation_number = ?;";
		int deletedCount = -1;
		
		try {
			deletedCount = jdbcTemplate.update(sql, reservationNumber);
			
		} catch (DataAccessException e) {
		    System.out.println(e);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		boolean isSuccess = deletedCount > 0;
		return isSuccess;
	}

	public boolean checkReservation(Long storeId, LocalDate reservationDate, LocalTime reservationTime) {

		String sql = "SELECT COUNT(*) FROM reservations "
				+ "WHERE store_id = ? AND reservation_date = ? AND reservation_time = ?";
		
		List<String> args = new ArrayList<>();
		args.add(String.valueOf(storeId));
		args.add(reservationDate.toString());
		args.add(reservationTime.toString());
		
		int result = -1;
		
		try {
			result = jdbcTemplate.queryForObject(sql, Integer.class, args.toArray());
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		boolean isExisted = result > 0;
		return isExisted;
	}

	public List<ReservationEntity> findAllByUserId(Long userId, int pageSize, Integer pageNo) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM reservations ")
			.append("WHERE user_id = ? ")
			.append("ORDER BY reservation_number DESC ")
			.append("LIMIT ? OFFSET ?;");
		
		final int offset = (pageNo - 1) * pageSize;
		
		List<String> args = new ArrayList<>();
		args.add(String.valueOf(userId));
		args.add(LocalDate.now().toString());
		args.add(String.valueOf(pageSize));
		args.add(String.valueOf(offset));
		
		List<ReservationEntity> results = null;
		
		try {
			results = jdbcTemplate.query(sb.toString(),
					BeanPropertyRowMapper.newInstance(ReservationEntity.class),
					userId, pageSize, offset);
		} catch (DataAccessException e) {
			System.out.println("[ReservationDao] findAllByUserId() => Error:\n" + e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}

	public Integer countMyReservations(Long userId) {
		
		String sql = "SELECT COUNT(*) FROM reservations WHERE user_id = ?;";
		int count = 0;
		
		try {
			count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
		} catch (DataAccessException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
}


