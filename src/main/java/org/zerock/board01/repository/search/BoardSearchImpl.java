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
        //QBoard 클래스는 Querydsl이 Board 엔터티에 대한 쿼리를 생성하는 데 필요한 메타모델 정보를 담고 있는 클래스입니다.
        // 이 클래스는 Querydsl이 빌드되는 과정에서 자동으로 생성되며, 엔터티의 필드, 관계, 메소드 등에 대한 정보를 정적으로 제공합니다.
        // 이러한 메타모델 클래스를 사용하면 컴파일 타임에 타입 안정성을 확보하면서 동적인 쿼리를 작성할 수 있습니다.
        // 코드에서는 QBoard.board와 같이 사용하여 엔터티의 속성을 참조하고 쿼리를 생성할 수 있습니다.


        JPQLQuery query = from(board);
        // JPQLQuery는 Querydsl에서 제공하는 인터페이스로, JPQL 쿼리를 표현하고 실행하는 데 사용됩니다.
        // from(board)는 board라는 엔터티에 대한 JPQL 쿼리를 시작한다는 것을 의미합니다.
        //이렇게 함으로써 Querydsl은 Board 엔터티를 대상으로 하는 JPQL 쿼리를 생성할 수 있게 됩니다.

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
        //query를 실행하고, 결과를 리스트로 가져옴, 실행 결과는 Board엔티티에 해당하는 레코드들.

        long count = query.fetchCount();
        //페이징처리를 위해 전체 쿼리 결과의 개수를 가져옴.

        return new PageImpl<>(list,pageable,count);
        //현재 페이지에 대한 엔티티리스트 list, 페이지 정보(번호,크기,정렬방법 등) pageable, 전체 결과의 개수 count를 Page객체로 만들어서 리턴함.
    }
}
