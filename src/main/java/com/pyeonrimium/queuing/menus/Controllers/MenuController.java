package com.pyeonrimium.queuing.menus.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuDeleteResponse;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuListResponse;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationRequest;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationResponse;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuUpdateFormResponse;
import com.pyeonrimium.queuing.menus.services.ForUpdatemenuJspService;
import com.pyeonrimium.queuing.menus.services.LatestMenuDeleteService;
import com.pyeonrimium.queuing.menus.services.LatestMenuPostService;
import com.pyeonrimium.queuing.menus.services.LatestMenuUpdateService;
import com.pyeonrimium.queuing.menus.services.MenuService;

@Controller
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	

	@Autowired
	LatestMenuDeleteService latestMenuDeleteService;

	@Autowired
	ForUpdatemenuJspService forUpdatemenuJspService;

	@Autowired
	LatestMenuPostService latestMenuPostService;

	@Autowired
	private LatestMenuUpdateService latestMenuUpdateService;
	
	
	/**
	 * 가게 메뉴 목록 조회하기
	 * @param storeId 가게 고유 번호
	 * @param session 세션
	 * @param model
	 * @return 메뉴 목록 페이지
	 */
	@GetMapping("/menu/{storeId}")
	public String getMenusInStore(@PathVariable Long storeId, HttpSession session, Model model) {
		
		if (!verifyLogin(session)) {
			return "redirect:/login/form";
		}
		
		if (!verifyManager(session)) {
			return "redirect:/stores/" + storeId;
		}
		
//		Long userId = (Long) session.getAttribute("user_id");
		MenuListResponse menuListResponse = menuService.getMenusByStoreId(storeId);
		
		if (!menuListResponse.isSuccess()) {
			System.out.println("Error: " + menuListResponse.getMessage());
			return "";
		}
		
		model.addAttribute("menuListResponse", menuListResponse);
		return "menus/store_menus";
	}

//	@GetMapping("/menu/list")
//	public String listMenus(Model model, HttpSession session) {
//		List<Menu> menuList = menuListDao.findByStoreId(5);
//
//		model.addAttribute("menuList", menuList);
//
//		return "MenuList"; // 메뉴 목록 화면
//	}

	
	/**
	 * 메뉴 등록 화면 불러오기
	 * @param storeId 가게 고유 번호
	 * @param model
	 * @return
	 */
	@GetMapping("/menu/registerView/{storeId}")
	public String registerView(@PathVariable Long storeId, Model model) {
		model.addAttribute("storeId", storeId);
		return "/menus/menu_registration";
	}

	
	/**
	 * 신규 메뉴 저장
	 * @param storeId 가게 고유 번호
	 * @param menuRegistrationRequest 메뉴 정보
	 * @param httpsession
	 * @return
	 */
	@PostMapping("/menu/register/{storeId}")
	public String registerMenu(@PathVariable Long storeId, MenuRegistrationRequest menuRegistrationRequest,
			HttpSession session) {
		
		if (!verifyLogin(session)) {
			return "redirect:/login/form";
		}
		
		if (!verifyManager(session)) {
			return "redirect:/stores/" + storeId;
		}

		Long userId = (Long) session.getAttribute("user_id");
		
		// 메뉴 정보 저장
		MenuRegistrationResponse menuRegistrationResponse = menuService.addNewMenu(storeId, userId, menuRegistrationRequest);
		
		if (!menuRegistrationResponse.isSuccess()) {
			// 실패
			System.out.println("Error: " + menuRegistrationResponse.getMessage());
			
			// 목록 화면으로 돌아가기
			return "redirect:/menu/" + storeId;
		}
		
		return "redirect:/menu/" + storeId;
	}

	
	/**
	 * 메뉴 수정 화면 불러오기
	 * @param storeId 가게 고유 번호
	 * @param menuId 메뉴 고유 번호
	 * @param session 세션
	 * @param model
	 * @return 메뉴 수정 화면
	 */
	@GetMapping("/menu/updateView")
	public String getUpdateView(@RequestParam Long storeId, @RequestParam Long menuId, HttpSession session, Model model) {
		
		if (!verifyLogin(session)) {
			return "redirect:/login/form";
		}

		if (!verifyManager(session)) {
			return "redirect:/stores/" + storeId;
		}
		
		// 기존 코드
//		ForUpdateMenu menu = forUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(menuId, storeId);
//		model.addAttribute("menu", menu);
		
		Long userId = (Long)session.getAttribute("user_id");
		
		// 메뉴 조회
		MenuUpdateFormResponse menuUpdateFormResponse = menuService.getMenuForUpdate(storeId, userId, menuId);
		
		if (!menuUpdateFormResponse.isSuccess()) {
			// 실패
			System.out.println(menuUpdateFormResponse.getMessage());
			
			// 목록 화면으로 돌아가기
			return "redirect:/menu/" + storeId;
		}

		model.addAttribute("menuUpdateFormResponse", menuUpdateFormResponse);
		return "menus/menu_edit";
		
//		return "UpdateMenu";
	}

	
	/**
	 * 메뉴 정보 수정
	 * @param storeId 가게 고유 번호
	 * @param menuId 메뉴 고유 번호
	 * @param name 메뉴 이름
	 * @param price 메뉴 가격
	 * @param description 메뉴 설명
	 * @return
	 */
	@PostMapping("/menu/update")
	public String updateMenu(@RequestParam Long storeId,
			@RequestParam Long menuId,
			@RequestParam("name") String name,
			@RequestParam("price") int price,
			@RequestParam("description") String description) {
		try {
			WillBeUpdatedMenu menu = new WillBeUpdatedMenu();
			menu.setId(menuId);
			menu.setName(name);
			menu.setPrice(price);
			menu.setDescription(description);

			latestMenuUpdateService.updateMenu(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/menu/" + storeId;
	}

	
	/**
	 * 메뉴 삭제하기
	 * @param storeId 가게 고유 번호
	 * @param menuId 메뉴 고유 번호
	 * @param session 세션
	 * @return
	 */
	@DeleteMapping("/menu/delete")
	@ResponseBody
	public ResponseEntity<?> deleteMenu(@RequestParam Long storeId, @RequestParam Long menuId, HttpSession session) {		
		if (!verifyLogin(session)) {
			MenuDeleteResponse response = MenuDeleteResponse.builder()
					.httpStatus(HttpStatus.UNAUTHORIZED)	// 401 에러
					.message("로그인이 필요합니다.")
					.redirectUrl("/queuing/login/form")
					.build();
			
			return ResponseEntity.status(response.getHttpStatus()).body(response);
		}

		Long userId = (Long) session.getAttribute("user_id");
		MenuDeleteResponse menuDeleteResponse = latestMenuDeleteService.deleteMenu(storeId, userId, menuId);

		return ResponseEntity.status(menuDeleteResponse.getHttpStatus()).body(menuDeleteResponse);
	}
	
	
	/**
	 * 로그인 여부 확인
	 * @param session 세션
	 * @return true: 로그인 됨, false: 로그인 안 됨
	 */
	private boolean verifyLogin(HttpSession session) {
		if (session == null) {
			return false;
		}
		
		if (session.getAttribute("user_id") == null) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 사용자 권한 확인
	 * @param session 세션
	 * @return true: 매니저, false: 일반 사용자
	 */
	private boolean verifyManager(HttpSession session) {
		String role = (String) session.getAttribute("role");
		
		if (role == null) {
			return false;
		}
		
		if (!role.equals("MANAGER")) {
			return false;
		}
		
		return true;
	}
}
