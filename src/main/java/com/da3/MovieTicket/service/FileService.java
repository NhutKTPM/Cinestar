
package com.da3.MovieTicket.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${cloudinary.enabled}")
    private boolean cloudinaryEnabled;

    private final Cloudinary cloudinary;

    @Autowired
    public FileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String handleFileUpload(MultipartFile file) {
        try {
            if (cloudinaryEnabled) {
                return uploadToCloudinary(file);
            } else {
                return uploadToLocal(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    private String uploadToCloudinary(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "cinestar",
                        "resource_type", "auto"
                ));
        return (String) uploadResult.get("secure_url");
    }

    private String uploadToLocal(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String filename = UUID.randomUUID().toString() + extension;

        // Save file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/images/" + filename;
    }

    public void deleteFileIfExists(String mediaURL) {
        if (cloudinaryEnabled && mediaURL != null && mediaURL.contains("cloudinary")) {
            deleteFromCloudinary(mediaURL);
        } else if (mediaURL != null && !mediaURL.isEmpty()) {
            deleteFromLocal(mediaURL);
        }
    }

    private void deleteFromCloudinary(String mediaURL) {
        try {
            // Extract public_id from Cloudinary URL
            // URL format: https://res.cloudinary.com/{cloud_name}/image/upload/{public_id}.jpg
            String[] parts = mediaURL.split("/");
            if (parts.length > 0) {
                String publicIdWithExtension = parts[parts.length - 1];
                String publicId = "cinestar/" + publicIdWithExtension.substring(0, publicIdWithExtension.lastIndexOf('.'));
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            System.err.println("Error deleting file from Cloudinary: " + e.getMessage());
        }
    }

    private void deleteFromLocal(String mediaURL) {
        try {
            Path filePath = Paths.get(uploadDir, new File(mediaURL).getName());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }
    }
}