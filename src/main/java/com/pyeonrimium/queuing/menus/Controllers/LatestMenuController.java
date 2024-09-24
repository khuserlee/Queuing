package com.pyeonrimium.queuing.menus.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;
import com.pyeonrimium.queuing.menus.domains.WillBePostedMenu;
import com.pyeonrimium.queuing.menus.domains.WillBeUpdatedMenu;
import com.pyeonrimium.queuing.menus.services.ForUpdatemenuJspService;
import com.pyeonrimium.queuing.menus.services.LatestMenuPostService;

public class LatestMenuController {
	
	@PostMapping("/menu/X")
	public String handleMenuAction(@RequestParam("action") String action, 
			@RequestParam("selectedMenuId") String selectedMenuId) {
		 if ("edit".equals(action)) {
			        return  "redirect:/menu/updateView" ;  // /menu/update 주소에 수정화면양식기능과 양식에서 받는 기능
		 }                                             // /menu/update 주소와 수정화면양식을 받는 주소 비교
		                                               // 주소가 다르다면 쉽다. 
	 	                                               //주소가 같다면 주소가 같다는게 한메서드 내처리를 의미하는가? 
		                                               // 메서드에서 두개의 기능을 처리. 
		                                               // 그 메서드가 받는 매개변수... 와 연관성.. 
		 
	
	else if ("delete".equals(action)) {

        return "redirect:/menu/delete"; // selectedMenuId를 /menu/delete로 다시 보내야 할 것 같은데.. 
    } 
		 
		 
	else if ("register".equals(action)) {
		
        return "MenuPost";
    }

    return "redirect:/menu/list";

}
	
	
	@GetMapping("/menu/list")
	public String listMenus(Model model) {
        List<Menu> menuList = menuService.getAllMenus();
        model.addAttribute("menuList", menuList);
        return "/menu/list"; // 메뉴 목록 화면 
    }
	
	
	@PostMapping("/menu/delete")
	public String deleteMenu(@RequestParam("selectedMenuId") String selectedMenuId) {
		menuDeleteService.deleteMenu(selectedMenuId);
		return "redirect:/menu/list";
	}
	
	
	@PostMapping("/menu/updateView") // post인지 get인지 모르겠음..
	public String getUpdateView(@RequestParam("selectedMenuId") String selectedMenuId, Model model )//<< 이게 받을수있나?  
	//***************************4*************************************************	
 { ForUpdateMenu menu = ForUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(selectedMenuId)//
	             model.addAttribute("menu", menu);                                            // jsp에 전달
		        return "MenuUpdate" ;
	}
	
//	-----------------------------------------------------------------------------------
	//(입력 양식에서 입력 후)(수정과 등록의 경우만)
	
	
	@PostMapping("/menu/register")
	 public String registerMenu(@RequestParam("menuId") String menuId,

             @RequestParam("name") String name,

             @RequestParam("price") int price,

             @RequestParam("details") String details)  {  
		try {
WillBePostedMenu menu = new WillBePostedMenu(menuId, name, price, details);
//jdbcTemplate.update(sql, menu.getMenuId(), menu.getStoreId(), menu.getName(), menu.getDescription(), menu.getPrice());
//윗코드가 menu를 이용해서 필드를 가져와서 데이터베이스에 넣기 때문에 . 그 menu에다가 입력받은 필드들을 저장한다. WillBePostedMenu=Vo이다. Vo  사용법 알아오기. 
LatestMenuPostService.saveMenu(menu);
return "redirect:/menu/list";   
		}catch(Exception e){
			return "menu/register"; //+ 메뉴등록에 실패했습니다. 
		}
  }
	
	
	@PostMapping("/menu/update")
	public String updateMenu(@RequestParam("menuId") String menuId, 

	                         @RequestParam("name") String name,

	                         @RequestParam("price") int price,

	                         @RequestParam("details") String details) {
		try {

	WillBeUpdatedMenu menu = new WillBeUpdatedMenu(menuId, name, price, details);

	LatestMenuUpdateService.updateMenu(menu); 
	return "redirect: /menu/list"; } catch(Exception e) {

    return "redirect:/menu/list"; 
	}
  }
	
	
}












