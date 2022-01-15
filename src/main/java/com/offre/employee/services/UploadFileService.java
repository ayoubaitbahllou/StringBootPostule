package com.offre.employee.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("uploadFileService")
public class UploadFileService {
    public  String SAVE_LOCATION ;
    public  String resPath = "";

    public UploadFileService() {};
    public UploadFileService(String location) {
        this.SAVE_LOCATION=location;
    }

    public boolean saveFile(MultipartFile multipartFile){
        boolean result = false;
        String fileName  = multipartFile.getOriginalFilename();
        File pathFile = new File(SAVE_LOCATION);
        if(!pathFile.exists()){
            pathFile.mkdir();
        }
        pathFile = new File(SAVE_LOCATION +"/"+ fileName);
        try {
            multipartFile.transferTo(pathFile);
            this.resPath = this.SAVE_LOCATION +"\\"+ fileName;
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}


