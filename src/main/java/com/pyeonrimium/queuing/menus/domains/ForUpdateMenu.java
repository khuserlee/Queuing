package com.pyeonrimium.queuing.menus.domains;

public class ForUpdateMenu {

	private String name;
	private int price;
	private String description;
	// willbePostedMenu 는 id가 없고 willbeUpdatedMenu는 id가 있음..
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void Menu(int id, String name, int price, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}

}
