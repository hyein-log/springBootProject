package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kosta.myapp.vo.relation.PDSBoard;

public interface PDSBoardRepository extends PagingAndSortingRepository<PDSBoard, Long> {
	// 1.기본제공되는 메서드만 가능  CrudRepository,PagingAndSortingRepository -> findAll(), PagingAndSortingRepository -> findAll(sort)도 포함됨
	// 2.규칙에 맞는 함수정의가능
	// 3.JPQL : @Query이용해서 query 직접사용가능
	@Query("select b.pid , b.pname, b.pwriter, f.pdsfilename from PDSBoard b left outer join b.files2 f where b.pid = ?1 order by b.pid ")
	public List<Object[]> getFilesInfo(long pid); // 가져오는 칼럼의 수가 3개인데 두개는 Board에 있고 한개는 File에 있으니 Object임.

	@Query("select b.pid , b.pname, b.pwriter,f.fno, f.pdsfilename from PDSBoard b left outer join b.files2 f  order by b.pid ")
	public List<Object[]> getFilesInfo2(); // 가져오는 칼럼의 수가 3개인데 두개는 Board에 있고 한개는 File에 있으니 Object임.

	@Query("select b.pname, count(f) from PDSBoard b left outer join b.files2 f  where b.pid>0 group by b.pname order by b.pname ")
	public List<Object[]> getFilesCount1();

	@Query(value = "select b.pname, count(*) from tbl_pdsboard b left outer join tbl_pdsfiles f on(b.pid=f.pdsno) group by b.pname order by 1 ", nativeQuery = true)
	public List<Object[]> getFilesCount2();

	//@Query : select만 지원 , DML(insert, update, delete) 작업하려면 @Modifying 추가
	@Modifying 
	@Query("UPDATE FROM PDSFile f set f. pdsfilename = ?2 WHERE f.fno = ?1 ") 
	int updatePDSFile(Long fno, String newFileName);

}
