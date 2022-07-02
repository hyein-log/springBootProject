package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.kosta.myapp.vo.relation.FreeBoard;

public interface FreeBoardRepository extends PagingAndSortingRepository<FreeBoard, Long>{
	//1. default 메서드
	//2. 규칙 기반 메서드
	public Page<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
	
//	//3. JPQL
//	@Query(value = "select bno, count(reply.board_bno) from tbl_freeboards board left outer join tbl_free_replies reply on(board.bno = reply.board_bno) group by bno",nativeQuery = true )
//	public List<Object[]> getCountReply1();
//	
//	@Query("select bno, count(replies) from FreeBoard board left outer join board.replies  group by bno")
//	public List<Object[]> getCountReply2();
	
	//3.JPQL
		@Query(value = "select bno, count(reply.board_bno) "
				+ " from tbl_freeboards board left outer join tbl_free_replies reply on(board.bno = reply.board_bno) "
				+ " group by bno", nativeQuery = true)
		public List<Object[]> getCountReply1() ;
		
		@Query(value = "select board.bno, count(reply) " //* 쓰면 칼럼이 있으면 무조건 1개가 Count되어서 null이라도 1이 나옴 그래서 * 쓰면 안됨
				+ " from FreeBoard board left outer join board.replies reply   "
				+ " group by board.bno" )
		public List<Object[]> getCountReply2() ;

		// 제목에 대한 검색처리
		@Query("select b from FreeBoard b where b.title like %?1% order by b.bno desc")
		public List<FreeBoard> findByTitle(String title);

		// 내용에 대한 검색처리 @Param
		@Query("select b from FreeBoard b where b.content like %:content% order by b.bno desc")
		public List<FreeBoard> findByContent(@Param("content") String content);

		// 작성자에 대한 검색처리 #{#entityName}
		@Query("select b from #{#entityName} b where b.writer like %?1% order by b.bno desc")
		public List<FreeBoard> findByWriter2(String writer);
		// 작성자에 대한 검색처리 #{#entityName}..필요칼럼만 가져오기
		@Query("select b.bno,b.title,b.writer from #{#entityName} b where b.writer like %?1% order by b.bno desc")
		public List<Object[]> findByWriter3(String writer);

		// 작성자에 대한 검색처리 ..nativeQuery이용
		@Query(value="select * from tbl_freeboards  where writer like '%'||?1||'%' order by bno desc", nativeQuery=true) 
		public List<Object[]> findByWriter4(String writer);

		//@Query와 Paging처리/정렬
		@Query("select b from #{#entityName} b order by b.bno desc")
		public Page<FreeBoard> findByPage(Pageable page);

}
