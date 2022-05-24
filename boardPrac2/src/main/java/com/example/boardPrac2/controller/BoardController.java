package com.example.boardPrac2.controller;


import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.FileVO;
import com.example.boardPrac2.dto.PageDTO;
import com.example.boardPrac2.service.BoardService;

import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class BoardController {

    // 스프링 빈 컨테테이너에 BoardService 클래스를 주입하여 하나의 객체를 공유하는 형식으로 사용한다 => single tone 방식
    // 스프링 프레임워크는 기존의 싱글톤 방식의 단점을 보완하여 사용할 수 있고, 그로인해 불필요한 객체 생성을 방지한다.

    @Autowired
    BoardService boardService;

    @RequestMapping("/")
    private String BoardList(Criteria cri, Model model) {
        List<BoardDto> boardDtos = new ArrayList<>();

        boardDtos = boardService.getFileBoardList(cri);
       
        model.addAttribute("boardDtos", boardDtos);
        model.addAttribute("pageMaker", new PageDTO(cri, boardService.getTotal(cri)));


        return "fileBoard/list";
    }

//    @PathVariable은 경로 변수이다.
//    @RequestMapping은 URL 경로를 템플릿 화 할 수 있는데, @PathVariable 을 사용하면
//    매칭되는 부분을 편리하게 조회할 수 있다. 또한 이름과 파라미터 이름이 같으면 생략이 가능하다. (다중 적용가능)
/*  원본 boardDetail 메서드 
    @RequestMapping("/detail/{b_no}")
    private String boardDetail(@PathVariable("b_no") int b_no, Model model) {
  */
        // 클라이언트가 선택한 url의 쿼리문 중 "b_no" 에 해당하는 값의 이름을 파라미터와 동일하게 하여 가져온다.
        // 그리고 이전 list페이지에서 가지고 있는 model을 받아 "detail" 이란 객체이름으로 한 속성을 넣는다.
        // 이 역시 로직을 위해 service의 detail메서드로 전달하고,
        // 데이터베이스로부터 쿼리문을 실행시킨 값을 받아 리턴 받는다.
        // 그리고 "fileBoard/detail" 이라는 논리적 주소를 리턴한다.
//        model.addAttribute("detail", boardService.fileBoardDetail(b_no));

/* 2021.04.29 파일 다운 로드 로직 추가 */
        
        /*if (boardService.fileDown(b_no) == null) {
            return "fileBoard/detail";
        } else {
            model.addAttribute("files", boardService.fileDown(b_no));
            return "fileBoard/detail";
        }
    }*/
    
    @RequestMapping("/detail/{no}")
    private String boardDetail(@PathVariable("no") int no, Model model) {
 
        model.addAttribute("detail", boardService.fileBoardDetail(no));

/* 2021.04.29 파일 다운 로드 로직 추가 */
        
        if (boardService.fileDown(no) == null) {
            return "fileBoard/detail";
        } else {
            model.addAttribute("files", boardService.fileDown(no));
            return "fileBoard/detail";
        }
    }

    // 여기서 postMapping인 이유는 detail 페이지에서 수정 버튼을 눌리면 해당 페이지의 정보를 가져온 뒤
    // 수정을 진행하기 때문이다. get은 내가 정보가 없어도 받을 수 있고, post 는 가지고 잇는 정보를 주고, 값을 받는 형식임을 명심하자.4
    // 그래서 detail페이지 중 "수정" 버튼에 달아놓은 링크에 따라 아래 메서드가 호출된다. 이 역시 @PathVariable(경로변수)
    // 템플릿화 된 URL 에서 일치하는 부분의 값을 가져올 수 있다.
//    원본 boardEdit메서드 
   /* @PostMapping("/edit/{b_no}")
    private String boardEdit(@PathVariable("b_no") int b_no, Model model) {
        model.addAttribute("detail", boardService.fileBoardDetail(b_no));
        return "fileBoard/update";
    }*/
    
    @PostMapping("/edit/{no}")
    private String boardEdit(@PathVariable("no") int no, Model model) {
        model.addAttribute("detail", boardService.fileBoardDetail(no));
        return "fileBoard/update";
    }
    
// 원본 boardEditDone 메서드 
    // 기존의 내용을 수정하는 거니까 post mapping이다. 게다가 model attribute까지 가져오니 빼박!
   /* @PostMapping("/update")
    private String boardEditDone(BoardDto boardDto) {*/
//        @ModelAttibute는 이름을 붙혀서 사용할 수  있는데 생략도 가능하다.
//        private String boardEditDone(@ModelAttribute BoardDto boardDto) ==> 생략 ㄴㄴ
//        생략 시 모델에 저정될 떄 클래스 명을 사용한다.. 이때 클래스의 첫 글자만 소문자로 변경해서 등록한다 .

        /*boardService.fileBoardUpdate(boardDto);

        /*int bno = boardDto.getB_no();
        String b_no = Integer.toString(bno); 원본 
        
        int bno = boardDto.getNo();
        String no = Integer.toString(bno);*/
        
        // 리다이렉트 할 때 논리적 주소를 적는게 아니라  URL을 적는 것이다.
// 원본        return "redirect:/detail/" + b_no;
       /* return "redirect:/detail/" + no;
    }*/
           
    // 수정판 
    @PostMapping("/update")
    private String boardEditDone(BoardDto boardDto) {
        boardService.fileBoardUpdate(boardDto);
        
        int bno = boardDto.getNo();
        String no = Integer.toString(bno);
        
        return "redirect:/detail/" + no;
    }

//    원본 fileDelete 메서드 
    /*@PostMapping("/delete/{b_no}")
    private String fileDelete(@PathVariable("b_no") int b_no) {
        boardService.fileBoardDelete(b_no);

        return "redirect:/";
    }*/
    
    @PostMapping("/delete/{no}")
    private String fileDelete(@PathVariable("no") int no) {
        boardService.fileBoardDelete(no);

        return "redirect:/";
    }

/*
2021.04.29 첨부파일 삽입 기능 추가 구현

*/
    @GetMapping("/write")
    private String fileWrite(@ModelAttribute BoardDto boardDto) {
        return "fileBoard/insert";
    }

    //    파일 업로드
   @PostMapping("/uploadFile")
    private String fileUpload(@ModelAttribute FileVO fileVO,
                              @RequestParam MultipartFile files)
            throws IllegalAccessException, IOException,Exception{

        File file = new File("C:/spring/temp");
        if(file.exists()) {
            file.mkdir();
        }
        String fileName = "C:/spring/temp" + UUID.randomUUID() + files.getOriginalFilename();
        File file1 = new File(fileName);
        files.transferTo(file1);

        return "/";
    }
   /* 원본 fileBoardInsertProc 메서드 
    @RequestMapping("/writeSub")
    private String fileBoardInsertProc(@ModelAttribute BoardDto boardDto, @RequestPart MultipartFile
            files, HttpServletRequest request) throws IllegalStateException, IOException, Exception {

        if(files.isEmpty()) {
            boardService.fileBoardInsert(boardDto);

        } else {
            String fileName = files.getOriginalFilename(); // 사용자 컴에 저장된 파일명 그대로
            //확장자
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
            File destinationFile; // DB에 저장할 파일 고유명
            String destinationFileName;
            //절대경로 설정 안해주면 지 맘대로 들어가버려서 절대경로 박아주었습니다.
            String fileUrl = "C:\\spring\\temp\\";

            do { //우선 실행 후
                //고유명 생성
//                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFileName = UUID.randomUUID() + "." + fileNameExtension;      //내가 수정한거
                destinationFile = new File(fileUrl + destinationFileName); //합쳐주기
            } while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs(); //디렉토리
            files.transferTo(destinationFile);

            boardService.fileBoardInsert(boardDto);



            FileVO file = new FileVO();
            file.setB_no(boardDto.getB_no());
            file.setFileName(destinationFileName);
            file.setFileOriginName(fileName);
            file.setFileUrl(fileUrl);

            boardService.fileInsert(file);
        }

        return "forward:/"; //객체 재사용
    }
*/
   /* 원본 fileBoardInsertProc 메서드
   @RequestMapping("/writeSub")
   private String fileBoardInsertProc(@ModelAttribute BoardDto boardDto, @RequestPart MultipartFile
           files, HttpServletRequest request) throws IllegalStateException, IOException, Exception {

       if(files.isEmpty()) {
           boardService.fileBoardInsert(boardDto);

       } else {
           String fileName = files.getOriginalFilename(); // 사용자 컴에 저장된 파일명 그대로
           //확장자
           String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
           File destinationFile; // DB에 저장할 파일 고유명
           String destinationFileName;
           //절대경로 설정 안해주면 지 맘대로 들어가버려서 절대경로 박아주었습니다.
           String fileUrl = "C:\\spring\\temp\\";

           do { //우선 실행 후
               //고유명 생성
               destinationFileName = UUID.randomUUID() + "." + fileNameExtension;      //내가 수정한거
               destinationFile = new File(fileUrl + destinationFileName); //합쳐주기
           } while (destinationFile.exists());

           destinationFile.getParentFile().mkdirs(); //디렉토리
           files.transferTo(destinationFile);

           boardService.fileBoardInsert(boardDto);



           FileVO file = new FileVO();
           file.setB_no(boardDto.getNo());
           file.setFileName(destinationFileName);
           file.setFileOriginName(fileName);
           file.setFileUrl(fileUrl);

           boardService.fileInsert(file);
       }

       return "forward:/"; //객체 재사용
   } */
   
   @RequestMapping("/writeSub")
   private String fileBoardInsertProc(@ModelAttribute BoardDto boardDto, @RequestPart MultipartFile
           files, HttpServletRequest request) throws IllegalStateException, IOException, Exception {

       if(files.isEmpty()) {
           boardService.fileBoardInsert(boardDto);

       } else {
           String fileName = files.getOriginalFilename(); // 사용자 컴에 저장된 파일명 그대로
           //확장자
           String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
           File destinationFile; // DB에 저장할 파일 고유명
           String destinationFileName;
           //절대경로 설정 안해주면 지 맘대로 들어가버려서 절대경로 박아주었습니다.
           String fileUrl = "C:\\spring\\temp\\";

           do { //우선 실행 후
               //고유명 생성
               destinationFileName = UUID.randomUUID() + "." + fileNameExtension;      //내가 수정한거
               destinationFile = new File(fileUrl + destinationFileName); //합쳐주기
           } while (destinationFile.exists());

           destinationFile.getParentFile().mkdirs(); //디렉토리
           files.transferTo(destinationFile);

           boardService.fileBoardInsert(boardDto);



           FileVO file = new FileVO();
           file.setNo(boardDto.getNo());
           file.setFL_NM(destinationFileName);
           file.setFL_URL(fileUrl);

           boardService.fileInsert(file);
       }

       return "forward:/"; //객체 재사용
   }
   
//   원본 fileDown 메서드
   /* @RequestMapping("/fileDown/{b_no}")
    private void fileDown(@PathVariable("b_no") int b_no, HttpServletRequest request, 
    HttpServletResponse response) throws UnsupportedEncodingException, Exception {
   
      request.setCharacterEncoding("UTF-8");
      FileVO fileVO = boardService.fileDown(b_no);
      
      //파일 업로드 경로
      try {
        String fileUrl = fileVO.getFileUrl();
        System.out.println(fileUrl);
        fileUrl += "/";
        String savePath = fileUrl;
        String fileName = fileVO.getFileName();

        //실제 내보낼 파일명
         String originFileName = fileVO.getFileOriginName();
         InputStream in = null;
         OutputStream os = null;
         File file= null;
         Boolean skip = false;
         String client = "";
         
         //파일을 읽어 스트림에 담기
        try {
          file = new File(savePath, fileName);
          in = new FileInputStream(file);
        } catch (FileNotFoundException fe) {
          skip = true;
        } 

        client = request.getHeader("User-Agent");
         
        //파일 다운로드 헤더 지정
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Description", "HTML Generated Data");

        if(!skip) {
          //IE
          if(client.indexOf("MSIE") != -1) {
            response.setHeader("Content-Disposition", "attachment; filename=\"" 
              + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
          //IE 11 이상
          } else if (client.indexOf("Trident") != -1) {
            response.setHeader("Content-Disposition", "attachment; filename=\""
              + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
          //한글 파일명 처리
          } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + 
  new String(originFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
            response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
           }
           
          response.setHeader("Content-Length", ""+file.length());
          os = response.getOutputStream();
          byte b[] = new byte[(int) file.length()];
          int leng = 0;

          while ((leng = in.read(b)) > 0) {
            os.write(b, 0, leng);
          }
        } else {
          response.setContentType("text/html; charset=UTF-8");
          PrintWriter out = response.getWriter();
          out.println("<script> alert('파일을 찾을 수 없습니다.'); history.back(); </script>");
          out.flush();
        }
         
         in.close();
         os.close();
      
      } catch (Exception e) {
        System.out.println("ERROR : " + e.getStackTrace());
      }
      
    }*/
/*  원본 fileDown 메서드
   @RequestMapping("/fileDown/{no}")
   private void fileDown(@PathVariable("no") int no, HttpServletRequest request, 
   HttpServletResponse response) throws UnsupportedEncodingException, Exception {
  
     request.setCharacterEncoding("UTF-8");
     FileVO fileVO = boardService.fileDown(no);
     
     //파일 업로드 경로
     try {
       String fileUrl = fileVO.getFileUrl();
       System.out.println(fileUrl);
       fileUrl += "/";
       String savePath = fileUrl;
       String fileName = fileVO.getFileName();

       //실제 내보낼 파일명
        String originFileName = fileVO.getFileOriginName();
        InputStream in = null;
        OutputStream os = null;
        File file= null;
        Boolean skip = false;
        String client = "";
        
        //파일을 읽어 스트림에 담기
       try {
         file = new File(savePath, fileName);
         in = new FileInputStream(file);
       } catch (FileNotFoundException fe) {
         skip = true;
       } 

       client = request.getHeader("User-Agent");
        
       //파일 다운로드 헤더 지정
       response.reset();
       response.setContentType("application/octet-stream");
       response.setHeader("Content-Description", "HTML Generated Data");

       if(!skip) {
         //IE
         if(client.indexOf("MSIE") != -1) {
           response.setHeader("Content-Disposition", "attachment; filename=\"" 
             + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
         //IE 11 이상
         } else if (client.indexOf("Trident") != -1) {
           response.setHeader("Content-Disposition", "attachment; filename=\""
             + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
         //한글 파일명 처리
         } else {
           response.setHeader("Content-Disposition", "attachment; filename=\"" + 
 new String(originFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
           response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
          }
          
         response.setHeader("Content-Length", ""+file.length());
         os = response.getOutputStream();
         byte b[] = new byte[(int) file.length()];
         int leng = 0;

         while ((leng = in.read(b)) > 0) {
           os.write(b, 0, leng);
         }
       } else {
         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script> alert('파일을 찾을 수 없습니다.'); history.back(); </script>");
         out.flush();
       }
        
        in.close();
        os.close();
     
     } catch (Exception e) {
       System.out.println("ERROR : " + e.getStackTrace());
     }
     
   } */
   
   @RequestMapping("/fileDown/{no}")
   private void fileDown(@PathVariable("no") int no, HttpServletRequest request, 
   HttpServletResponse response) throws UnsupportedEncodingException, Exception {
  
     request.setCharacterEncoding("UTF-8");
     FileVO fileVO = boardService.fileDown(no);
     
     //파일 업로드 경로
     try {
       String fileUrl = fileVO.getFL_URL();
       System.out.println(fileUrl);
       fileUrl += "/";
       String savePath = fileUrl;
       String fileName = fileVO.getFL_NM();

       //실제 내보낼 파일명
        String originFileName = fileVO.getFL_NM();
        InputStream in = null;
        OutputStream os = null;
        File file= null;
        Boolean skip = false;
        String client = "";
        
        //파일을 읽어 스트림에 담기
       try {
         file = new File(savePath, fileName);
         in = new FileInputStream(file);
       } catch (FileNotFoundException fe) {
         skip = true;
       } 

       client = request.getHeader("User-Agent");
        
       //파일 다운로드 헤더 지정
       response.reset();
       response.setContentType("application/octet-stream");
       response.setHeader("Content-Description", "HTML Generated Data");

       if(!skip) {
         //IE
         if(client.indexOf("MSIE") != -1) {
           response.setHeader("Content-Disposition", "attachment; filename=\"" 
             + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
         //IE 11 이상
         } else if (client.indexOf("Trident") != -1) {
           response.setHeader("Content-Disposition", "attachment; filename=\""
             + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
         //한글 파일명 처리
         } else {
           response.setHeader("Content-Disposition", "attachment; filename=\"" + 
 new String(originFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
           response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
          }
          
         response.setHeader("Content-Length", ""+file.length());
         os = response.getOutputStream();
         byte b[] = new byte[(int) file.length()];
         int leng = 0;

         while ((leng = in.read(b)) > 0) {
           os.write(b, 0, leng);
         }
       } else {
         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script> alert('파일을 찾을 수 없습니다.'); history.back(); </script>");
         out.flush();
       }
        
        in.close();
        os.close();
     
     } catch (Exception e) {
       System.out.println("ERROR : " + e.getStackTrace());
     }
     
   }
}