package com.pyeonrimium.queuing.stores.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreEditRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreEditResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreFindResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegisterationRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegistrationResponse;
import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;

@Service
public class StoreService {
	
	//StoreDao 연결
	@Autowired
	private StoreDao storeDao;
	
	//등록위한 dao연결성
	public StoreRegistrationResponse addStore(StoreRegisterationRequest storeRegisterationRequest, Long userId) {

		// 1. StoreEntity 생성
		StoreEntity storeEntity = StoreEntity.builder()
				.userId(userId)
				.name(storeRegisterationRequest.getName())
				.description(storeRegisterationRequest.getDescription())
				.address(storeRegisterationRequest.getAddress())
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
		Long storeId = storeDao.findStoreByUserId(userId);
		return storeId;
	}
	
	public String updateStore(Long storeId) {
		System.out.println("[storeService] updateStore()");
		return null;
		
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
		
		if (userId != storeEntity.getUserId()) {
			return StoreEditResponse.builder()
					.isSuccess(false)
					.message("잘못된 접근입니다.")
					.build();
		}
		
		return null;
	}

}
