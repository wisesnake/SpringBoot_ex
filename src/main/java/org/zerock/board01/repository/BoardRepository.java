package org.zerock.board01.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.board01.domain.Board;


public interface BoardRepository extends JpaRepository<Board,Long> {

}
