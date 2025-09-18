package com.khahnm04.shopco.service.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {

    String uploadFile(MultipartFile file, String folder);

}
