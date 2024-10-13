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
import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;
import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;
import com.pyeonrimium.queuing.menus.services.ForUpdatemenuJspService;
import com.pyeonrimium.queuing.menus.services.LatestMenuDeleteService;
import com.pyeonrimium.queuing.menus.services.LatestMenuPostService;
import com.pyeonrimium.queuing.menus.services.LatestMenuUpdateService;

@Controller
public class MenuController {

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

	@GetMapping("/menu/registerView")
	public String registerView() {
		return "PostMenu";
	}

	@PostMapping("/menu/register")
	public String registerMenu(@RequestParam("name") String name,
			@RequestParam("price") int price,
			@RequestParam("description") String description,
			HttpSession httpsession) {

		try {
			WillBePostedMenu menu = new WillBePostedMenu();
			// menu.setId(menuId);
			menu.setName(name);
			menu.setPrice(price);
			menu.setDescription(description);
			menu.setStoreId(5);

			latestMenuPostService.saveMenu(menu);
			return "redirect:/menu/list";
		} catch (Exception e) {
			e.printStackTrace();
			return "PostMenu";
		}
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
