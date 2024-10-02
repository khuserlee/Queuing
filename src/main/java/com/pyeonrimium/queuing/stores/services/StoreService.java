package com.pyeonrimium.queuing.stores.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegisterationRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegistrationResponse;
import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;

@Service
public class StoreService {
	
	//StoreDao 연결
	@Autowired
	private StoreDao storeDao;
	
	//등록위한 dao연결성
	public String addStore(StoreRegisterationRequest storeRegisterationRequest) {
		System.out.println("[storeService] addStore()");
		return null;
		
		// TODO: 매장 정보 등록
		// 1. StoreEntity 생성
		
		// 2. DAO에 StoreEntity를 전달
		// DAO에서는 전달받은 StoreEntity를 DB에 저장
		// 결과를 반환
		
		// 3. StoreRegistrationResponse 생성
		// 4. 2.의 결과를 StoreRegistrationResponse에 저장
		
		// 5.StoreRegistrationResponse 반환
		
	}
	public StoreEntity findStore(Long storeId) {
		System.out.println("[StoreService] findStore()");
		
		// storeDao에 가게를 찾아달라고 요청
		// 결과 받아오기
		StoreEntity storeEntity = storeDao.findStore(storeId);
		return storeEntity;
	}
	
	public String updateStore(Long storeId) {
		System.out.println("[storeService] updateStore()");
		return null;
		
	}

}
