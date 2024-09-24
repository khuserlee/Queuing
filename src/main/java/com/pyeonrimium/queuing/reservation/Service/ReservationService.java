package com.pyeonrimium.queuing.reservation.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.reservation.daos.ReservationDao;
import com.pyeonrimium.queuing.reservation.domains.ReservationDTO;
import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationVo;

@Service
public class ReservationService {
	@Autowired
	private ReservationDao reservationDao;
	
	// TODO: 예약 신청 처리
	public ReservationResponse createReservation(long user_id, ReservationVo reservationVo) {
		// 요청 데이터 확인
		System.out.println("[ReservationService] createReservation()");
		System.out.println("date: " + reservationVo.getReservationDate());
		System.out.println("time: " + reservationVo.getReservationTime());
		System.out.println("size: " + reservationVo.getPartySize());
		System.out.println("request: " + reservationVo.getRequest());
		
		// 예약 번호 만들기
		String reservationNumber = makeReservationNumber(reservationVo);
		System.out.println("[ReservationService} makeReservationNumber()");
		System.out.println("makeReservationNumber : " + makeReservationNumber(reservationVo));

		// ReservatinEntity 생성
		ReservationEntity reservationEntity = new ReservationEntity();
		reservationEntity.setReservationNumber(reservationNumber);
		reservationEntity.setUserId(user_id);
		
		// TODO: 식당 아이디 확인
		reservationEntity.setStoreId(1);
		
		// DAO 전달 -> DB에 저장
		boolean isSuccess = reservationDao.addReservation(reservationEntity);
		
		// 결과 반환
		if (isSuccess) {
			return ReservationResponse.builder()
					.isSuccess(true)
					.message("예약에 성공했습니다.")
					.reservationNumber(reservationEntity.getReservationNumber())
					.partySize(reservationEntity.getPartySize())
					.request(reservationEntity.getRequest())
					.build();
		} else {
			return ReservationResponse.builder()
					.isSuccess(false)
					.message("예약에 실패했습니다.")
					.build();
		}
	}
	
	private ReservationEntity getReservation(ReservationVo vo) {
		ReservationEntity reservationEntity = new ReservationEntity();
		reservationEntity.setReservationNumber("");
		reservationEntity.setReservationDate(LocalDate.now());
		reservationEntity.setReservationTime(LocalTime.now());
		reservationEntity.setPartySize(vo.getPartySize());
		
		return reservationEntity;
	}
	
	private ReservationEntity getReservationUsingBuilder(ReservationVo vo) {
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
	 * @param reservationVo 예약 신청 양식
	 * @return 예약 번호
	 */
	private String makeReservationNumber(ReservationVo reservationVo) {
		
		String strDate = reservationVo.getReservationDate().toString().replace("-", "");
		String strTime = reservationVo.getReservationTime().toString().replace(":", "");
		
		String reservationNumber = strDate + strTime;

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