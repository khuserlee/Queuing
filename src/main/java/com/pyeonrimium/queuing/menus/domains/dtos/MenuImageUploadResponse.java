package com.pyeonrimium.queuing.menus.domains.dtos;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;

import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuImageUploadResponse {

	private boolean isSuccess;
	private String message;
	private String redirectUrl;
}
