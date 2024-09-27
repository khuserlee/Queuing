package com.pyeonrimium.queuing.stores.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;

@Service
public class StoreService {
	
	// StoreDao 연결
	@Autowired
	private StoreDao storeDao;
	
	
	public StoreEntity findStore(Long storeId) {
		System.out.println("[StoreService] findStore()");
		
		// storeDao에 가게를 찾아달라고 요청
		// 결과 받아오기
		StoreEntity storeEntity = storeDao.findStore(storeId);
		
		return storeEntity;
	}
	
}
