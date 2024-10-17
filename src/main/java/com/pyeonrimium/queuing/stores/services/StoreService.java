package com.pyeonrimium.queuing.stores.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreDeleteRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreDeleteResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreEditRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreEditResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreFindResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegisterationRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegistrationResponse;
import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;
import com.pyeonrimium.queuing.users.controllers.UserService;
import com.pyeonrimium.queuing.users.domains.entities.UserEntity;

@Service
public class StoreService {
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 신규 매장 정보 등록
	 * @param storeRegisterationRequest 신규 매장 정보
	 * @param userId 유저 고유 번호
	 * @return 결과
	 */
	public StoreRegistrationResponse addStore(StoreRegisterationRequest storeRegisterationRequest, Long userId) {

		// 1. StoreEntity 생성
		StoreEntity storeEntity = StoreEntity.builder()
				.userId(userId)
				.name(storeRegisterationRequest.getName())
				.description(storeRegisterationRequest.getDescription())
				.roadAddress(storeRegisterationRequest.getRoadAddress())
				.detailAddress(storeRegisterationRequest.getDetailAddress())
				.phone(storeRegisterationRequest.getPhone())
				.startTime(storeRegisterationRequest.getStartTime())
				.endTime(storeRegisterationRequest.getEndTime())
				.closedDay(storeRegisterationRequest.getClosedDay())
				.longitude(storeRegisterationRequest.getLongitude())
				.latitude(storeRegisterationRequest.getLatitude())
				.build();
		
		// 2. DAO에 StoreEntity를 전달
		// DAO에서는 전달받은 StoreEntity를 DB에 저장
		// 결과를 반환
		StoreEntity result = storeDao.addStore(storeEntity);
		
		if (result == null) {
			return StoreRegistrationResponse.builder()
					.isSuccess(false)
					.message("등록에 실패했습니다.")
					.build();
		}
		
		// 3. StoreRegistrationResponse 생성
		// 4. 2.의 결과를 StoreRegistrationResponse에 저장
		StoreRegistrationResponse storeRegistrationResponse = StoreRegistrationResponse.builder()
				.isSuccess(true)
				.storeId(result.getStoreId())
				.build();
		
		// 5.StoreRegistrationResponse 반환
		return storeRegistrationResponse;
	}
	
	/**
	 * 가게 정보 불러오기
	 * @param storeId 가게 고유 번호
	 * @return 조회 결과
	 */
	public StoreFindResponse findStore(Long storeId) {
		// storeId로 가게 정보 조회
		StoreEntity storeEntity = storeDao.findStoreByStoreId(storeId);
		
		// 조회 실패
		if (storeEntity == null) {
			return StoreFindResponse.builder()
					.isSuccess(false)
					.message("가게 정보를 불러올 수 없습니다.")
					.build();
		}
		
		// 조회 성공
		return StoreFindResponse.builder()
				.isSuccess(true)
				.storeId(storeEntity.getStoreId())
				.userId(storeEntity.getUserId())
				.name(storeEntity.getName())
				.address(storeEntity.getAddress())
				.roadAddress(storeEntity.getRoadAddress())
				.detailAddress(storeEntity.getDetailAddress())
				.description(storeEntity.getDescription())
				.phone(storeEntity.getPhone())
				.startTime(storeEntity.getStartTime())
				.endTime(storeEntity.getEndTime())
				.closedDay(storeEntity.getClosedDay())
				.build();
	}

	/**
	 * 가게 정보 불러오기
	 * @param storeId 가게 고유 번호
	 * @param userId 유저 고유 번호
	 * @return 조회 결과
	 */
	public StoreFindResponse findStore(Long storeId, Long userId) {
		StoreFindResponse storeFindResponse = findStore(storeId);
		
		if (!storeFindResponse.isSuccess()) {
			return storeFindResponse;
		}
		
		if (userId != storeFindResponse.getUserId()) {
			return StoreFindResponse.builder()
					.isSuccess(false)
					.message("가게 정보를 불러올 수 없습니다.")
					.build();
		}
		
		return storeFindResponse;
	}

	/**
	 * 등록한 가게 고유 번호 조회
	 * @param userId 유저 고유 번호
	 * @return 가게 고유 번호
	 */
	public Long getMyStoreId(Long userId) {
		Long storeId = storeDao.findStoreIdByUserId(userId);
		return storeId;
	}


	public StoreEditResponse editStore(long storeId, Long userId, StoreEditRequest storeEditRequest) {

		// storeId로 가게 정보 조회
		StoreEntity storeEntity = storeDao.findStoreByStoreId(storeId);
		
		// 조회 실패
		if (storeEntity == null) {
			return StoreEditResponse.builder()
					.isSuccess(false)
					.message("가게 정보를 불러올 수 없습니다.")
					.build();
		}
		
		if (!userId.equals(storeEntity.getUserId())) {
			System.out.println("userId: " + userId + ", id: " + storeEntity.getUserId());
			return StoreEditResponse.builder()
					.isSuccess(false)
					.message("잘못된 접근입니다.")
					.build();
		}
		
		// 데이터 업데이트
		storeEntity.setName(storeEditRequest.getName());
		storeEntity.setRoadAddress(storeEditRequest.getRoadAddress());
		storeEntity.setDetailAddress(storeEditRequest.getDetailAddress());
		storeEntity.setDescription(storeEditRequest.getDescription());
		storeEntity.setPhone(storeEditRequest.getPhone());
		
		storeEntity.setStartTime(storeEditRequest.getStartTime());
		storeEntity.setEndTime(storeEditRequest.getEndTime());
		storeEntity.setClosedDay(storeEditRequest.getClosedDay());
		
		storeEntity.setLongitude(storeEditRequest.getLongitude());
		storeEntity.setLatitude(storeEditRequest.getLatitude());
		
		// 저장
		boolean isSuccess = storeDao.update(storeEntity);
		
		if (!isSuccess) {
			return StoreEditResponse.builder()
					.isSuccess(false)
					.message("가게 정보를 수정할 수 없습니다.")
					.build();
		}

		return StoreEditResponse.builder()
				.isSuccess(true)
				.storeId(storeId)
				.build();
	}

	/**
	 * 스토어 삭제
	 * @param storeDeleteRequest
	 * @param storeId
	 * @param userId
	 * @return
	 */
	public StoreDeleteResponse deleteStore(Long storeId, Long userId) {
		
		StoreEntity storeEntity = storeDao.findStoreByStoreId(storeId);
		
		// 내 가게인지 확인
		boolean isValid = storeEntity != null && userId.equals(storeEntity.getUserId());
		if (!isValid) {
			return StoreDeleteResponse.builder()
					.isSuccess(false)
					.message("매장을 삭제할 수 없습니다.")
					.build();
		}
		
		// 삭제
		boolean isSuccess = storeDao.deleteStore(storeEntity);
		if (!isSuccess) {
			return StoreDeleteResponse.builder()
					.isSuccess(false)
					.message("매장을 삭제할 수 없습니다.")
					.build();
		}
		
		return StoreDeleteResponse.builder()
				.isSuccess(true)
				.message("매장을 삭제했습니다.")
				.build();
	}

}
