package com.pyeonrimium.queuing.menus.domains;
//사실 이게 VO인데.. 이름을 VO철자를 포함하라는건가..? 
public class WillBeUpdatedMenu {
	
//	입력 필드를 등록이랑 똑같이 해야할까? 수정된곳이 3개 다 일수 있잖아.. 
	
	 private String id;
	 private String name;
	 private double price;
	 private String description;
	 
	 public void Menu(String id, String name, double price, String description) {
	        this.id = id;
	        this.name = name;
	        this.price = price;
	        this.description = description;
	    }

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

	
		
	
	
	

}
