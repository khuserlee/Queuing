package com.pyeonrimium.queuing.maps.domains.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyStore {

	private long storeId;
	private String name;
	private String address;
	private BigDecimal longitude;
	private BigDecimal latitude;

}
