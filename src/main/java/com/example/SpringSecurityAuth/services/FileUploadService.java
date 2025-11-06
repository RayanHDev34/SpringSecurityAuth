package com.example.SpringSecurityAuth.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadService {

    private final Cloudinary cloudinary;

    public FileUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadToCloudinary(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "rentals",
                            "resource_type", "auto"
                    )
            );

            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Erreur d’upload Cloudinary : " + e.getMessage());
        }
    }

    public void deleteFromCloudinary(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) return;

        try {
            // Récupérer le "public_id" à partir de l’URL Cloudinary
            // ex: https://res.cloudinary.com/dkqmlwvq4/image/upload/v1730917081/rentals/abc123.jpg
            String[] parts = imageUrl.split("/");
            String publicIdWithExt = parts[parts.length - 1]; // ex: "abc123.jpg"
            String publicId = "rentals/" + publicIdWithExt.split("\\.")[0]; // "rentals/abc123"

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String replaceImage(String oldImageUrl, MultipartFile newFile) {
        deleteFromCloudinary(oldImageUrl);
        return uploadToCloudinary(newFile);
    }
}
