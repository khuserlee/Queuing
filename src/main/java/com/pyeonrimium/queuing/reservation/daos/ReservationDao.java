package com.pyeonrimium.queuing.reservation.daos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateRequest;

@Repository
public class ReservationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean addReservation(ReservationEntity reservationEntity) {
		String sql = "INSERT INTO reservations (user_id, store_id, reservation_number, "
				+ "reservation_date, reservation_time, party_size, request, status, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

		final String status = "예약 생성"; // 고정된 문자열로 상태 설정
		int result = -1;

		try {
			result = jdbcTemplate.update(sql, reservationEntity.getUserId(), reservationEntity.getStoreId(),
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

	// 예약된 정보 reservationId로 찾아오기
	public boolean updateReservation(ReservationUpdateRequest request) {
		String sql = "UPDATE reservations SET user_id = ?, store_id = ?, reservation_number = ?, "
				+ "reservation_date = ?, reservation_time = ?, party_size = ?, request = ?, "
				+ "status = ?, modified_at = ? WHERE reservation_id = ?;";

		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(request.getUserId()));
		args.add(String.valueOf(request.getStoreId()));
		args.add(request.getReservationNumber());
		args.add(request.getReservationDate().toString()); 
		args.add(request.getReservationTime().toString());
		args.add(String.valueOf(request.getPartySize())); 
		args.add(request.getRequest());
		args.add(request.getStatus());
		args.add(LocalDateTime.now().toString());
		args.add(request.getReservationId()); 
		
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
		// TODO Auto-generated method stub
		return null;
	}


}
