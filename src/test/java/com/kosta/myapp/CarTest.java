package com.kosta.myapp;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.CarRepository;
import com.kosta.myapp.vo.CarVO;

@SpringBootTest
public class CarTest {

	@Autowired
	CarRepository carRepository;

	@Test
	public void countAndExists() {
		Long count = carRepository.count();
		System.out.println("car table의 count(*) =>"+count);
		
		boolean result = carRepository.existsById(110L);
		System.out.println(result?"존재":"존재안함");
	}
	
	//@Test
	public void delete() {
		carRepository.findById(110L).ifPresentOrElse(car -> {
			carRepository.delete(car);
		}, () -> {
			System.out.println("삭제할 데이터가 없습니다.");
		});
	}

	//@Test
	@ParameterizedTest
	@ValueSource(longs = {110L, 111L})
	public void update(Long args) {
		System.out.println("args-->"+args);
		carRepository.findById(args).ifPresentOrElse(car -> {
			car.setColor("pink");
			car.setPrice(3000);

			carRepository.save(car);
			System.out.println(car);
		}, () -> {
			System.out.println("해당번호의 데이터는 존재하지 않습니다.");
		});
	}

	// @Test
	public void selectAll() {
		List<CarVO> car = (List<CarVO>) carRepository.findAll();
		car.forEach(c -> {
			System.out.println(c);
		});
	}

	// @Test
	public void insertFunction() {
		String[] arr = { "black", "red", "white" };
		IntStream.rangeClosed(1, 10).forEach(index -> {
			int num = new Random().nextInt(arr.length);
			CarVO car = CarVO.builder().color(arr[num]).price(2000).build();
			carRepository.save(car);
		});
	}
}
