package com.kosta.myapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;

import com.kosta.myapp.repository.PDSBoardRepository;
import com.kosta.myapp.repository.PDSFileRepository;
import com.kosta.myapp.vo.relation.PDSBoard;
import com.kosta.myapp.vo.relation.PDSFile;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit
public class PDSBoardTest {

	@Autowired
	PDSBoardRepository pdsBoardRepository;
	@Autowired
	PDSFileRepository pdsFileRepository;
	
	@Test
	public void boardSelectAll() {
		List<PDSBoard> blist = (List<PDSBoard>) pdsBoardRepository.findAll(Sort.by(Direction.DESC, "pname"));
		blist.forEach(b->{
			System.out.println(b);
		});
	}
	
	
	//board에 file 추가
	//@Test
	public void boardAddFile() {
		Long pid = 141L;
		pdsBoardRepository.findById(pid).ifPresent(b->{
			b.setPwriter("작성자수정1");
			b.setPname("메뉴얼수정1");
			List<PDSFile> flist = b.getFiles2();
			flist.remove(0);
			PDSFile file = PDSFile.builder()
					.pdsfilename("insert.jpg")
					.build();
			flist.add(file);
			pdsBoardRepository.save(b);
		});
	}
	
//	@Transactional
//	@Test
	public void updatePDSFile() {
		Long fno = 150L;
		String fname= "파일이름수정.xls";
		int result = pdsBoardRepository.updatePDSFile(fno, fname);
		log.info("result : "+result);
	}
	//@Test
	public void fileNameUpdate() {
		Long fno = 150L;
		String fname= "파일이름수정.xls";
		pdsFileRepository.findById(fno).ifPresent(f->{
			f.setPdsfilename(fname);
			pdsFileRepository.save(f);
		});
	}
	
	//@Test
	public void getFilesCount2() {
		List<Object[]> blist = pdsBoardRepository.getFilesCount1();
		blist.forEach(b->{
			log.info(Arrays.toString(b));
		});
	}
	//@Test
	public void getFilesCount1() {
		List<Object[]> blist = pdsBoardRepository.getFilesCount1();
		blist.forEach(b->{
			log.info(Arrays.toString(b));
		});
	}
	
	//@Test
	public void getFilesInfo2() {
		List<Object[]> blist = pdsBoardRepository.getFilesInfo2();
		blist.forEach(b->{
			log.info(Arrays.toString(b));
		});
	}
	//@Test
	public void getFilesInfo() {
		Long pid = 150L;
		List<Object[]> blist = pdsBoardRepository.getFilesInfo(pid);
		blist.forEach(b->{
			log.info(Arrays.toString(b));
		});
	}
	//@Test
	public void delete() {
		Long pid = 162L;
		pdsBoardRepository.findById(pid).ifPresent(b->{
			pdsBoardRepository.delete(b);
		});
	}
	
	//@Test
	public void update1() {
		Long pid = 159L;
		pdsBoardRepository.findById(pid).ifPresent(b->{
			b.setPname("메뉴얼4");
			b.setPwriter("작성자4");
			pdsBoardRepository.save(b);
		});
	}
	
	
	//@Test
	public void fileSearch() {
		Long fno = 150L;
		pdsFileRepository.findById(fno).ifPresentOrElse(f->{
			log.info(f.toString());
		}, ()->{
			log.info("데이터가 존재하지 않습니다.");
		});
	}
	
	//@Test
	public void retrieveById() {
		Long pid = 147L;
		pdsBoardRepository.findById(pid).ifPresentOrElse(b->{
			log.info("pid : "+b.getPid());
			log.info("pname : "+b.getPname());
			log.info("pwriter : "+b.getPwriter());
			log.info("file2 : "+b.getFiles2());
			log.info("file2 size : "+b.getFiles2().size());
		}, ()->{
			log.info("해당 데이터는 존재하지 않습니다.");
		});
	}
	//@Test
	public void retrieve() {
		pdsBoardRepository.findAll().forEach(board->{
			System.out.println(board);
		});
	}
	//@Test
	public void insert() {
		// Board를 3건, file를 5건 insert
		IntStream.rangeClosed(1, 3).forEach(i -> {
			PDSBoard board = PDSBoard.builder()
					.pname("메뉴얼"+i)
					.pwriter("작성자"+i)
					.build();
			List<PDSFile> filelist = new ArrayList<>();
			IntStream.rangeClosed(1, 5).forEach(j->{
				PDSFile file = PDSFile.builder()
						.pdsfilename("첨부파일"+j+".doc")
						.build();
				filelist.add(file);
			});
			board.setFiles2(filelist);
			pdsBoardRepository.save(board);
		});
	}
}
