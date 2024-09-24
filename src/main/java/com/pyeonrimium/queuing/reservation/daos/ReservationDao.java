package com.pyeonrimium.queuing.reservation.daos;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;

@Component
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addReservation(ReservationEntity reservationEntity) {
        String sql = "INSERT INTO reservations (user_id, store_id, reservation_number, "
                   + "reservation_date, party_size, request, status, created_at, modified_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int result = -1;

        try {
            String status = "Sample"; // 고정된 문자열로 상태 설정
            LocalDateTime now = LocalDateTime.now(); // 현재 시간
            String reservationDate = "2024-09-10"; // 고정된 예약 날짜
            int userId = 1; // 예시로 1로 설정, 필요에 따라 변경 가능
            
            result = jdbcTemplate.update(sql, 
            	   userId,
                   reservationEntity.getStoreId(),
                   reservationEntity.getReservationNumber(),
                   reservationDate, // 고정된 예약 날짜 사용
                   reservationEntity.getPartySize(),
                   reservationEntity.getRequest(), // 요청 내용
                   status, // 고정된 status 값 사용
                   now, // 현재 시간으로 'created_at' 설정
                   now  // 현재 시간으로 'modified_at' 설정
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result > 0; // 결과가 0보다 크면 true 반환
    }
}
