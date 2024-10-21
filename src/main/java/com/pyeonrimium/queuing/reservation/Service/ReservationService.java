package com.pyeonrimium.queuing.reservation.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.reservation.daos.ReservationDao;
import com.pyeonrimium.queuing.reservation.domains.ReservationDeleteResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateResponse;
import com.pyeonrimium.queuing.reservation.domains.dtos.MyReservation;
import com.pyeonrimium.queuing.reservation.domains.dtos.MyReservationListResponse;
import com.pyeonrimium.queuing.reservation.domains.dtos.ReservationEditFormResponse;
import com.pyeonrimium.queuing.reservation.domains.dtos.ReservationEditRequest;
import com.pyeonrimium.queuing.reservation.domains.dtos.ReservationEditResponse;
import com.pyeonrimium.queuing.reservation.domains.dtos.StoreReservation;
import com.pyeonrimium.queuing.reservation.domains.dtos.StoreReservationResponse;
import com.pyeonrimium.queuing.sms.services.SmsService;
import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.users.domains.entities.UserEntity;
import com.pyeonrimium.queuing.users.services.UserService;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationDao reservationDao;

	@Autowired
	private UserService userService;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private SmsService smsService;


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
		
		// 유저 이름 확인
		UserEntity userEntity = userService.findUser(userId);

		if (userEntity == null) {
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
				.userName(userEntity.getName())
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
		
		// 문자 메시지 보내기
		smsService.sendReservationConfirmMessage(userEntity.getPhone(), reservationEntity);
		
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


	/**
	 * 나의 예약 정보 불러오기(페이지네이션 적용)
	 * @param userId 유저 고유 번호
	 * @param pageNo 조회할 페이지 번호
	 * @return 처리 결과
	 */
	public MyReservationListResponse getMyReservations(Long userId, Integer pageNo) {
		
		// 한 페이지에 보여줄 예약 정보 개수
		final int pageSize = 10;
		
		// 예약 정보 조회하기
		List<ReservationEntity> results = reservationDao.findAllByUserId(userId, pageSize, pageNo);
		
		if (results == null) {
			return MyReservationListResponse.builder()
					.httpStatus(HttpStatus.BAD_REQUEST)
					.message("예약 목록을 조회할 수 없습니다.")
					.build();
		}
		
		List<MyReservation> reservations = new ArrayList<>();
		
		for (ReservationEntity entity : results) {
			MyReservation reservation = MyReservation.builder()
											.reservationId(entity.getReservationId())
											.storeId(entity.getStoreId())
											.userName(entity.getStoreName())
											.storeName(entity.getStoreName())
											.reservationNumber(entity.getReservationNumber())
											.reservationDate(entity.getReservationDate())
											.reservationTime(entity.getReservationTime())
											.partySize(entity.getPartySize())
											.request(entity.getRequest())
											.status(entity.getStatus())
											.build();
			
			reservations.add(reservation);
		}
		
		int count = reservationDao.countMyReservations(userId);
		
		// 마지막 페이지 번호
		int lastPageNo = (count == pageSize)
							? 1
							: (int) Math.ceil((double) count / (pageSize - 1));
		
		// 화면에 표시될 시작 페이지 번호
		int startPageNo = Math.max(1, pageNo - 2);
		
		// 화면에 표시될 끝 페이지 번호
		int endPageNo = Math.min(lastPageNo, pageNo + 2);

		return MyReservationListResponse.builder()
				.httpStatus(HttpStatus.OK)
				.startPageNo(startPageNo)
				.endPageNo(endPageNo)
				.lastPageNo(lastPageNo)
				.currentPageNo(pageNo)
				.reservations(reservations)
				.build();
	}


	/**
	 * 예약 수정 화면에서 사용할 예약 정보 불러오기
	 * @param reservationId 예약 고유 번호
	 * @param userId 유저 고유 번호
	 * @return 조회된 예약 정보
	 */
	public ReservationEditFormResponse getReservationForEdit(Long reservationId, Long userId) {
		ReservationEntity reservationEntity = reservationDao.getReservationsByReservationId(reservationId);
		
		if (reservationEntity == null) {
			return ReservationEditFormResponse.builder()
					.isSuccess(false)
					.message("예약 정보를 확인할 수 없습니다.")
					.build();
		}
		
		return ReservationEditFormResponse.builder()
				.isSuccess(true)
				.storeId(reservationEntity.getStoreId())
				.storeName(reservationEntity.getStoreName())
				.reservationId(reservationEntity.getReservationId())
				.reservationDate(reservationEntity.getReservationDate())
				.reservationTime(reservationEntity.getReservationTime())
				.partySize(reservationEntity.getPartySize())
				.request(reservationEntity.getRequest())
				.build();
	}

	
	/**
	 * 예약 정보 불러오기
	 * @param userId 유저 고유 번호
	 * @return 예약 정보 목록
	 */
	public List<ReservationEntity> getReservations(long userId) {
		return reservationDao.getReservationsByUserId(userId);
	}
	
	
	/**
	 * 수정된 예약 정보 적용하기
	 * @param request 수정된 예약 정보
	 * @param userId 유저 고유 번호
	 * @return 처리 결과
	 */
	public ReservationEditResponse updateMyReservation(ReservationEditRequest request, Long userId) {
		
		// 예약 정보 조회하기
		ReservationEntity reservationEntity = reservationDao.findReservation(request.getReservationId(), userId);
		
		if (reservationEntity == null) {
			return ReservationEditResponse.builder()
					.isSuccess(false)
					.message("예약을 수정할 수 없습니다.")
					.build();
		}
		
		// 유저 이름 확인
		UserEntity userEntity = userService.findUser(userId);

		if (userEntity == null) {
			return ReservationEditResponse.builder()
					.isSuccess(false)
					.message("예약을 수정할 수 없습니다.")
					.build();
		}

		// 정보 업데이트
		reservationEntity.setPartySize(request.getPartySize());
		reservationEntity.setRequest(request.getRequest());
		
		// DAO에 업데이트 요청
		boolean isSuccess = reservationDao.updateReservation(reservationEntity);
		
		if (!isSuccess) {
			return ReservationEditResponse.builder()
					.isSuccess(false)
					.message("예약 수정을 할 수 없습니다.")
					.build();
		}

		// 문자 메시지 보내기
		smsService.sendReservationUpdateMessage(userEntity.getPhone(), reservationEntity);
		
		return ReservationEditResponse.builder()
			.isSuccess(true)
			.message("예약을 수정했습니다.")
			.redirectUrl("/queuing/users/profile")
			.build();
	}
	
	
	// 예약 수정 처리
	public ReservationUpdateResponse updateReservations(ReservationUpdateRequest request){
		String number = makeReservationNumber(request.getReservationDate(), request.getReservationTime());
		ReservationEntity reservationEntity = reservationDao.findByReservationNumber(number);
		
		if (reservationEntity == null) {
			return ReservationUpdateResponse.builder()
					.isSuccess(false)
					.message("예약 수정을 할 수 없습니다.")
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

	
	/**
	 * 예약 정보 삭제하기
	 * @param reservationNumber 예약번호
	 * @return 처리 결과
	 */
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


	public StoreReservationResponse getStoreReservations(Long userId, Integer pageNo) {
		
		Long storeId = storeDao.findStoreIdByUserId(userId);
		
		if (storeId == null) {
			return StoreReservationResponse.builder()
					.isSuccess(false)
					.message("예약 목록을 불러올 수 없습니다.")
					.build();
		}
		
		// 예약 목록 조회
		final int pageSize = 10;
		List<ReservationEntity> reservations = reservationDao.findAllReservationsOfStore(storeId, pageSize, pageNo);
		
		if (reservations == null) {
			return StoreReservationResponse.builder()
					.isSuccess(false)
					.message("예약 목록을 불러올 수 없습니다.")
					.build();
		}
		
		// 데이터 옮기기
		List<StoreReservation> results = new ArrayList<>();
		for (ReservationEntity reservation : reservations) {
			StoreReservation storeReservation = StoreReservation.builder()
					.reservationId(reservation.getReservationId())
					.userName(reservation.getUserName())
					.reservationNumber(reservation.getReservationNumber())
					.reservationDate(reservation.getReservationDate())
					.reservationTime(reservation.getReservationTime())
					.partySize(reservation.getPartySize())
					.request(reservation.getRequest())
					.status(reservation.getStatus())
					.build();
			
			results.add(storeReservation);
		}
		
		// 가게 이름 확인
		String storeName = storeDao.getStoreName(storeId);
		
		// 가게의 전체 예약 개수 확인
		int count = reservationDao.countStoreReservations(storeId);
		
		// 마지막 페이지 번호
		int lastPageNo = (count == pageSize)
							? 1
							: (int) Math.ceil((double) count / (pageSize - 1));
		
		// 화면에 표시될 시작 페이지 번호
		int startPageNo = Math.max(1, pageNo - 2);
		
		// 화면에 표시될 끝 페이지 번호
		int endPageNo = Math.min(lastPageNo, pageNo + 2);
		
		return StoreReservationResponse.builder()
				.isSuccess(true)
				.message("예약 목록 조회에 성공했습니다.")
				.storeName(storeName)
				.reservations(results)
				.startPageNo(startPageNo)
				.endPageNo(endPageNo)
				.currentPageNo(pageNo)
				.lastPageNo(lastPageNo)
				.build();
	}
}
