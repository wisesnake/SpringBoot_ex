package org.zerock.board01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board01.domain.Board;
import org.zerock.board01.domain.QBoard;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;//Q도메인 객체

        JPQLQuery<Board> query = from(board); // select.. from board

        query.where(board.title.contains("1")); // where title like ...

        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;

        JPQLQuery query = from(board);
        //query객체를 통해 실행되는 JPQL쿼리는 from board를 사용함.
        //JPQL = Java Persistence Query Language, DB 테이블을 대상으로 쿼리하는게 아니라, 엔티티 객체를 대상으로 쿼리한다.

        if((types != null && types.length > 0) && keyword != null){ // 검색조건(types) 와 키워드(keyword) 있을 때,

            BooleanBuilder booleanBuilder = new BooleanBuilder();
            //BooleanBuilder : 쿼리문에 and 나 or 이 들어갔을 때, 쿼리 우선실행도가 달라지게 되므로, where절에 and 혹은 or를 사용했을 때는
            //                  괄호를 이용해 묶어서 우선순위를 조정해야(and or이 우선되도록) 함.
            //                  이 때, BooleanBuilder 는 or 혹은 and 메소드로 묶은 쿼리들을 ()로 묶어주는 역할을 함.
            for(String type: types){
                //types 매개변수 데이터값의 개수만큼 type에 초기화하여 반복문 실행(검색조건을 여러개를 체크했을 때)
                switch(type){
                    case "t":
                    booleanBuilder.or(board.title.contains(keyword));
                    break;
                    case "c":
                    booleanBuilder.or(board.content.contains(keyword));
                    break;
                    case "w":
                    booleanBuilder.or(board.writer.contains(keyword));
                    break;
                }
                //type에 각각 문자열 t,c,w 일때(검색조건에 제목,내용,글쓴이가 체크되어있을 때) booleanBuilder.or를 이용해 where문 or을 사용하여 생성.
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0L));
        //bno > 0

        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list,pageable,count);
    }
}
