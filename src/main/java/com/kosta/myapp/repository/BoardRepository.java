package com.kosta.myapp.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kosta.myapp.vo.BoardVO;

public interface BoardRepository extends CrudRepository<BoardVO, Long>, QuerydslPredicateExecutor<BoardVO>{
//1.기본 crud는 제공된다. findAll(), findById(), sava(), delete(), count(), exist()
//2.규칙에 맞는 메서드정의 추가
//https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods
	List<BoardVO> findByTitle(String title); //where title = ?
	List<BoardVO> findByWriter(String writer);
	List<BoardVO> findByWriterAndTitle(String writer, String title);
	List<BoardVO> findByTitleLike(String title); //where title like '%'?'%'
	List<BoardVO> findByTitleContaining(String title); //where title like '%'?'%'
	List<BoardVO> findByBnoGreaterThan(Long bno); // where bno>?
	List<BoardVO> findByregDateBetween(Timestamp sdate,Timestamp edate ); // where regDate between ? and ?
	
	List<BoardVO> findByregDateBetweenOrderByBnoDesc(Timestamp sdate,Timestamp edate , Pageable paging); //paging은 rownum임
	//select * from ( select * from t_boards where reg_date between ? and ? order by bno desc ) where rownum <= ?
	
	Page<BoardVO> findByregDateBetweenOrderByBno(Timestamp sdate,Timestamp edate , Pageable paging);
	
	//3.JPAL(JPA Query Language)..join , 복잡한 조건을 가진 sql 등 가능
	@Query("select b from BoardVO b where bno>=?2 and b.title like %?1% order by b.title desc")
	//jpal은 * 없음 sql이랑 jpal이랑 다른 언어임.
	List<BoardVO> selectAllbyTitle(String title, Long bno);
	
	//sql문이다. (nativeQuery = true)
	@Query(value = "select * from t_boards b where bno>=?2 and b.title like %?1% order by b.title desc", nativeQuery = true)
	List<BoardVO> selectAllbyTitle2(String title, Long bno);
	
	//@Param이용한 JPAL
	@Query("select b from BoardVO b where bno>=:NUM and b.title like %:T% order by b.title desc")
	List<BoardVO> selectAllbyTitle3(@Param("T") String title,@Param("NUM") Long bno);
	
	//#{#entityName}이라고 했기 때문에 BoardVO 이름이 바뀌어도 괜찮음 
	@Query("select b from #{#entityName} b where bno>=:NUM and b.title like %:T% order by b.title desc")
	List<BoardVO> selectAllbyTitle4(@Param("T") String title,@Param("NUM") Long bno);
}
