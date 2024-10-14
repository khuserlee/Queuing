package com.pyeonrimium.queuing.menus.domains.dtos;

import java.util.List;

import com.pyeonrimium.queuing.menus.domains.entities.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuListResponse {

	private boolean isSuccess;
	private String message;
	
	private List<Menu> menus;
}
