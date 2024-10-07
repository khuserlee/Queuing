package com.pyeonrimium.queuing.maps.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.maps.domains.dtos.NearbyStore;
import com.pyeonrimium.queuing.maps.domains.dtos.NearbyStoreResponse;
import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;

@Service
public class MapService {
	
	@Autowired
	private StoreDao storeDao;

	private final int SEARCH_STORE_RADIUS = 500;
	
	/**
	 * 주어진 위도, 경도를 기준으로 가까운 가게 검색
	 * @param latitude 위도
	 * @param longitude 경도
	 * @return 조회된 가게 목록
	 */
	public NearbyStoreResponse getNearbyStores(BigDecimal latitude, BigDecimal longitude) {
		List<StoreEntity> stores = storeDao.getNearbyStores(latitude, longitude, SEARCH_STORE_RADIUS);
		
		if (stores == null) {
			return NearbyStoreResponse.builder()
					.isSuccess(false)
					.message("서버에서 오류가 발생했습니다.")
					.build();
		}
		
		// 조회된 데이터들을 응답용 데이터로 전환
		List<NearbyStore> nearbyStores = stores.stream()
				.map(store -> NearbyStore.builder()
							.storeId(store.getStoreId())
							.name(store.getName())
							.address(store.getAddress())
							.longitude(store.getLongitude())
							.latitude(store.getLatitude())
							.build()
				).collect(Collectors.toList());
		
		return NearbyStoreResponse.builder()
				.isSuccess(true)
				.nearbyStores(nearbyStores)
				.build();
	}
}
