package com.pyeonrimium.queuing.stores.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//model에 저장
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreUpdateResponse {
	private boolean isSuccess;
	private String message;
	private String redirectUrl;
	
	private Long storeId;
}
