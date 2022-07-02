package com.kosta.myapp.vo.multikey;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_order_detail2")
public class _MultikeyChild2 {
	
	@EmbeddedId
	_MultikeyParent2 orderid;
	
	String orderGoods;
	String orderUser;
	@CreationTimestamp
	Timestamp orderDate;
}
