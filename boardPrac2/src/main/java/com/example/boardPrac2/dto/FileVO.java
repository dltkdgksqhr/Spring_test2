package com.example.boardPrac2.dto;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {

    private int fno; //파일 번호 
    private int b_no; //글 번호 
    private String fileName;
    private String fileOriginName;
    private String fileUrl;
    private Date uploadDate; //업로드 날짜

}
