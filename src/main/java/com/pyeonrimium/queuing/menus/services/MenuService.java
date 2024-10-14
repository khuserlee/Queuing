package com.pyeonrimium.queuing.menus.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.MenuDao;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuListResponse;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
	
	private final MenuDao menuDao;

	public MenuListResponse getMenusByStoreId(Long storeId) {
		
		List<Menu> menus = menuDao.findMenusByStoreId(storeId);
		
		if (menus == null) {
			return MenuListResponse.builder()
					.isSuccess(false)
					.message("메뉴를 조회할 수 없습니다.")
					.build();
		}
		
		return MenuListResponse.builder()
				.isSuccess(true)
				.menus(menus)
				.build();
	}

	
}
