package com.pyeonrimium.queuing.menus.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document(collection = "menus") // 이건 뭐임? 처음 보는 어노테이션인디. 이후 형식의 의미도.. 

//실제 데이터베이스에 메뉴를 저장하는 로직 으로 엔티티를 작성중인데.. 뭔지도 모르겠네미ㅏ엄나ㅣㅓ


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
//	@Id
    private long menuId;
    private String name;
    private int price;
    private String description;
 //   private String imageUrl;



}
