package com.pyeonrimium.queuing.menus.domains;

public class WillBePostedMenu {

	// private String id;
	private String name;
	private int price;
	private String description;
	private int storeId;

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void Menu(/* String id */ String name, int price, String description) {
		// this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	/*
	 * public String getId() { return id; } public void setId(String id) { this.id =
	 * id; }
	 */

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

}
