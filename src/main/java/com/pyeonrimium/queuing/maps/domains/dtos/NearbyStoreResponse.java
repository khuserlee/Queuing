package com.pyeonrimium.queuing.maps.domains.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyStoreResponse {

	private boolean isSuccess;
	private String message;
	
	private List<NearbyStore> nearbyStores;
	
}
