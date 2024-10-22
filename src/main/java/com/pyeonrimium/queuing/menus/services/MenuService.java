package com.pyeonrimium.queuing.menus.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pyeonrimium.queuing.menus.daos.MenuDao;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuListResponse;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationRequest;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationResponse;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuUpdateFormResponse;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;
import com.pyeonrimium.queuing.stores.daos.StoreDao;
import com.pyeonrimium.queuing.utils.services.UploadFileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
	
	private final StoreDao storeDao;
	private final MenuDao menuDao;
	private final UploadFileService uploadFileService;

	/**
	 * 가게 메뉴 목록 불러오기
	 * @param storeId 가게 고유 번호
	 * @return 메뉴 목록
	 */
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

	/**
	 * 신규 메뉴 등록하기
	 * @param storeId 가게 고유 번호
	 * @param userId 유저 고유 번호
	 * @param request 메뉴 정보
	 * @return
	 */
	public MenuRegistrationResponse addNewMenu(Long storeId,
												Long userId,
												MenuRegistrationRequest request) {
		
		// 내 가게인지 확인(권한 확인)
		Long id = storeDao.findStoreIdByUserId(userId);
		if (!storeId.equals(id)) {
			return MenuRegistrationResponse.builder()
					.isSuccess(false)
					.message("메뉴를 등록할 수 없습니다.")
					.redirectUrl("/queuing/menu/" + storeId)
					.build();
		}
		
		// 메뉴 엔터티 생성
		Menu menu = Menu.builder()
				.storeId(storeId)
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.menuOrder(0)
				.build();
		
		Menu entity = menuDao.insertNewMenu(menu);
		
		if (entity == null) {
			return MenuRegistrationResponse.builder()
					.isSuccess(false)
					.message("메뉴를 등록할 수 없습니다.")
					.redirectUrl("/queuing/menu/" + storeId)
					.build();
		}
		
		return MenuRegistrationResponse.builder()
				.isSuccess(true)
				.menuId(entity.getMenuId())
				.build();
	}

	/**
	 * 메뉴 정보 불러오기
	 * @param storeId 가게 고유 번호
	 * @param userId 유저 고유 번호
	 * @param menuId 메뉴 고유 번호
	 * @return
	 */
	public MenuUpdateFormResponse getMenuForUpdate(Long storeId, Long userId, Long menuId) {
		
		// 가게 주인인지 확인
		Long myStoreId = storeDao.findStoreIdByUserId(userId);
		
		if (!storeId.equals(myStoreId)) {
			return MenuUpdateFormResponse.builder()
					.isSuccess(false)
					.message("권한이 없습니다.")
					.build();
		}
		
		// 메뉴 데이터 조회
		Menu menu = menuDao.findMenuByMenuId(menuId);
		
		if (menu == null) {
			// 조회 실패
			return MenuUpdateFormResponse.builder()
					.isSuccess(false)
					.message("해당 메뉴를 찾을 수 없습니다.")
					.build();
		}
		
		// 조회 성공
		return MenuUpdateFormResponse.builder()
				.isSuccess(true)
				.menuId(menu.getMenuId())
				.storeId(menu.getStoreId())
				.name(menu.getName())
				.price(menu.getPrice())
				.description(menu.getDescription())
				.build();
	}

	public boolean uploadImage(Long storeId, Long menuId, MultipartFile file) {
		
		if (file == null) {
			return false;
		}
		
		Menu menu = this.menuDao.findMenuByMenuId(menuId);
		
		if (menu == null) {
			return false;
		}

		// 이미지 저장
		String savedFile = uploadFileService.upload(file);
		
		if (savedFile == null) {
			return false;
		}
		
		menu.setMenuFile(savedFile);
		
		return this.menuDao.updateMenu(menu);
	}

	
}
