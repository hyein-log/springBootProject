package com.kosta.myapp.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.myapp.repository.BoardRepository;
import com.kosta.myapp.vo.BoardVO;
import com.kosta.myapp.vo.CarVO;
import com.kosta.myapp.vo.QBoardVO;
import com.querydsl.core.BooleanBuilder;

@RestController
public class SampleRestController {
	@Autowired
	BoardRepository boardrepository;
	
	@GetMapping("/boardlist.do/{type}/{keyword}")
	public List<BoardVO> testPredicate(@PathVariable String type,@PathVariable String keyword) {

		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;
		if (type.equals("content")) {
			builder.and(board.content.like("%" + keyword + "%")); //content like '%'?'%'
		}else if(type.equals("title")) {
			builder.and(board.title.like("%"+keyword+"%")); //title like '%'?'%'
		}
		builder.and(board.bno.gt(50L)); // bno >50 //gt-> greaterthan
		System.out.println(builder);
		Pageable pageable = PageRequest.of(0, 5); //0페이지에 5건을 찍어라
		Page<BoardVO> result = boardrepository.findAll(builder, pageable);
		System.out.println("getSize:" + result.getSize());
		System.out.println("getTotalElements:" + result.getTotalElements());
		System.out.println("getTotalPages:" + result.getTotalPages());
		System.out.println("nextPageable:" + result.nextPageable());
		return result.getContent();
	}
	
	
	@GetMapping("/boardlist.do/{pageno}")
	public List<BoardVO> pageInfo(@PathVariable int pageno){
		Timestamp sdate = Timestamp.valueOf("2022-06-29 00:00:000");
		Timestamp edate = Timestamp.valueOf("2022-06-30 00:00:000");
		Pageable paging = PageRequest.of(pageno, 10, Sort.by(Direction.DESC, "writer", "regDate", "bno")); // page는 0부터 시작
																										// 1page의 건수는
																										// 10으로 설정

		Page<BoardVO> result = boardrepository.findByregDateBetweenOrderByBno(sdate, edate, paging);
		System.out.println("페이지번호 : " + result.getNumber());
		System.out.println("전체건수 : " + result.getTotalElements());
		System.out.println("페이지수 : " + result.getTotalPages());
		System.out.println("건수의 수 : " + result.getNumberOfElements());

		List<BoardVO> blist = result.getContent();
		
		return blist;
	}
	@GetMapping("/carlist.do")
	public List<CarVO> getCarList(){
		List<CarVO> carlist = new ArrayList<>();
		CarVO c1 = new CarVO(1L, "a", 1000, "white");
		CarVO c2 = new CarVO(2L, "b", 2000, "black");
		CarVO c3 = new CarVO(3L, "c", 3000, "red");
		CarVO c4 = CarVO.builder().model("d").price(4000).color("blue").build();
		carlist.add(c1);
		carlist.add(c2);
		carlist.add(c3);
		carlist.add(c4);
		return carlist;
 	}
}
