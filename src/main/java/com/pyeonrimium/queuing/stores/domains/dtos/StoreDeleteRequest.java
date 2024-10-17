package com.pyeonrimium.queuing.stores.domains.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDeleteRequest {
	//삭제할 식당 고유 ID
	public Long store_id;
}
