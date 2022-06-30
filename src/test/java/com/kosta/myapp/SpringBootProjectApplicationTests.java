package com.kosta.myapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.vo.CarVO;

@SpringBootTest
class SpringBootProjectApplicationTests {

	@Test
	public void test1() {
		CarVO car1 = new CarVO();
		System.out.println(car1);
		CarVO car2 = new CarVO(2L, "aaa", 100, "red");
		System.out.println(car2.getModel());
		car2.setColor("white");
		System.out.println(car2);
		
		CarVO car3 = CarVO.builder()
				.model("abc모델")
				.price(2000)
				.color("black")
				.build();
		CarVO car4 = CarVO.builder()
				.model("abc모델")
				.price(2000)
				.color("black")
				.build();
		System.out.println(car3.toString());
		System.out.println(car3.equals(car4));
	}
}
