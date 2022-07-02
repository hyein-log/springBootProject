package com.kosta.myapp.vo.multikey;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_order_detail")
@IdClass(MultikeyParent.class)
public class MultikeyChild {
	//복합키임. 키 2개.
	@Id
	private Integer orderId;
	@Id
	private Integer orderSeq;
	
	String orderGoods;
	String orderUser;
	@CreationTimestamp
	Timestamp orderDate;
}
