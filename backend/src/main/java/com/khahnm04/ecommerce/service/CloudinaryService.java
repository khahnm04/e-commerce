package com.khahnm04.ecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            var uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap(
                            "folder", "fashion-ecommerce",
                            "resource_type", "auto"
                    ));
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

}
