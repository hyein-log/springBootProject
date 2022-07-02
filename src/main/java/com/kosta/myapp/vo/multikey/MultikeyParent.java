package com.kosta.myapp.vo.multikey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultikeyParent implements Serializable{  //복합키는 무조건 Serializable 해줘야함
	
	private static final long serialVersionUID = 1L;
	private Integer orderId;
	private Integer orderSeq;
}
