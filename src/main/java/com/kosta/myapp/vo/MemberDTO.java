package com.kosta.myapp.vo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data //getter, setter. toString, EqualsAndHashcord 쓴것과 동일함 생성자는 써줘야함
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "mid") //mid가 같으면 같은거임
@Entity //객체로 관리함
@Table(name = "tbl_members") //테이블로 만듦
@Builder
public class MemberDTO {
	@Id
	String mid;
	String mpassword;
	String mname;
	
	@Enumerated(EnumType.STRING) //데이터 들어갈때 enum의 string으로 들어감 안쓰면 숫자가 들어감 
	MemberRoleEnum mrole;
}
