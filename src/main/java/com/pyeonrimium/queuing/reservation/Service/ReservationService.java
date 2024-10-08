package com.pyeonrimium.queuing.reservation.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.reservation.daos.ReservationDao;
import com.pyeonrimium.queuing.reservation.daos.StoreDao;
import com.pyeonrimium.queuing.reservation.domains.MyReservationResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateResponse;

@Service
public class ReservationService {
	@Autowired
	private ReservationDao reservationDao;

    @Autowired
    private StoreDao storeDao;
    
	// 예약 신청 처리
	public ReservationResponse createReservation(long userId, ReservationRequest reservationRequest) {
		// 예약 번호 만들기
		String reservationNumber = makeReservationNumber(reservationRequest);
		
		// 식당 아이디 확인
		System.out.println("[ReservationService] storeId: " + reservationRequest.getStoreId());
		
		String storeName = storeDao.getStoreName(reservationRequest.getStoreId());
		
		if (storeName == null) {
			return ReservationResponse.builder()
					.isSuccess(false)
					.message("가게이름을 찾지 못했습니다.")
					.build();
		}
		

		// ReservatinEntity 생성
		ReservationEntity reservationEntity = ReservationEntity.builder()
				.userId(userId)
				.storeId(reservationRequest.getStoreId())
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

	// storeId를 통해서 storeName 가져오기
	public String getStoreName(long storeId) {
		return storeDao.getStoreName(storeId);
	}
	
	
	
	//userId를 통해서 user의 예약 정보 불러오기
	public List<ReservationEntity> getmyReservation(long userId) {
		return reservationDao.getReservationsByUserId(userId);
	}
	
	// TODO :예약 정보 열람 신청 처리
		public MyReservationResponse getMyReservation(Long userId) {
			
			// TODO: userId 확인
//			 List<ReservationEntity> getReservations(Long userId) {
//				return reservationDao.getReservationsByUserId(userId);
//			System.out.println("[ReservationService] getMyReservation: " + "");
//			    }
			
			if (userId == null) {
				return MyReservationResponse.builder()
						.isSuccess(false)
						.message("예약을 찾지 못했습니다.")
						.build();
			}
			
			return null;
		}
		
		/**
		 * 예약 정보 불러오기
		 * @param userId 유저 고유 번호
		 * @return 예약 정보 목록
		 */
		public List<ReservationEntity> getReservations(long userId) {
			return reservationDao.getReservationsByUserId(userId);
		}
		
		
		
		
		// TODO : 예약 수정 처리
		public ReservationUpdateResponse updateReservations(ReservationUpdateRequest request){
			ReservationEntity reservationEntity = reservationDao.findByReservationNumber(request.getReservationNumber());
			
			if (reservationEntity == null) {
				return ReservationUpdateResponse.builder()
						.isSuccess(false)
						.message("예약 수정을 할 수 없습니다.")
						.build();
			}
			
			// 정보 업데이트
			reservationEntity.setModifiedAt(LocalDateTime.now());
			reservationEntity.setReservationNumber(request.getReservationNumber());
			reservationEntity.setPartySize(request.getPartySize());
			reservationEntity.setRequest(request.getRequest());
			
			// DAO에 업데이트 요청
			boolean isSuccess = reservationDao.updateReservation(request);
			
			if (!isSuccess) {
				return ReservationUpdateResponse.builder()
						.isSuccess(false)
						.message("예약 수정을 할 수 없습니다.")
						.build();
			}
			
				return ReservationUpdateResponse.builder()
					.isSuccess(true)
					.build();
			}



		public ReservationEntity findReservation(Long reservationId) {
			//TODO:DAO로 reservationId를 통해 예약 호출
			return reservationDao.getReservationsByReservationId(reservationId);
		}

	
		// TODO: 예약 삭제(D)
//		public List<reservationRequest> listupRes(){
//		System.out.println("[ReservationService] listipRes()");
//		
//		return ReservationDao.selectRes();
//	}
}
