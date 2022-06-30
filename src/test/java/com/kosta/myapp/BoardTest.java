package com.kosta.myapp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.kosta.myapp.repository.BoardRepository;
import com.kosta.myapp.vo.BoardVO;

@SpringBootTest
public class BoardTest {
	@Autowired
	BoardRepository boardrepository;

	@Test
	public void jpqlTest4() {
		List<BoardVO> blist = boardrepository.selectAllbyTitle4("2", 30L);
		blist.forEach(b -> {
			System.out.println(b);
		});
	}
	
	//@Test
	public void jpqlTest3() {
		List<BoardVO> blist = boardrepository.selectAllbyTitle3("1", 30L);
		blist.forEach(b -> {
			System.out.println(b);
		});
	}
	//@Test
	public void jpqlTest1() {
		List<BoardVO> blist = boardrepository.selectAllbyTitle("1", 10L);
		blist.forEach(b -> {
			System.out.println(b);
		});
	}
	
	//@Test
	public void Paging3() {
		Timestamp sdate = Timestamp.valueOf("2022-06-29 00:00:000");
		Timestamp edate = Timestamp.valueOf("2022-06-30 00:00:000");
		Pageable paging = PageRequest.of(1, 10, Sort.by(Direction.DESC, "writer", "regDate", "bno")); // page는 0부터 시작
																										// 1page의 건수는
																										// 10으로 설정

		Page<BoardVO> result = boardrepository.findByregDateBetweenOrderByBno(sdate, edate, paging);
		System.out.println("페이지번호 : " + result.getNumber());
		System.out.println("전체건수 : " + result.getTotalElements());
		System.out.println("페이지수 : " + result.getTotalPages());
		System.out.println("건수의 수 : " + result.getNumberOfElements());

		List<BoardVO> blist = result.getContent();
		Pageable p = result.nextPageable();
		blist.forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void Paging2() {
		Timestamp sdate = Timestamp.valueOf("2022-06-29 00:00:000");
		Timestamp edate = Timestamp.valueOf("2022-06-30 00:00:000");
		Pageable paging = PageRequest.of(1, 10, Sort.by(Direction.DESC, "writer", "regDate", "bno")); // page는 0부터 시작
																										// 1page의 건수는
																										// 10으로 설정
		boardrepository.findByregDateBetweenOrderByBnoDesc(sdate, edate, paging).forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void Paging() {
		Timestamp sdate = Timestamp.valueOf("2022-06-29 00:00:000");
		Timestamp edate = Timestamp.valueOf("2022-06-30 00:00:000");
		Pageable paging = PageRequest.of(1, 10); // page는 0부터 시작 1page의 건수는 10으로 설정
		boardrepository.findByregDateBetweenOrderByBnoDesc(sdate, edate, paging).forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void regDateBetween() {
		Timestamp sdate = Timestamp.valueOf("2022-06-29 00:00:000");
		Timestamp edate = Timestamp.valueOf("2022-06-30 00:00:000");

		boardrepository.findByregDateBetween(sdate, edate).forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void selByTitle() {
		boardrepository.findByTitle("제목50").forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void selByWriter() {
		boardrepository.findByWriter("작성자1").forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void selByTitleANDWriter() {
		boardrepository.findByWriterAndTitle("작성자2", "내용2").forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void selByTitleLike() {
		boardrepository.findByTitleLike("%제목%").forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void selByTitleContaining() {
		boardrepository.findByTitleContaining("제목").forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void selByBnoGreaterThan() {
		boardrepository.findByBnoGreaterThan(90L).forEach(b -> {
			System.out.println(b);
		});
	}

	// @Test
	public void countAndExists() {
		Long count = boardrepository.count();
		System.out.println("board table의 count(*) =>" + count);

		boolean result = boardrepository.existsById(110L);
		System.out.println(result ? "존재" : "존재안함");
	}

	// 1건조회
	// @Test
	public void testone() {
		boardrepository.findById(101L).ifPresent(board -> {
			System.out.println(board);
		});
	}

	// @Test //존재하지 않는 번호 입력시 알려주는 메서드
	public void testone2() {
		boardrepository.findById(1L).ifPresentOrElse(board -> {
			System.out.println(board);
		}, () -> {
			System.out.println("해당번호의 데이터가 존재하지 않습니다.");
		});
	}

	// @Test
	public void testone3() {
		Optional<BoardVO> board = boardrepository.findById(100L);
		// Optional -> 있어? 없어?
		if (board.isPresent()) { // 있으면
			BoardVO b = board.get(); // board를 갖고와
			System.out.println(b);
		} else {
			System.out.println("해당번호의 데이터가 존재하지 않습니다.");
		}
	}

	// 수정
	// @Test
	public void testupdate() {
		boardrepository.findById(200L).ifPresentOrElse(board -> {
			board.setTitle("제목수정" + board.getBno());
			boardrepository.save(board);
		}, () -> {
			BoardVO board = BoardVO.builder().title("제목new").content("내용new").writer("작성자new").build();
		});
	}

	// @Test
	public void testupdate2() {
		Optional<BoardVO> board = boardrepository.findById(1L);
		if (board.isPresent()) {
			BoardVO b = board.get();
			b.setTitle("제목수정" + b.getBno());
			b.setContent("내용수정" + b.getBno());
		}
	}

	// 삭제

	// @Test
	public void test2() {
		List<BoardVO> blist = (List<BoardVO>) boardrepository.findAll(); // findAll == select *
		blist.forEach(board -> {
			System.out.println(board);
		});
	}

	// @Test
	// insert
	public void test1() {

		IntStream.rangeClosed(1, 100).forEach(i -> {
			BoardVO board = BoardVO.builder().title("제목" + i).content("내용" + i).writer("작성자" + i).build();

			boardrepository.save(board); // save == insert into t_boards values()
		});
	}
}
