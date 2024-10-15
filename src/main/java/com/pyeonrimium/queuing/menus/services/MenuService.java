package com.pyeonrimium.queuing.menus.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.MenuDao;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuListResponse;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationRequest;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationResponse;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;
import com.pyeonrimium.queuing.stores.daos.StoreDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
	
	private final StoreDao storeDao;
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
				.storeId(storeId)
				.menus(menus)
				.build();
	}

	public MenuRegistrationResponse addNewMenu(Long storeId, Long userId, MenuRegistrationRequest request) {
		
		Long id = storeDao.findStoreByUserId(userId);
		if (!storeId.equals(id)) {
			return MenuRegistrationResponse.builder()
					.isSuccess(false)
					.message("메뉴를 등록할 수 없습니다.")
					.build();
		}
		
		Menu menu = Menu.builder()
				.storeId(storeId)
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.menuOrder(0)
				.build();
		
		boolean isSuccess = menuDao.insertNewMenu(menu);
		
		if (!isSuccess) {
			return MenuRegistrationResponse.builder()
					.isSuccess(false)
					.message("메뉴를 등록할 수 없습니다.")
					.build();
		}
		
		return MenuRegistrationResponse.builder()
				.isSuccess(true)
				.build();
	}

	
}
