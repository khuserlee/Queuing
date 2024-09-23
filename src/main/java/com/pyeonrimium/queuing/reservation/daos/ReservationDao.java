package com.pyeonrimium.queuing.reservation.daos;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;

@Component
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationResponse addReservation(ReservationEntity reservation) {
        String sql = "INSERT INTO reservations (user_id, store_id, reservation_number, "
                   + "reservation_date, party_size, request, status, created_at, modified_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int result = -1;

        try {
            result = jdbcTemplate.update(sql, 
                    reservation.getUserId(),
                    reservation.getStoreId(),
                    reservation.getReservationNumber(),
                    reservation.getReservationDate(),
                    reservation.getPartySize(),
                    reservation.getRequset(), // 
                    reservation.getStatus(),
                    LocalDateTime.now(), // 현재 시간으로 'created_at' 설정
                    LocalDateTime.now()  // 현재 시간으로 'modified_at' 설정
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
}

 


