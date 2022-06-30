package com.kosta.myapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.MemberRepository;
import com.kosta.myapp.vo.MemberDTO;
import com.kosta.myapp.vo.MemberRoleEnum;

@SpringBootTest
public class MemberTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	public void jpqlTest2() {
		List<Object[]> plist = memberRepository.getMemberwithProfileCount2("1");
		plist.forEach(objarr->{
			System.out.println(objarr[0]+"멤버의 프로파일 건수 --> "+objarr[1]);
		});
	}
	//@Test
	public void jpqlTest1() {
		List<Object[]> plist = memberRepository.getMemberwithProfileCount1("1");
		plist.forEach(objarr->{
			System.out.println(Arrays.toString(objarr));
		});
	}
	
	//@Test
	public void delete() {
		memberRepository.deleteById("ohio05105");
	}
	
	//@Test
	public void selectById() {
		memberRepository.findById("ohio05102").ifPresentOrElse(m->{
			System.out.println(m);
		}, ()->{
			System.out.println("해당 멤버는 존재하지 않습니다.");
		});
	}
	//@Test
	public void update() {
		memberRepository.findById("ohio05102").ifPresentOrElse(m->{
			m.setMname("id"+m.getMid());
			m.setMpassword("1111");
			m.setMrole(MemberRoleEnum.MANAGER);
			memberRepository.save(m);
		}, ()->{
			System.out.println("해당 멤버는 존재하지 않습니다.");
		});
		selectById();
	}
	//@Test
	public void selectAll() {
		memberRepository.findAll().forEach(m -> {
			System.out.println(m);
		});
	}

	// @Test
	public void insert() {
		IntStream.rangeClosed(1, 5).forEach(m -> {
			MemberDTO member = MemberDTO.builder().mid("ohio0510" + m).mname("멤버" + m).mpassword("1234")
					.mrole(m % 2 == 0 ? MemberRoleEnum.ADMIN : MemberRoleEnum.USER).build();
			memberRepository.save(member);
		});
	}
}
