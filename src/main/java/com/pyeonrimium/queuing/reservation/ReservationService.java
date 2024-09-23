package com.pyeonrimium.queuing.reservation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReservationService {
	@Autowired
	private ReservationDao reservationDao;
	
	// TODO: 예약 신청 처리
	public ReservationResponse createReservation(ReservationVo reservationVo) {
		// 요청 데이터 확인
		System.out.println("[ReservationService] createReservation()");
//		System.out.println("date: " + reservationVo.getReservationDate());
//		System.out.println("time: " + reservationVo.getReservationTime());
//		System.out.println("size: " + reservationVo.getPartySize());
//		System.out.println("request: " + reservationVo.getRequest());
		
		
		// 예약 번호 만들기
////		var reservationDate = reservation.getReservationDate();
////		
////		String temp = reservationDate.toString()
////				.replace("-", "")
////				.replace(":", "");
////		reservation.setReservationNumber(temp);
//		
//		String reservationNumber = makeReservationNumber(reservation);
//		reservation.setReservationNumber(reservationNumber);
		
		// TODO: ReservatinEntity(구 Reservation) 생성
		ReservationEntity reservationEntity = new ReservationEntity();
		
		// DAO 전달 -> DB에 저장
		ReservationResponse result = reservationDao.addReservation(reservationEntity);
		
		// 결과 반환
		return result;
	}
	
	private ReservationEntity getReservation(ReservationVo vo) {
		ReservationEntity reservationEntity = new ReservationEntity();
		reservationEntity.setReservationNumber("");
		reservationEntity.setReservationDate(LocalDateTime.now());
		reservationEntity.setPartySize(vo.getPartySize());
		
		return reservationEntity;
	}
	
	private ReservationEntity getReservationUsingBuilder(ReservationVo vo) {
		return ReservationEntity.builder()
				.reservationNumber("")
				.reservationDate(LocalDateTime.now())
				.partySize(vo.getPartySize())
				.build();
	}
	
	
	private String makeReservationNumber(ReservationEntity reservation) {
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