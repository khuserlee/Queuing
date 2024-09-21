package com.pyeonrimium.queuing.reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Service
public class ReservationService {
	@Autowired
	private ReservationDao reservationDao;
	
	// TODO: 예약 신청 처리
	public ReservationResponse createReservation(Reservation reservation) {
		// 요청 데이터 확인
		
		// 예약 번호 만들기
//		var reservationDate = reservation.getReservationDate();
//		
//		String temp = reservationDate.toString()
//				.replace("-", "")
//				.replace(":", "");
//		reservation.setReservationNumber(temp);
		
		String reservationNumber = makeReservationNumber(reservation);
		reservation.setReservationNumber(reservationNumber);
		
		// DAO 전달 -> DB에 저장
		ReservationResponse result = reservationDao.addReservation(reservation);
		
		// 결과 반환
		return result;
	}
	
	private String makeReservationNumber(Reservation reservation) {
		var reservationDate = reservation.getReservationDate();
				
		String reservationNumber = reservationDate.toString()
				.replace("-", "")
				.replace(":", "");

		return reservationNumber;
	}
	
	// TODO : 예약 수정 처리
	public ReservationResponse modifyReservation(ReservationDTO reservationDTO) {
		return null;
		//DB에서 예약 아이디를 대조하여 예약 정보 불러오기
		//예약 수정
		//다시 DAO 전달 -> DB에 저장
	}
	
	
	// TODO: 예약 수정(U)
	
	// TODO: 예약 삭제(D)
//	public List<ReservationVos> listupRes(){
//		System.out.println("[ReservationService] listipRes()");
//		
//		return ReservationDao.selectRes();
//	}
}