package com.pyeonrimium.queuing.stores.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreImageUploadResponse {

	private boolean isSuccess;
	private String message;
	private String redirectUrl;
}
