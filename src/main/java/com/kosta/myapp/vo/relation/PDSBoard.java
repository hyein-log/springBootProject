package com.kosta.myapp.vo.relation;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // getter, setter, toString -> LAZE로 설정하면 조인된 칼럼은 가지고 오지 않는데 @Data안에는 toString도 포함되어 있기 때문에 springBoot는 모든 칼럼을 가지고 오려고함.
//따라서 오류가 발생하게 되기에 조인된 칼럼은 빼고 toString갖고오라는 명령어를 써줘야함
@ToString(exclude = "files2")
@Entity
@Table(name = "tbl_pdsboard")
@EqualsAndHashCode(of = "pid")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PDSBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;
	private String pname;
	private String pwriter;
	
	
	//cascade : 영속성전이 PDSBoard가 수정되면 PDSFile변경
	//fetch : EAGER(즉시 로딩), LAZY(지연 로딩)
	//EAGER -> PDSBoard 조회시 자식인 PDSFile가 조인하여 조회
	//조인한 테이블의 내용은 필요하지 않다면 LAZY 사용.
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//1:N 단방향
	@JoinColumn(name = "pdsno") // PDSFile칼럼에 생성
	private List<PDSFile> files2; //PDSBoard에는 files2칼럼은 존재안함 // JPQL에서만 사용하지 실제 DB에 생기는게 아님
}
