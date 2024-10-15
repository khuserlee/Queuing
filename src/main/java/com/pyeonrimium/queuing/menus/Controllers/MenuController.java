package com.pyeonrimium.queuing.menus.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pyeonrimium.queuing.menus.daos.MenuListDao;
import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;
import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuListResponse;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationRequest;
import com.pyeonrimium.queuing.menus.domains.dtos.MenuRegistrationResponse;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;
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
	private MenuListDao menuListDao;

	@Autowired
	LatestMenuDeleteService latestMenuDeleteService;

	@Autowired
	ForUpdatemenuJspService forUpdatemenuJspService;

	@Autowired
	LatestMenuPostService latestMenuPostService;

	@Autowired
	private LatestMenuUpdateService latestMenuUpdateService;
	
	/**
	 * 로그인 여부 확인
	 * @param session
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
	 * @param session
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
	
	/**
	 * 가게 메뉴 목록 조회하기
	 * @param storeId 가게 고유 번호
	 * @param session
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
		MenuRegistrationResponse menuRegistrationResponse = menuService.addNewMenu(storeId, userId, menuRegistrationRequest);
		
		// 실패했을 때
		if (!menuRegistrationResponse.isSuccess()) {
			System.out.println("Error: " + menuRegistrationResponse.getMessage());
			return "PostMenu";
		}
		
		return "redirect:/menu/" + storeId;
	}

	@GetMapping("/menu/list")
	public String listMenus(Model model, HttpSession session) {
		List<Menu> menuList = menuListDao.findByStoreId(5);

		model.addAttribute("menuList", menuList);

		return "MenuList"; // 메뉴 목록 화면
	}

	@GetMapping("/menu/delete/{menuId}")
	public String deleteMenu(@PathVariable Long menuId) {

		latestMenuDeleteService.deleteMenu(menuId);
		return "redirect:/menu/list";
	}

	@GetMapping("/menu/updateView/{menuId}")
	public String getUpdateView(@PathVariable Long menuId, Model model, HttpSession session) {

		ForUpdateMenu menu = null;

		menu = forUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(menuId, 5);

		model.addAttribute("menu", menu);

		return "UpdateMenu";
	}

	@PostMapping("/menu/update")
	public String updateMenu(@RequestParam("menuId") int menuId,
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
			return "redirect:/menu/list";
		} catch (Exception e) {
			e.printStackTrace();
			return "UpdateMenu";
		}
	}

}
