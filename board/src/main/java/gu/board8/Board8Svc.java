package gu.board8;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gu.common.FileVO;
import gu.common.SearchVO;

@Service
public class Board8Svc {

    @Autowired
    private SqlSessionTemplate sqlSession;    
    @Autowired
    private DataSourceTransactionManager txManager;
        
    public Integer selectBoardCount(SearchVO param) {
        return sqlSession.selectOne("selectBoard8Count", param);
    }
    
    public List<?> selectBoardList(SearchVO param) {
        return sqlSession.selectList("selectBoard8List", param);
    }
    
    /**
     * 글 저장.
     */
    public void insertBoard(BoardVO param, List<FileVO> filelist, String[] fileno) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition(); //트랜잭션 객체 생성
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); //트랜잭션 안에서 새로운 트랜잭션을 여는 경우
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
            if (param.getBrdno() == null || "".equals(param.getBrdno())) {
                 sqlSession.insert("insertBoard8", param); // param의 brdno가 null이면 insert 그외는 update 
            } else {
                sqlSession.update("updateBoard8", param);
            }
            
            if (fileno != null) {
                HashMap<String, String[]> fparam = new HashMap<String, String[]>();
                fparam.put("fileno", fileno);
                sqlSession.insert("deleteBoard8File", fparam);
            }
            
            for (FileVO f : filelist) {
                f.setParentPK(param.getBrdno()); // 반복해서 parentpk에 brdno를 매개변수로보내서 삽입한다.
                sqlSession.insert("insertBoard8File", f);
            }
            txManager.commit(status);  // 커밋
        } catch (TransactionException ex) {
            txManager.rollback(status); // 예외 발생시 롤백 
            System.out.println("데이터 저장 오류: " + ex.toString());
        }            
    }
 
    public BoardVO selectBoardOne(String param) {
        return sqlSession.selectOne("selectBoard8One", param);
    }
    
    public void updateBoard8Read(String param) {
        sqlSession.insert("updateBoard8Read", param);
    }
    
    public void deleteBoardOne(String param) {
        sqlSession.delete("deleteBoard8One", param);
    }
    
    public List<?> selectBoard8FileList(String param) {
        return sqlSession.selectList("selectBoard8FileList", param);
    }
    
    /* =============================================================== */
    public List<?> selectBoard8ReplyList(String param) {
        return sqlSession.selectList("selectBoard8ReplyList", param);
    }
    
    /**
     * 댓글 저장. 
     */
    public void insertBoardReply(BoardReplyVO param) {
        if (param.getReno() == null || "".equals(param.getReno())) { // param값이 빈값이면 insert SQL로 넘어간다. 아니면 다음 if문으로 
            if (param.getReparent() != null) { // reparent메서드 값이 null이 아니면 
                BoardReplyVO replyInfo = sqlSession.selectOne("selectBoard8ReplyParent", param.getReparent()); // getparent값을 selectOne 변수로 사용후 결과값을 replyInfo로 담는다.
                param.setRedepth(replyInfo.getRedepth()); // replyInfo클래스의 Redepth메서드의 값을 매개변수로 BoardReplyVO의 Redpth의 저장
                param.setReorder(replyInfo.getReorder() + 1); // replyInfo클래스의 getReorder메서드의 값을 매개변수로 BoardReplyVO의 Reorder에 +1 더해서 저장
                sqlSession.selectOne("updateBoard8Reply", replyInfo); 
            } else {
                Integer reorder = sqlSession.selectOne("selectBoard8ReplyMaxOrder", param.getBrdno()); //param의 Reno의 값이 null이면 brdno를 매개변수로해서 selectBoard8ReplyMaxOrder의 SQL문 탄다.
                param.setReorder(reorder);
            }
            
            sqlSession.insert("insertBoard8Reply", param); // 이건 뭐지....
        } else {
            sqlSession.insert("updateBoard8Reply", param); //param값을 매개변수로해서 updateBoardReply SQL문을 탄다.
        }
    }   
    
    /**
     * 댓글 삭제.
     * 자식 댓글이 있으면 삭제 안됨. 
     */
    public boolean deleteBoard8Reply(String param) {
        Integer cnt = sqlSession.selectOne("selectBoard8ReplyChild", param);
        
        if ( cnt > 0) {
            return false;
        }
        
        sqlSession.update("updateBoard8Reply", param);
        
        sqlSession.delete("deleteBoard8Reply", param);
        
        return true;
    }    
}
