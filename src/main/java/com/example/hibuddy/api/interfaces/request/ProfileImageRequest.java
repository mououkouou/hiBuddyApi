package com.example.hibuddy.api.interfaces.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageRequest {
    private MultipartFile image;
}
