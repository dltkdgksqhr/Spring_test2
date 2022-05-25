package com.example.boardPrac2.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
   /* 원본
    private int fno; //파일 번호 
    private int b_no; //글 번호 
    private String fileName;
    private String fileOriginName;
    private String fileUrl;
    private Date uploadDate; //업로드 날짜
    */

    private int no; //글 번호 
    private String FL_NM;
    private String FL_URL;
    private String FL_ORI_NM; // 받아올 때 파일 이름
}
