package com.pyeonrimium.queuing.menus.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pyeonrimium.queuing.menus.daos.MenuListDao;
import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;
import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;
import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;
import com.pyeonrimium.queuing.menus.services.ForUpdatemenuJspService;
import com.pyeonrimium.queuing.menus.services.LatestMenuDeleteService;
import com.pyeonrimium.queuing.menus.services.LatestMenuPostService;
import com.pyeonrimium.queuing.menus.services.LatestMenuUpdateService;

@Controller
public class MenuController {

	@PostMapping("/menu/X")
	public String handleMenuAction(@RequestParam("action") String action,
			@RequestParam("selectedMenuId") long selectedMenuId, HttpSession session) {

		if ("edit".equals(action)) {
			return "redirect:/menu/updateView?selectedMenuId=" + selectedMenuId  ; 
			//return "redirect:/menu/updateView"  + selectedMenuId */ ;
		}

		else if ("delete".equals(action)) {
			
			return "redirect:/menu/delete?selectedMenuId=" + selectedMenuId; // 매핑된 주소는 /menu/delete 로 해도 되나?
		}

		else if ("register".equals(action)) {
			return "PostMenu";
		}

		return "redirect:/menu/list";
	}

	@Autowired
	private MenuListDao menuListDao;

	@GetMapping("/menu/list")
	public String listMenus(Model model, HttpSession session) {
		//int storeId = (int) session.getAttribute("storeId");

		List<Menu> menuList = menuListDao.findByStoreId(5); // storeId
															

		model.addAttribute("menuList", menuList);

		return "MenuList"; // 메뉴 목록 화면
	}

	@Autowired
	LatestMenuDeleteService latestMenuDeleteService;

	@GetMapping("/menu/delete")
	public String deleteMenu(@RequestParam("selectedMenuId") long selectedMenuId) {
		
		latestMenuDeleteService.deleteMenu(selectedMenuId);
		return "redirect:/menu/list";
	}

	@Autowired
	ForUpdatemenuJspService forUpdatemenuJspService;

	@GetMapping("/menu/updateView") // post인지 get인지 모르겠음..
	public String getUpdateView( @RequestParam("selectedMenuId") long selectedMenuId, 
			/* @RequestParam("storeId") int storeId, */
			Model model, HttpSession session) {

System.out.println(selectedMenuId);

		ForUpdateMenu menu = null; 
		

		menu = forUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(selectedMenuId , /* storeId */5);
		// selectedMenuId는 익숙한데 storeId는 왜 생소하지? 로컬 변수.. 호출용 변수..매개변수가, 필드에선언된 변수가..

		model.addAttribute("menu", menu);
		// 키-값 자료구조를 알아야할듯해서.

		return "UpdateMenu";
	}

	@Autowired
	LatestMenuPostService latestMenuPostService;

	@PostMapping("/menu/register")
	public String registerMenu(// @RequestParam("menuId") int menuId,

			@RequestParam("name") String name,

			@RequestParam("price") int price,

			@RequestParam("description") String description) {
		try {
			WillBePostedMenu menu = new WillBePostedMenu();
			// menu.setId(menuId);
			menu.setName(name);
			menu.setPrice(price);
			menu.setDescription(description);

			latestMenuPostService.saveMenu(menu);
			return "redirect:/menu/list";
		} catch (Exception e) {
			e.printStackTrace();
			return "PostMenu"; // + 메뉴등록에 실패했습니다.
		}
	}

	@Autowired
	private LatestMenuUpdateService latestMenuUpdateService;

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
