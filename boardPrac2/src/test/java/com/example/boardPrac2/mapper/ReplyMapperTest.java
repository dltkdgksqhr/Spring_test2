package com.example.boardPrac2.mapper;

import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.ReplyVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReplyMapperTest {

    @Autowired
    ReplyMapper mapper;

    @Test
    @DisplayName("insert")
    public void testCreate() {
        ReplyVO vo = new ReplyVO();

        vo.setBno(109);
        vo.setReply("댓글 테스트");
        vo.setReplyer("replayer");

        mapper.insert(vo);
    }

    @Test
    @DisplayName("select")
    public void testRead() {

        Long targetRno = 3L;

        ReplyVO vo = mapper.read(targetRno);
        System.out.println("vo = " + vo);
    }

    @Test
    @DisplayName("delete")
    public void testDelete() {

        Long targetRno = 3L;

        mapper.delete(targetRno);
    }

    @Test
    @DisplayName("update")
    public void testUpdate() {
        Long targerRno = 4L;

        ReplyVO vo = mapper.read(targerRno);
        vo.setReply("수정입니다.");

        int count = mapper.update(vo);
        System.out.println("count = " + count);

    }

    @Test
    @DisplayName("paging")
    public void testList() {
        Criteria cri = new Criteria();
        List<ReplyVO> replies = mapper.getListWithPaging(cri, 109);

        System.out.println("replies = " + replies);

    }
}