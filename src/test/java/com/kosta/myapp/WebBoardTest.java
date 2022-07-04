package com.kosta.myapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.WebBoardRepository;
import com.kosta.myapp.repository.WebReplyRepository;
import com.kosta.myapp.vo.relation.WebBoard;
import com.kosta.myapp.vo.relation.WebReply;

@SpringBootTest
public class WebBoardTest {

	@Autowired
	WebBoardRepository boardRepository;
	@Autowired
	WebReplyRepository replyRepository;
	
	@Test
	public void insert2() {
		IntStream.rangeClosed(1, 10).forEach(b->{
			WebBoard board = WebBoard.builder()
			.title("제목"+b)
			.content("내용"+b)
			.writer("hyein"+(b%5)+1)
			.build();
			boardRepository.save(board);
		});
	}
	
	//@Test
	public void insert() {
		IntStream.rangeClosed(1, 50).forEach(b->{
			WebBoard board = WebBoard.builder()
					.title("타이틀"+b)
					.content("내용"+b)
					.writer("작성자"+(b%5)+1)
					.build();
			List<WebReply> rlist = new ArrayList<>();
			IntStream.rangeClosed(1, 10).forEach(r->{
				WebReply reply = WebReply.builder()
						.replyer("댓글작성자"+r)
						.replyText("댓글내용"+r)
						.board(board)
						.build();
				rlist.add(reply);
			});
			board.setReplies(rlist);
			boardRepository.save(board);
		});
	}
}
