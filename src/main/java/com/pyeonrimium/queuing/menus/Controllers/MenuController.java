package com.pyeonrimium.queuing.menus.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			@RequestParam("selectedMenuId") String selectedMenuId , HttpSession session) {
		
		 if ("edit".equals(action)) {
			 
			// /menu/updateView에서 int storeId = (int) session.getAttribute("storeId"); // 누군가의 로직에서 가져옴? 
			 
		// return "redirect:/menu/updateView?selectedMenuId=" + selectedMenuId + "&storeId=" + storeId; }
		 
		 return "redirect:/menu/updateView?selectedMenuId=" + selectedMenuId ; }
		
	else if ("delete".equals(action)) {
        return "redirect:/menu/delete?selectedMenuiId="+ selectedMenuId;  // 매핑된 주소는 /menu/delete 로 해도 되나?
    } 
		 
		 
	else if ("register".equals(action)) {
        return "MenuPost";
    }
	
	
    return "redirect:/menu/list";
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired 
	private MenuListDao menuListDao;
	@GetMapping("/menu/list")
	public String listMenus(Model model, HttpSession session) {
		//int storeId = (int) session.getAttribute("storeId");
		
        List<Menu> menuList = menuListDao.findByStoreId(5);  //storeId 
                                                             //데이터베이스에 storeId가 5인 얘가 있어야..
        
        System.out.println(menuList.size());
        model.addAttribute("menuList", menuList); 
        
        return "MenuList"; // 메뉴 목록 화면 
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired LatestMenuDeleteService latestMenuDeleteService;
	@PostMapping("/menu/delete")
	public String deleteMenu(@RequestParam("selectedMenuId") int selectedMenuId) {
		latestMenuDeleteService.deleteMenu(selectedMenuId);
		return "redirect:/menu/list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired ForUpdatemenuJspService forUpdatemenuJspService;
	@PostMapping("/menu/updateView") // post인지 get인지 모르겠음..
	public String getUpdateView(@RequestParam("selectedMenuId") int selectedMenuId,
			                    /*@RequestParam("storeId") int storeId,*/
			                            Model model         
			                          , HttpSession session               ) {
		
	int storeId = (int) session.getAttribute("storeId");
		
    ForUpdateMenu menu= null; // 이건 일단은 빨간줄없앤다고 해둔거긴 한데.. 창현님이 뭐 알려주신듯.. 
    //menu/list에선 딱히 다른게 없고 Dao에 적용됐나?
    //List<Menu> menus = new ArrayList<Menu>(); < 이거임. 이거 이해좀해야함. 
    //복붙하기엔 메뉴 하나만 조회하는거라서.

	menu = forUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(selectedMenuId,storeId); 
	// selectedMenuId는 익숙한데 storeId는 왜 생소하지? 로컬 변수.. 호출용 변수..매개변수가, 필드에선언된 변수가..

	             model.addAttribute("menu", menu); 
	             
		        return "MenuUpdate" ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired LatestMenuPostService latestMenuPostService ;
	@PostMapping("/menu/register")
	 public String registerMenu(//@RequestParam("menuId") int menuId,

                                @RequestParam("name") String name,

                                @RequestParam("price") int price,

                                @RequestParam("description") String description)  {  
		try {
			
WillBePostedMenu menu = new WillBePostedMenu();
//menu.setId(menuId);
menu.setName(name);
menu.setPrice(price);
menu. setDescription(description);

latestMenuPostService.saveMenu(menu);
return "redirect:/menu/list";   
		}catch(Exception e){
			return "menu/register"; //+ 메뉴등록에 실패했습니다. 
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
    menu. setDescription(description);

	latestMenuUpdateService.updateMenu(menu); 
	return "redirect: /menu/list"; } catch(Exception e) {

    return "redirect:/menu/list"; 
	}
  }
	
	
}












