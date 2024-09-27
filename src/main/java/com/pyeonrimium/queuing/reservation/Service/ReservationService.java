package com.pyeonrimium.queuing.reservation.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.reservation.daos.ReservationDao;
import com.pyeonrimium.queuing.reservation.daos.StoreDao;
import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationFormResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;

@Service
public class ReservationService {
	@Autowired
	private ReservationDao reservationDao;

    @Autowired
    private StoreDao storeDao;
	
	// TODO: 예약 신청 처리
	public ReservationResponse createReservation(long userId, ReservationRequest reservationRequest) {
		// 예약 번호 만들기
		String reservationNumber = makeReservationNumber(reservationRequest);
		
		// TODO: 식당 아이디 확인
		long storeId = 1;
		
		String storeName = storeDao.getStoreName(storeId);
		
		if (storeName == null) {
			return ReservationResponse.builder()
					.isSuccess(false)
					.message("가게이름을 찾지 못했습니다.")
					.build();
		}
		

		// ReservatinEntity 생성
		ReservationEntity reservationEntity = ReservationEntity.builder()
				.userId(userId)
				.storeId(storeId)
				.reservationNumber(reservationNumber)
				.reservationDate(reservationRequest.getReservationDate())
				.reservationTime(reservationRequest.getReservationTime())
				.partySize(reservationRequest.getPartySize())
				.request(reservationRequest.getRequest())
				.createdAt(LocalDateTime.now())
				.build();
		
		// DAO 전달 -> DB에 저장
		boolean isSuccess = reservationDao.addReservation(reservationEntity);
		
		// 결과 반환
		if (!isSuccess) {
			return ReservationResponse.builder()
					.isSuccess(false)
					.message("예약에 실패했습니다.")
					.build();
		}
		
		return ReservationResponse.builder()
				.isSuccess(true)
				.message("예약에 성공했습니다.")
				.reservationNumber(reservationEntity.getReservationNumber())
				.reservationDate(reservationEntity.getReservationDate())
				.reservationTime(reservationEntity.getReservationTime())
				.partySize(reservationEntity.getPartySize())
				.request(reservationEntity.getRequest())
				.build();
	}
	
	private ReservationEntity getReservation(ReservationRequest vo) {
		ReservationEntity reservationEntity = new ReservationEntity();
		reservationEntity.setReservationNumber("");
		reservationEntity.setReservationDate(LocalDate.now());
		reservationEntity.setReservationTime(LocalTime.now());
		reservationEntity.setPartySize(vo.getPartySize());
		
		return reservationEntity;
	}
	
	private ReservationEntity getReservationUsingBuilder(ReservationRequest vo) {
		return ReservationEntity.builder()
				.reservationNumber("")
				.reservationDate(LocalDate.now())
				.reservationTime(LocalTime.now())
				.partySize(vo.getPartySize())
				.build();
	}
	
	private String makeReservationNumber(LocalDate date, LocalTime time) {
		
		String strDate = date.toString().replace("-", "");
		String strTime = time.toString().replace(":", "");
		
		String reservationNumber = strDate + strTime;

		return reservationNumber;
	}
	
	/**
	 * 예약 번호 만들기
	 * @param reservationRequest 예약 신청 양식
	 * @return 예약 번호
	 */
	private String makeReservationNumber(ReservationRequest reservationRequest) {
		
		String strDate = reservationRequest.getReservationDate().toString().replace("-", "");
		String strTime = reservationRequest.getReservationTime().toString().replace(":", "");
		
		String reservationNumber = strDate + strTime;

		return reservationNumber;
	}

	
	// TODO : 예약 수정 처리
	
		//DB에서 예약 아이디를 대조하여 예약 정보 불러오기
		//예약 수정
		//다시 DAO 전달 -> DB에 저장
	

	//TODO : 예약화면에 식당 정보 가져오기

	
	
	// TODO: 예약 수정(U)
	
	// TODO: 예약 삭제(D)
//	public List<reservationRequest> listupRes(){
//		System.out.println("[ReservationService] listipRes()");
//		
//		return ReservationDao.selectRes();
//	}
}