package com.kosta.myapp.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name =  "t_boards") //table명을 안주면 class명이 테이블명이 됨
public class BoardVO {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.AUTO) //sequence , 번호 자동생성기 , 숫자일 때만 가능 , primary key가 문자면 사용불가
	private Long bno;
	@NonNull //lombot한테 알려주는 것
	@Column(nullable = false) //JPA -> DB만들때 조건 알려주는 것
	private String title;
	
	@Column(length = 1000) //데이터 크기 지정
	private String content;
	private String writer;
	
	@CreationTimestamp //생성날짜가 만들어짐  //오라클에서 sysdate
	private Timestamp regDate; 
	@UpdateTimestamp //업데이트하면 만들어짐  //오라클에서 sysdate
	private Timestamp updateDate;
	
}
