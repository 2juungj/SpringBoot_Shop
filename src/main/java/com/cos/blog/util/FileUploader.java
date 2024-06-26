package com.cos.blog.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;

@RestController
public class FileUploader {

    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("itemImage") MultipartFile itemImage) {
        // 이미지를 저장할 경로 설정
        String uploadPath = "C:/dev/workspace/springbootwork/blog/src/main/resources/static/image/upload/";
        
        try {
            // 업로드된 파일의 원래 이름을 가져와서 사용
            String fileName = itemImage.getOriginalFilename();
            // 저장할 경로와 파일명을 합쳐서 File 객체 생성
            File dest = new File(uploadPath + fileName);
            
         // 만약 동일한 이름의 파일이 이미 존재하는 경우 덮어쓰기
            if(dest.exists()){
                dest.delete();
            }
            
            // MultipartFile을 File로 변환하여 저장
            itemImage.transferTo(dest);
            // 이미지가 저장된 경로를 클라이언트에게 반환 (예를 들어, DB에 경로를 저장하거나 클라이언트에게 보여줄 때 사용)
            return uploadPath + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload image.";
        }
    }
}
