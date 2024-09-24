package com.pyeonrimium.queuing.menus.domains.dtos;

public class Menu {
	
	    private String id;        // 메뉴 고유 ID (예: MongoDB와 같은 데이터베이스 사용 시)
	    private String name;      // 메뉴 이름
	    private double price;     // 메뉴 가격
	    private String description; // 메뉴 설명
	    private String imageUrl;  // 메뉴 이미지 URL

	    // 기본 생성자
	    public Menu() {
	    }

	    // 모든 필드를 포함한 생성자
	    public Menu(String id, String name, double price, String description, String imageUrl) {
	        this.id = id;
	        this.name = name;
	        this.price = price;
	        this.description = description;
	        this.imageUrl = imageUrl;
	    }

	    // Getter와 Setter 메서드
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

	    public String getImageUrl() {
	        return imageUrl;
	    }

	    public void setImageUrl(String imageUrl) {
	        this.imageUrl = imageUrl;
	    }

	    @Override
	    public String toString() {
	        return "Menu{" +
	                "id='" + id + '\'' +
	                ", name='" + name + '\'' +
	                ", price=" + price +
	                ", description='" + description + '\'' +
	                ", imageUrl='" + imageUrl + '\'' +
	                '}';
	    }
	


}
