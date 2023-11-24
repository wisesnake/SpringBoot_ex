package org.zerock.board01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board01.domain.Board;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title..."+i)
                    .content("content...."+i)
                    .writer("user...."+i)
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();
        //board가 만약 null일 경우(쿼리가 결과를 null을 반환할 경우) throw하는 optional클래스의 메서드.

        log.info(board);

    }

    @Test
    public void updateTest(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);

        board.change("update..title 100", "update content 100...");

        //boardRepository 객체를 이용해 bno를 맞춰 검색결과를 뽑아온 후, 해당 객체를 수정하여 board 엔티티 객체의 change메소드를 이용하여 수정함
    }

    @Test
    public void deleteTest(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging(){
//        Page 포함된 전체 목록에서 해당 항목의 위치에 대한 정보를 얻을 수 있음
//        Pageable 페이지네이션 정보의 추상 인터페이스
//        PageRequest Pageable의 구현체

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").ascending());
        log.info(pageable);
    }
}
