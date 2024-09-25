package com.pyeonrimium.queuing.reservation.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;

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
			result = jdbcTemplate.update(sql, 
			reservationEntity.getUserId(),
			reservationEntity.getStoreId(),
			reservationEntity.getReservationNumber(),
			reservationEntity.getReservationDate(),
			reservationEntity.getReservationTime(),
			reservationEntity.getPartySize(),
			reservationEntity.getRequest(), // 요청 내용
			status, // 고정된 status 값 사용
			reservationEntity.getCreatedAt() // 현재 시간으로 'created_at' 설정
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result > 0; // 결과가 0보다 크면 true 반환
	}
}
