package com.kosta.myapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.FetchType;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;

import com.kosta.myapp.repository.FreeBoardRepliesRepository;
import com.kosta.myapp.repository.FreeBoardRepository;
import com.kosta.myapp.vo.relation.FreeBoard;
import com.kosta.myapp.vo.relation.FreeBoardReply;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit
public class FreeBoardReplyTest {

	@Autowired
	FreeBoardRepository boardRepository;
	@Autowired
	FreeBoardRepliesRepository repliesRepository;
	
	@Test
	public void getCountReply() {
		List<Object[]> rlist1 = boardRepository.getCountReply1();
		List<Object[]> rlist2 = boardRepository.getCountReply2();
		
		rlist1.forEach(r->{
			log.info("rlist1 : "+Arrays.toString(r));
		});
		log.info("---------------------------------------");
		rlist2.forEach(r->{
			log.info("rlist2 : "+Arrays.toString(r));
		});
	}
	
	//@Test
	public void findByBnoGreaterThan() {
		Long bno = 290L;
		Pageable page = PageRequest.of(0, 5);
		Page<FreeBoard> result = boardRepository.findByBnoGreaterThan(bno, page);
		log.info("getNumber = "+result.getNumber());
		log.info("getNumberOfElements = "+result.getNumberOfElements());
		log.info("getTotalElements = "+result.getTotalElements());
		log.info("getTotalPages = "+result.getTotalPages());
		log.info("getSize = "+result.getSize());
		List<FreeBoard> blist = result.getContent();
		blist.forEach(b->{
			log.info(b.toString());
		});
	}
	
	//@Test
	public void retrieveAll() {
		boardRepository.findAll(Sort.by(Direction.DESC, "title")).forEach(b->{
			log.info(b.toString()); //양방향일 때는 toString에 제약을 걸어주지 않으면 계속 서로의 테이블을 조회하게 되어서 무한루프가 걸리게 되고 메모리 부족이되면 종료가 됨
		});
	}
	
	//@Test
	public void getReplyCount() {
		Long bno = 303L;
		boardRepository.findById(bno).ifPresent(b->{
			log.info("댓글 건수 : " + b.getReplies().size());
		});
	}
	
	//@Test
	public void insert5() {
		//댓글에다가 바로 insert함
		Long bno = 303L;
		boardRepository.findById(bno).ifPresent(b->{
			FreeBoardReply reply = FreeBoardReply.builder()
					.reply("!!댓글내용")
					.replyer("!!댓글작성자")
					.board(b)
					.build();
			repliesRepository.save(reply);
		});
	}
	
//	@Transactional //fetch = FetchType.LAZY인 경우 @Transactional필수임, @Transactional은 @Commit 필수임
//	@Test
	
	public void insert4() {
		//board에 생성한 댓글을 insert
		Long bno = 303L;
		
		boardRepository.findById(bno).ifPresent(b->{
			List<FreeBoardReply> replies = b.getReplies();
			if(replies== null) {
				replies = new ArrayList<>();
			}
			FreeBoardReply reply = FreeBoardReply.builder()
					.reply("댓글내용~~")
					.replyer("댓글작성자~~")
					.board(b)
					.build();
			replies.add(reply);
			//b.setReplies(replies); -> getReplies를 했기 때문에 주소를 알고 있어서 set을 안해도 그 주소에 add됨
			boardRepository.save(b);
		});
	}
	//@Test
	//insert 댓글(reply)
	public void insert3() {
		Long bno = 303L;
		
		boardRepository.findById(bno).ifPresent(b->{
			List<FreeBoardReply> replies = b.getReplies();
			if(replies== null) {
				replies = new ArrayList<>();
			}
			FreeBoardReply reply = FreeBoardReply.builder()
					.reply("댓글내용")
					.replyer("댓글작성자")
					.board(b)
					.build();
			FreeBoardReply reply2 = FreeBoardReply.builder()
					.reply("댓글내용2")
					.replyer("댓글작성자2")
					.board(b)
					.build();
			replies.add(reply);
			replies.add(reply2);
			//b.setReplies(replies); -> getReplies를 했기 때문에 주소를 알고 있어서 set을 안해도 그 주소에 add됨
			boardRepository.save(b);
		});
	}
	//@Test
	//insert 게시글 (board)
	public void insert2() {
		IntStream.rangeClosed(11, 20).forEach(b->{
			FreeBoard board = FreeBoard.builder()
					.title("제목"+b)
					.content("내용"+b)
					.writer("작성자"+b)
					.build();
			boardRepository.save(board);
		});
	}
	
	//@Test
	public void insert() {
		//board 10건, reply 5건 입력
		
		IntStream.rangeClosed(1, 10).forEach(i->{
			FreeBoard board = FreeBoard.builder()
					.title("제목"+i)
					.writer("작성자"+i)
					.content("내용"+i)
					.build();
			List<FreeBoardReply> rlist = new ArrayList<>();
			IntStream.rangeClosed(1, 5).forEach(j->{
				FreeBoardReply reply = FreeBoardReply.builder()
						.reply(i+" - 댓글"+j)
						.replyer(i+" - 댓글작성자"+j)
						.board(board) //어떤 게시글의 댓글인지 setting
						.build();
				rlist.add(reply);
			});
			board.setReplies(rlist);
			boardRepository.save(board);
		});
	}
}
