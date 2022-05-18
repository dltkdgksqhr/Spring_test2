package com.example.boardPrac2.mapper;

import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.ReplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ReplyMapper {

    // 댓글 생성
    public int insert(ReplyVO vo);
    // 댓글 읽기
    public ReplyVO read(Long rno);

    // 댓글 삭제
    public int delete(Long rno);

    // 댓글 수정
    public int update(ReplyVO vo);

    //댓글 페이징 - criteria 이용
    // mybatis에서 두개 이상의 데이터를 파라미터로 전달하기 위해서는 세가지 방법이 잇는데 그중 @Param을 이용한다.

    public List<ReplyVO> getListWithPaging(
            @Param("cri") Criteria cri,
            @Param("bno") int bno
    );




}
