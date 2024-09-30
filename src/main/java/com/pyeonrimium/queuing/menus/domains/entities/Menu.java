package com.pyeonrimium.queuing.menus.domains.entities;


//@Document(collection = "menus") // 이건 뭐임? 처음 보는 어노테이션인디. 이후 형식의 의미도.. 

//실제 데이터베이스에 메뉴를 저장하는 로직 으로 엔티티를 작성중인데.. 뭔지도 모르겠네미ㅏ엄나ㅣㅓ
public class Menu {
//	@Id
    private int id;
    private String name;
    private int price;
    private String description;
 //   private String imageUrl; 

    // 기본 생성자
    public Menu() {}

    // 모든 필드를 포함한 생성자
    public Menu(int id, String name, int price, String description/*, String imageUrl*/) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
      //  this.imageUrl = imageUrl;
    }

    // Getter와 Setter 메서드
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

  //  public String getImageUrl() {
 //       return imageUrl;
  //  }

  //  public void setImageUrl(String imageUrl) {
  //      this.imageUrl = imageUrl;
 //   }

   

}
