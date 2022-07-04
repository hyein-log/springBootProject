package com.kosta.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.myapp.repository.WebBoardRepository;
import com.kosta.myapp.repository.WebReplyRepository;

@Controller
@RequestMapping("/board/*")
public class WebBoardController {

	@Autowired
	WebBoardRepository boardRepository;
	
	@Autowired
	WebReplyRepository replyRepository;
	
	@GetMapping("/replyByBoard.go")
	public String replyByBoard(Long bno, Model model) {
		model.addAttribute("reply", replyRepository.getRepliesOfBoard(boardRepository.findByBno(bno)));
		return "WebBoard/replyByBoard";
	}
	
	@GetMapping("/boardlist.go")
	public String boardList(Model model) {
		model.addAttribute("blist",boardRepository.findAll());
		return "WebBoard/boardlist";
	}
}
