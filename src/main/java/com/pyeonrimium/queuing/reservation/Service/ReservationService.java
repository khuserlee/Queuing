package com.pyeonrimium.queuing.reservation.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.reservation.daos.ReservationDao;
import com.pyeonrimium.queuing.reservation.domains.MyReservationResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationDeleteResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateResponse;
import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.users.services.UserService;

@Service
public class ReservationService {
	@Autowired
	private ReservationDao reservationDao;

	@Autowired
	private UserService userService;
	
    @Autowired
    private StoreDao storeDao;
    
	// 예약 신청 처리
	public ReservationResponse createReservation(long userId, ReservationRequest reservationRequest) {
		
		// 예약 번호 만들기
		String reservationNumber = makeReservationNumber(reservationRequest.getReservationDate(),
													reservationRequest.getReservationTime());
		
		if (reservationNumber == null) {
			return ReservationResponse.builder()
					.isSuccess(false)
					.message("예약 날짜와 시간을 선택하세요.")
					.storeId(reservationRequest.getStoreId())
					.build();
		}
		
		String userName = userService.getUserName(userId);
		
		if (userName == null) {
			return ReservationResponse.builder()
					.isSuccess(false)
					.message("예약에 실패했습니다.")
					.storeId(reservationRequest.getStoreId())
					.build();
		}
		
		// 같은 날, 시간에 예약이 있는지 확인
		boolean isExisted = reservationDao.checkReservation(reservationRequest.getStoreId(),
									reservationRequest.getReservationDate(),
									reservationRequest.getReservationTime());
		
		if (isExisted) {
			return ReservationResponse.builder()
					.isSuccess(false)
					.message("이미 예약이 있습니다. 다른 날짜 또는 시간을 선택해주세요.")
					.storeId(reservationRequest.getStoreId())
					.build();
		}

		// ReservatinEntity 생성
		ReservationEntity reservationEntity = ReservationEntity.builder()
				.userId(userId)
				.storeId(reservationRequest.getStoreId())
				.userName(userName)
				.storeName(reservationRequest.getStoreName())
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
					.storeId(reservationRequest.getStoreId())
					.build();
		}
		
		return ReservationResponse.builder()
				.isSuccess(true)
				.message("예약에 성공했습니다.")
				.userName(reservationEntity.getUserName())
				.storeId(reservationEntity.getStoreId())
				.storeName(reservationEntity.getStoreName())
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
	private String makeReservationNumber(LocalDate date, LocalTime time) {
		
		String reservationNumber = null;
		
		try {
			String strDate = date.toString().replace("-", "");
			String strTime = time.toString().replace(":", "");
			
			reservationNumber = strDate + strTime;
			
		} catch (Exception e) {
			System.out.println("Error: 예약번호를 만들 수 없습니다. => " + e);
		}
		
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
			String number = makeReservationNumber(request.getReservationDate(), request.getReservationTime());
			ReservationEntity reservationEntity = reservationDao.findByReservationNumber(number);
			
			if (reservationEntity == null) {
				return ReservationUpdateResponse.builder()
						.isSuccess(false)
						.message("예약 수정을 할 수 없습니다. 111")
						.build();
			}
			
			// 정보 업데이트
			reservationEntity.setReservationNumber(number);
			reservationEntity.setReservationDate(request.getReservationDate());
			reservationEntity.setReservationTime(request.getReservationTime());
			reservationEntity.setPartySize(request.getPartySize());
			reservationEntity.setRequest(request.getRequest());
			
			// DAO에 업데이트 요청
			boolean isSuccess = reservationDao.updateReservation(reservationEntity);
			
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
		//DAO로 reservationId를 통해 예약 호출
			return reservationDao.getReservationsByReservationId(reservationId);
		}

		// TODO: 예약 삭제(D)
		
//		public ReservationEntity deleteReservation(String reservationNumber) {
//			return reservationDao.deleteReservationByNumber(reservationNumber);
//		}
		
		public ReservationDeleteResponse deleteReservation(String reservationNumber) {
			
			// 메뉴 삭제
			boolean isSuccess = reservationDao.deleteReservationByNumber(reservationNumber);
			
			if (!isSuccess) {
				return ReservationDeleteResponse.builder()
						.httpStatus(HttpStatus.BAD_REQUEST)
						.message("메뉴를 삭제할 수 없습니다.")
						.build();
			}

			return ReservationDeleteResponse.builder()
					.httpStatus(HttpStatus.OK)
					.message("메뉴를 삭제했습니다.")
					.redirectUrl("/queuing/reservations/pageNo=1")
					.build();
		}
}
