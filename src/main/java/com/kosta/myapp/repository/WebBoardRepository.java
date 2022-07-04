package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kosta.myapp.vo.relation.QWebBoard;
import com.kosta.myapp.vo.relation.WebBoard;
import com.kosta.myapp.vo.relation.WebReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface WebBoardRepository
		extends PagingAndSortingRepository<WebBoard, Long>, QuerydslPredicateExecutor<WebBoard> {

	public WebBoard findByBno(Long bno);
	
	//1.추상메서드 : 정의는 있고 구현은 없다. 구현은 implements받은 class가 함
	//3.static메서드 : interface를 구현한 모든 class가 공유해서 사용하는 메서드임. 재정의 불가능함
	//2.default 메서드 : interface를 구현한 모든 class가 공유해서 사용하는 메서드임. 재정의 가능함
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QWebBoard board = QWebBoard.webBoard;
		builder.and(board.bno.gt(0)); //and bno > 0
		// 검색조건처리
		if (type == null)
			return builder;
		switch (type) {
		case "title":
			builder.and(board.title.like("%" + keyword + "%"));
			break; //and title like '%'?'%'
		case "content":
			builder.and(board.content.like("%" + keyword + "%"));
			break;
		case "writer":
			builder.and(board.writer.like("%" + keyword + "%"));
			break;
		default:
			break;
		}
		return builder;
	}

}
