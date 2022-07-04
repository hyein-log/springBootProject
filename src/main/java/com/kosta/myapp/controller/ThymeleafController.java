package com.kosta.myapp.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jayway.jsonpath.internal.function.Parameter;
import com.kosta.myapp.repository.FreeBoardRepository;
import com.kosta.myapp.vo.CarVO;

@Controller
public class ThymeleafController {
	
	@GetMapping("/sample1")
	public String test1(Model model) {
		model.addAttribute("score", 100);
		model.addAttribute("myname", "hyein");
		CarVO car = CarVO.builder()
				.carNO(1234L)
				.model("포르쉐")
				.build();
		model.addAttribute("mycar", car);
		return "Thymeleaf1";
	}
	
	@Autowired
	FreeBoardRepository freeboard;
	
	@GetMapping("/sample2")
	public String test2(Model model, HttpSession session) {
		model.addAttribute("boardList", freeboard.findAll());
		model.addAttribute("User1", "작성자10");
		model.addAttribute("salary", 1245678911283F);
		session.setAttribute("user3", "admin");
		return "Thymeleaf2";
	}
	
	@GetMapping("/sample3")
	public String test3(Model model) {
		model.addAttribute("now", new Date());
		model.addAttribute("salary", 1245678911.283F);
		model.addAttribute("message", "hi my name is hyein");
		model.addAttribute("fruits", Arrays.asList("사과","바나나","망고","멜론","수박"));
		
		return "Thymeleaf3";
	}
}
