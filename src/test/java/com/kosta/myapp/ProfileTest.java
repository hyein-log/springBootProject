package com.kosta.myapp;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.MemberProfileRepository;
import com.kosta.myapp.repository.MemberRepository;
import com.kosta.myapp.vo.MemberDTO;
import com.kosta.myapp.vo.ProfileDTO;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class ProfileTest {

	@Autowired
	MemberProfileRepository profileRepository;
	@Autowired
	MemberRepository memberRepository;
	
	
	
	//@Test
	public void delete() {
		profileRepository.findById(130L).ifPresentOrElse(p->{
			profileRepository.delete(p);
		}, ()->{
			System.out.println("해당번호의 데이터가 없습니다.");
		});
	}
	
	@Test
	public void selectByMember() {
		MemberDTO member = memberRepository.findById("ohio05101").orElse(null);
		if(member== null) {
			return;
		}
		List<ProfileDTO> plist = profileRepository.findByMember(member);
		plist.forEach(p->{
			log.info(p.toString());
			System.out.println(p.getMember());
			log.info("--------------");
		});
	}
	
	//@Test
	public void update() {
		profileRepository.findById(130L).ifPresentOrElse(p->{
			p.setCurrent_yn(true);
			p.setFname("updatejpg");
			profileRepository.save(p);
		}, ()->{
			System.out.println("해당번호의 데이터가 없습니다.");
		});
	}
	
	//@Test
	public void selectByid() {
		profileRepository.findById(130L).ifPresentOrElse(p->{
			System.out.println(p);
		}, ()->{
			System.out.println("해당번호의 데이터가 없습니다.");
		});
	}
	
	//@Test
	public void selectAll() {
		profileRepository.findAll().forEach(p->{
			System.out.println(p);
		});
	}
	
	//@Test
	public void insert() {
		Optional<MemberDTO> member = memberRepository.findById("ohio05101");
		if(member.isEmpty()) {
			System.out.println("데이터가 존재하지 않습니다.");
			return;
		}
		MemberDTO existMember = member.get();
		IntStream.rangeClosed(1, 10).forEach(i->{
			ProfileDTO profile = ProfileDTO.builder()
					.fname("face"+i+"jpg")
					.current_yn(i==1?true:false)
					.member(existMember)
					.build();
			profileRepository.save(profile);
		});
	}
}
