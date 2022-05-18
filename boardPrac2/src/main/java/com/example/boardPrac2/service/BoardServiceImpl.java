package com.example.boardPrac2.service;

import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.FileVO;
import com.example.boardPrac2.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper boardMapper;

    @Override
    public List<BoardDto> getFileBoardList() {
        return boardMapper.getFileBoardList();
    }

    @Override
    public BoardDto fileBoardDetail(int b_no) {
        return boardMapper.fileBoardDetail(b_no);
    }

    @Override
    public int fileBoardInsert(BoardDto boardDto) {
        return boardMapper.fileBoardInsert(boardDto);
    }

    @Override
    public int fileBoardUpdate(BoardDto boardDto) {
        return boardMapper.fileBoardUpdate(boardDto);
    }

    @Override
    public int fileBoardDelete(int b_no) {
        return boardMapper.fileBoardDelete(b_no);
    }

    @Override
    public List<BoardDto> getFileBoardList(Criteria cri) {
        return boardMapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(Criteria cri) {
        return boardMapper.getTotalCount(cri);
    }

    //파일 업로드
    @Override
    public int fileInsert(FileVO file) {
        return boardMapper.fileInsert(file);
    }

    //파일 다운로드
    @Override
    public FileVO fileDown(int b_no) {
        return boardMapper.fileDown(b_no);
    }
}
