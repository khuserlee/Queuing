package com.pyeonrimium.queuing.menus.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pyeonrimium.queuing.menus.daos.MenuListMenuFindByStoreIdDao;
import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;
import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;
import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;
import com.pyeonrimium.queuing.menus.domains.entities.Menu;
import com.pyeonrimium.queuing.menus.services.ForUpdatemenuJspService;
import com.pyeonrimium.queuing.menus.services.LatestMenuDeleteService;
import com.pyeonrimium.queuing.menus.services.LatestMenuPostService;
import com.pyeonrimium.queuing.menus.services.LatestMenuUpdateService;

public class LatestMenuController {
	
	@PostMapping("/menu/X")
	public String handleMenuAction(@RequestParam("action") String action, 
			@RequestParam("selectedMenuId") String selectedMenuId , HttpSession session) {
		 if ("edit".equals(action)) {
			 String storeId = (String) session.getAttribute("storeId"); // 누군가의 로직에서 가져옴? 
			 return "redirect:/menu/updateView?selectedMenuId=" + selectedMenuId + "&storeId=" + storeId; }
		 
		 
	
	else if ("delete".equals(action)) {

        return "redirect:/menu/delete"; // selectedMenuId를 /menu/delete로 다시 보내야 할 것 같은데.. 
    } 
		 
		 
	else if ("register".equals(action)) {
		
        return "MenuPost";
    }

    return "redirect:/menu/list";

}
	
	@Autowired MenuListMenuFindByStoreIdDao menuListMenuFindByStoreIdDao;
	@GetMapping("/menu/list")
	public String listMenus(Model model, HttpSession session) {
		String storeId = (String) session.getAttribute("storeId");
        List<Menu> menuList = menuListMenuFindByStoreIdDao.findByStoreId(storeId); //DAo 할래..
        model.addAttribute("menu", menuList); //menu인가 menus인가 
        return "MenuList"; // 메뉴 목록 화면 
    }
	
	
	
	@Autowired LatestMenuDeleteService latestMenuDeleteService;
	@PostMapping("/menu/delete")
	public String deleteMenu(@RequestParam("selectedMenuId") String selectedMenuId) {
		latestMenuDeleteService.deleteMenu(selectedMenuId);
		return "redirect:/menu/list";
	}
	
	
	
	@Autowired ForUpdatemenuJspService forUpdatemenuJspService;
	@PostMapping("/menu/updateView") // post인지 get인지 모르겠음..
	public String getUpdateView(@RequestParam("selectedMenuId") String selectedMenuId, Model model,@RequestParam("storeId") String storeId  ) {
		
 ForUpdateMenu menu= null;
try {
	menu = forUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(selectedMenuId,storeId);
} catch (Exception e) {
	
	e.printStackTrace();
}
	             model.addAttribute("menu", menu); 
	             
		        return "MenuUpdate" ;
	}
	
//	-----------------------------------------------------------------------------------
	
	
	
	@Autowired LatestMenuPostService latestMenuPostService ;
	@PostMapping("/menu/register")
	 public String registerMenu(@RequestParam("menuId") String menuId,

             @RequestParam("name") String name,

             @RequestParam("price") int price,

             @RequestParam("details") String details)  {  
		try {
			
WillBePostedMenu menu = new WillBePostedMenu();
menu.setId(menuId);
menu.setName(name);
menu.setPrice(price);
menu. setDescription(details);

latestMenuPostService.saveMenu(menu);
return "redirect:/menu/list";   
		}catch(Exception e){
			return "menu/register"; //+ 메뉴등록에 실패했습니다. 
		}
  }
	
	
	
	
	
	@Autowired
    private LatestMenuUpdateService latestMenuUpdateService;
	@PostMapping("/menu/update")
	public String updateMenu(@RequestParam("menuId") String menuId, 

	                         @RequestParam("name") String name,

	                         @RequestParam("price") int price,

	                         @RequestParam("details") String details) {
		try {

	WillBeUpdatedMenu menu = new WillBeUpdatedMenu();
	menu.setId(menuId);
    menu.setName(name);
    menu.setPrice(price);
    menu. setDescription(details);

	latestMenuUpdateService.updateMenu(menu); 
	return "redirect: /menu/list"; } catch(Exception e) {

    return "redirect:/menu/list"; 
	}
  }
	
	
}












