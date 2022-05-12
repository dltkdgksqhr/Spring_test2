package gu.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    /**
     * 파일 업로드.
     */
    public List<FileVO> saveAllFiles(List<MultipartFile> upfiles) {
        String filePath = "c:\\workspace\\fileupload\\"; // 파일 경로 설정
        List<FileVO> filelist = new ArrayList<FileVO>();  // 파일VO타입의 ArrayList를 생성하고 filelist에 담는다. 

        for (MultipartFile uploadfile : upfiles ) {
            if (uploadfile.getSize() == 0) {
                continue;
            }
            
            String newName = getNewName(); //NewName메서드에서 가져와서 newName변수에 담는다.
            
            saveFile(uploadfile, filePath + "/" + newName.substring(0,4) + "/", newName); //파일 저장 메서드에 변수를 보낸다.
            
            FileVO filedo = new FileVO(); // FileVO타입의 filedo 객체를 만든다.
            filedo.setFilename(uploadfile.getOriginalFilename()); //uploadfile메서드에 Original파일 이름을 가져와서 filedo에 저장한다.
            filedo.setRealname(newName);  // newName을 Realname에 저장한다.
            filedo.setFilesize(uploadfile.getSize()); // 파일 사이즈를 가져와서 filesize에 저장한다.
            filelist.add(filedo); // 각각 담은 객체를 filelist에 저장한다.
        }
        return filelist;
    }    
    
    /**
     * 파일 저장 경로 생성.
     */
    public void makeBasePath(String path) {
        File dir = new File(path); // 파일 타입의 dir객체를 생성함
        if (!dir.exists()) { //dir존재하지 않으면 dir을 만들어준다.
            dir.mkdirs();
            
        }
    }

    /**
     * 실제 파일 저장.
     */
    public String saveFile(MultipartFile file, String basePath, String fileName){
        if (file == null || file.getName().equals("") || file.getSize() < 1) { // file이 비어있거나 사이즈가 1보다 작으면 null을 반환한다.
            return null;
        }
     
        makeBasePath(basePath);
        String serverFullPath = basePath + fileName;
  
        File file1 = new File(serverFullPath);
        try {
            file.transferTo(file1);
        } catch (IllegalStateException ex) {
            System.out.println("IllegalStateException: " + ex.toString());
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.toString());
        }
        
        return serverFullPath;
    }
    
    /**
     * 날짜로 새로운 파일명 부여.
     */
    public String getNewName() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return ft.format(new Date()) + (int) (Math.random() * 10);
    }
    
    public String getFileExtension(String filename) {
          Integer mid = filename.lastIndexOf(".");
          return filename.substring(mid, filename.length());
    }

    public String getRealPath(String path, String filename) {
        return path + filename.substring(0,4) + "/";
    }
}
