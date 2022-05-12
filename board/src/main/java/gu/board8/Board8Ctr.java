package gu.board8;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Request;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import gu.common.FileUtil;
import gu.common.FileVO;
import gu.common.SearchVO;

@Controller 
public class Board8Ctr {

    @Autowired
    private Board8Svc boardSvc;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/board8List")
    public String boardList(SearchVO searchVO, ModelMap modelMap) {
        
        if (searchVO.getBgno() == null) {
            searchVO.setBgno("1"); 
        }
        searchVO.pageCalculate( boardSvc.selectBoardCount(searchVO) ); // startRow, endRow

        List<?> listview  = boardSvc.selectBoardList(searchVO);// searchVO값을 selectBoardList 변수 값으로 사용한다.그리고 List타입의 listview에 담는다.
        
        modelMap.addAttribute("listview", listview); // modelMap에 listview와 searchVO를 담는다.
        modelMap.addAttribute("searchVO", searchVO);
        
        return "board8/BoardList";
    }
    
    /** 
     * 글 쓰기. 
     */
    @RequestMapping(value = "/board8Form")
    public String boardForm(HttpServletRequest request, ModelMap modelMap) {
        String bgno = request.getParameter("bgno");
        String brdno = request.getParameter("brdno"); // brdno값을 받아온 후 brdno에 담는다.
        if (brdno != null) {
            BoardVO boardInfo = boardSvc.selectBoardOne(brdno); // selectBoardOne메서드의 변수를 brdno를 적용한다. 결과 값을 boardInfo에 담는다.
            List<?> listview = boardSvc.selectBoard8FileList(brdno); // selectBoard8FileList메서드에 brdno를 변수로 보낸다. 그 결과 값을 listview에 담는다.
        
            modelMap.addAttribute("boardInfo", boardInfo); // 각각의 결과값을 modelMap에 담는다.
            modelMap.addAttribute("listview", listview);
            bgno = boardInfo.getBgno(); //boardInfo class에서 bgno값을 가져와서 bgno에 담는다. 
        }
        modelMap.addAttribute("bgno", bgno); // modelMap에 담는다.
        
        return "board8/BoardForm";
    }
    
    /**
     * 글 저장.
     */
    @RequestMapping(value = "/board8Save")
    public String boardSave(HttpServletRequest request, BoardVO boardInfo) {
        String[] fileno = request.getParameterValues("fileno");
        
        FileUtil fs = new FileUtil();
        List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());// BoardInfo에서 UploadFile메서드에서 가져온 값을 saveAllfiles메서드의 변수로 넣고 로직을 탄다.

        boardSvc.insertBoard(boardInfo, filelist, fileno);

        return "redirect:/board8List?bgno=" + boardInfo.getBgno();
    }

    /**
     * 글 읽기.
     */
    @RequestMapping(value = "/board8Read")
    public String board8Read(HttpServletRequest request, ModelMap modelMap) {
        
        String brdno = request.getParameter("brdno");
        
        boardSvc.updateBoard8Read(brdno); // boardSVC클레스의 updateBoard8Read메서드에 brdno를 변수로 보낸다.
        BoardVO boardInfo = boardSvc.selectBoardOne(brdno); //boardsvc의 selectBoardOne메서드 실행
        List<?> listview = boardSvc.selectBoard8FileList(brdno); //boardSvc의 selectBoard8FileList 실행
        List<?> replylist = boardSvc.selectBoard8ReplyList(brdno); // boardSvc의 selectBoard8ReplyList 실행
        
        modelMap.addAttribute("boardInfo", boardInfo);
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("replylist", replylist);
        
        return "board8/BoardRead";
    }
    
    /**
     * 글 삭제.
     */
    @RequestMapping(value = "/board8Delete")
    public String boardDelete(HttpServletRequest request) {
        String brdno = request.getParameter("brdno");
        String bgno = request.getParameter("bgno");
        
        boardSvc.deleteBoardOne(brdno); // boardSvc클래스 deleteBoardOne메서드로 brdno를 매개변수로 보낸다. 그리고 SQL문을 탄다.
        
        return "redirect:/board8List?bgno=" + bgno;
    }

    /* ===================================================================== */
    
    /**
     * 댓글 저장. 원본
    */
    @RequestMapping(value = "/board8ReplySave")
    public String board8ReplySave(HttpServletRequest request, BoardReplyVO boardReplyInfo, ModelMap modelMap) {
        
        boardSvc.insertBoardReply(boardReplyInfo); // insertBoardReply메서드에 boardReplyInfo를 매개변수로 잡고 로직을 탄다. 

        modelMap.addAttribute("replyInfo", boardReplyInfo); // modelMap에 boardReplyInfo매개변수를 저장한다.
        
        return "board8/BoardReadAjax4Reply";        
    }
     
    /**
     * 댓글 삭제. 원본 
     * @return 
     */
    @RequestMapping(value = "/board8ReplyDelete")
    public void board8ReplyDelete(HttpServletResponse response, BoardReplyVO boardReplyInfo, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper(); // Json 형식을 사용할 때, 응답들을 직렬화하고 요청들을 역직렬화 할 때 사용하는 기술이다. 
        String referer = request.getHeader("Refere");
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            if (!boardSvc.deleteBoard8Reply(boardReplyInfo.getReno()) ) { // Reno 변수를 deleteBoard8Reply메서드에 넘기지 않는다.
                response.getWriter().print(mapper.writeValueAsString("Fail"));
            } else {
                response.getWriter().print(mapper.writeValueAsString("OK"));
                request.getSession().setAttribute("redirectURI", referer);
            }
        } catch (IOException ex) {
            System.out.println("오류: 댓글 삭제에 문제가 발생했습니다.");
        }
        
    }
  
}
