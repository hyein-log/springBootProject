package com.kosta.myapp.vo.relation;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString (exclude = "board")//양방향일때 무한loop주의 
@EqualsAndHashCode(of="rno")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_free_replies")
public class FreeBoardReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//oracle:sequence, mysql:identity
	Long rno;
	String reply;
	String replyer;
	@CreationTimestamp
	Timestamp regdate;
	@UpdateTimestamp
	Timestamp updatedate;
	
	//댓글임
	//댓글입장에서 보드를 보면 댓글(나) 여러개가 보드(상대) 하나에 있을 수 있으니까
	//N : 1임
	//jackson에 의해 JSON이 만들어질때
	//양방향은 무한루프에 걸리므로 replies가 다시 board연결하는 것을 취소함
	@JsonIgnore //board => replies -> board-> replies.....무한 loop
	@ManyToOne
	FreeBoard board; //tbl_free_replies테이블에 board_bno칼럼이 추가됨
}