package com.pyeonrimium.queuing.menus.domains;

public class ForUpdateMenu {
	
	    private String name;
	    private double price;
	    private String description;
	    //willbePostedMenu 는 id가 없고 willbeUpdatedMenu는 id가 있음..
	    private String id;

	  
	    public String getId() {
	        return id;
	    }
	    public void setId(String id) {
	        this.id = id;
	    }
	    
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }
	    
	    public void Menu(String id, String name, double price, String description) {
	        this.id = id;
	        this.name = name;
	        this.price = price;
	        this.description = description;
	    }

}
