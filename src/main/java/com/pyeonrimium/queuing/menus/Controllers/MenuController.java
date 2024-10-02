package com.pyeonrimium.queuing.menus.Controllers;

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
			/*@RequestParam("selectedMenuId") String selectedMenuId ,*/ HttpSession session) {
		
		 if ("edit".equals(action)) {
			 
			// /menu/updateView에서 int storeId = (int) session.getAttribute("storeId"); // 누군가의 로직에서 가져옴? 
			 
		// return "redirect:/menu/updateView?selectedMenuId=" + selectedMenuId + "&storeId=" + storeId; }
		 
		 return "redirect:/menu/updateView?selectedMenuId=" /*+ selectedMenuId */ ; }
		
	else if ("delete".equals(action)) {
        return "redirect:/menu/delete?selectedMenuiId=" /*+ selectedMenuId*/ ;  // 매핑된 주소는 /menu/delete 로 해도 되나?
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
	@GetMapping("/menu/updateView") // post인지 get인지 모르겠음..
	public String getUpdateView(/*@RequestParam("selectedMenuId") int selectedMenuId,*/
			                    /*@RequestParam("storeId") int storeId,*/
			                            Model model         
			                          , HttpSession session               ) {
		
//	int storeId = (int) session.getAttribute("storeId"); /menu/list처럼 storeId에 숫자를 넣기 위해.
// 파람을 받아야 하는데 실험할때 그 양식에서 시작할수있지만 그냥 이 주소로 바로 시작한다면 파람값을 받진 않고 
//실행이 되기 때문에 오류가 나서 임의로 값을 대입하고 매개변수는 주석처리하여(주석처리를 해야하는지는 정확히는..)
		
    ForUpdateMenu menu= null; // 이건 일단은 빨간줄없앤다고 해둔거긴 한데.. 창현님이 뭐 알려주신듯.. 
    //menu/list에선 딱히 다른게 없고 Dao에 적용됐나?
    //List<Menu> menus = new ArrayList<Menu>(); < 이거임. 이거 이해좀해야함. 
    //복붙하기엔 메뉴 하나만 조회하는거라서.
    //근데 이것도 이해는 잘안감. 빨간줄 없애랴고 챗지피티한테 물어본거라
    //변수초기화 문제 해결 챗지피티에 있으니 나중에 차차 알아볼것. .. 뭔가 어렵다.. 
    //우선은 기능 구현 중심으로.. updateview,등록,수정,삭제 까지.. 

    //(/*slecetedMenuId*/2,/*storeId*/5)를 하려했는데 이렇게 해도되나? 
    //forUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(2,5) 하면..우선 이것부터가..하면 어떻게 되는지 정확히! 아나?
    //매개변수의 순서.. 아니면 selecedMenuId = 5; 하고 selectedmenuId는 그대로 사용하고.. 
    //근데 또 selectedMenuId를 쓰려면 선언을 해야하는데.. 실험을 함녀서 이런 고려?를 하게되는구나. 
    
	menu = forUpdatemenuJspService.ForUpdateMenuJspGetThatWantedMenu(/*selectedMenuId*/2,/*storeId*/5); 
	// selectedMenuId는 익숙한데 storeId는 왜 생소하지? 로컬 변수.. 호출용 변수..매개변수가, 필드에선언된 변수가..

	             model.addAttribute("menu", menu); 
	             //키-값 자료구조를 알아야할듯해서. 
	             
		        return "UpdateMenu" ;
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












