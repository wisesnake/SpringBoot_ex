package org.zerock.board01.service;

import org.springframework.data.domain.PageRequest;
import org.zerock.board01.dto.BoardDTO;
import org.zerock.board01.dto.PageRequestDTO;
import org.zerock.board01.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

}
