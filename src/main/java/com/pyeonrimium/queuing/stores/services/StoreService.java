package com.pyeonrimium.queuing.stores.services;


import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.stores.controllers.StoresControllers;
import com.pyeonrimium.queuing.stores.daos.StoreDao;
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
		System.out.println("[storeService] addStore()");
        //return null;
		
		// TODO: 매장 정보 등록
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
		
		System.out.println("storeId: " + result.getStoreId());
		
		// 3. StoreRegistrationResponse 생성
		// 4. 2.의 결과를 StoreRegistrationResponse에 저장
		StoreRegistrationResponse storeRegistrationResponse = StoreRegistrationResponse.builder()
				.isSuccess(true)
				.storeId(result.getStoreId())
				.build();
		
		// 5.StoreRegistrationResponse 반환
		return storeRegistrationResponse;
	}
	
	
	public StoreFindResponse findStore(Long storeId) {
		System.out.println("[StoreService] findStore()");
		
		// storeDao에 가게를 찾아달라고 요청
		// 결과 받아오기
		StoreEntity result = storeDao.findStore(storeId);
		
		if(result == null) {
			return StoreFindResponse.builder()
					.isSuccess(false)
					.message("조회 실패")
					.build();
		}
		
		StoreFindResponse storeFindResponse = StoreFindResponse.builder()
				.isSuccess(true)
				.storeId(result.getStoreId())
				.name(result.getName())
				.address(result.getAddress())
				.description(result.getDescription())
				.phone(result.getPhone())
				.startTime(result.getStartTime())
				.endTime( result.getEndTime())
				.closedDay(result.getClosedDay())
				.build();

		//StoreFindResponse 반환
		return storeFindResponse;
	}
	
	public String updateStore(Long storeId) {
		System.out.println("[storeService] updateStore()");
		return null;
		
	}

}
