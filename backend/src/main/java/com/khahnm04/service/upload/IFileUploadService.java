package com.khahnm04.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {

    String uploadFile(MultipartFile file);
    String uploadFileIfPresent(MultipartFile file);

}
