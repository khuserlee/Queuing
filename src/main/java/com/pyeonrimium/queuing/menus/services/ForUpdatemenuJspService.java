package com.pyeonrimium.queuing.menus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.menus.daos.ForUpdateMenuJspDao;
import com.pyeonrimium.queuing.menus.domains.ForUpdateMenu;

@Service
public class ForUpdatemenuJspService {

	@Autowired
	ForUpdateMenuJspDao MenuDao;

	public ForUpdateMenu ForUpdateMenuJspGetThatWantedMenu(long selectedMenuId, int storeId) {

		return MenuDao.ForUpdateMenuJspGetThatWantedMenu(selectedMenuId, storeId); // 다른 서비스와는 달리 return이..

	};

}
