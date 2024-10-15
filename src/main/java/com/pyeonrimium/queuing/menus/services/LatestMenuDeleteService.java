package com.pyeonrimium.queuing.menus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.LatestMenuDeleteDao;
import com.pyeonrimium.queuing.menus.daos.MenuDao;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuDeleteResponse;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;
import com.pyeonrimium.queuing.stores.daos.StoreDao;

@Service
public class LatestMenuDeleteService {
	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private MenuDao menuDao;

	// 이전 코드
//	@Autowired
//	private LatestMenuDeleteDao menuDAO;
//
//	public void deleteMenu(Long selectedMenuId) {
//		menuDAO.deleteMenu(selectedMenuId);
//	}


	/**
	 * 메뉴 삭제하기
	 * @param storeId 가게 고유 번호
	 * @param userId 유저 고유 번호
	 * @param menuId 메뉴 고유 번호
	 * @return
	 */
	public MenuDeleteResponse deleteMenu(Long storeId, Long userId, Long menuId) {
		
		// 내 가게인지 확인(권한 확인)
		Long id = storeDao.findStoreIdByUserId(userId);
		
		if (!storeId.equals(id)) {
			return MenuDeleteResponse.builder()
					.message("권한이 없습니다.")
					.httpStatus(HttpStatus.FORBIDDEN)	// 403 에러
					.redirectUrl("/queuing/stores/" + storeId)
					.build();
		}

		// 메뉴 삭제
		boolean isSuccess = menuDao.deleteMenu(menuId);
		
		if (!isSuccess) {
			return MenuDeleteResponse.builder()
					.httpStatus(HttpStatus.NOT_FOUND)	// 404 에러
					.message("메뉴를 삭제할 수 없습니다.")
					.redirectUrl("/queuing/menu/" + storeId)
					.build();
		}
		
		return MenuDeleteResponse.builder()
				.httpStatus(HttpStatus.OK)	// 성공
				.message("메뉴를 삭제했습니다.")
				.redirectUrl("/queuing/menu/" + storeId)
				.build();
	}
}
