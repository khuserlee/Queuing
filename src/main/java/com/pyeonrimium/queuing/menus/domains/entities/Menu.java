package com.pyeonrimium.queuing.menus.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

	private long menuId;
	private String name;
	private int price;
	private String description;

}
