package org.zerock.board01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
    private int page;
    private int size;
    private int total;

    //시작페이지 번호
    private int start;
    //끝페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;
    //다음 페이지의 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName="withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){
        if(total <= 0){
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;
        //화면에서의 마지막번호
        this.start = this.end - 9;
        //10페이지씩 출력할거고, 각 첫페이지는 1,11,21와 같이 나갈거기 때문에 -9 하는거임

        this.prev = this.start > 1;
        //start가 1보다 높아야 두번째 섹션 이상이므로, 그제서야 prev버튼을 true 로 설정.

        this.next = total > this.end * this.size;

    }
}
