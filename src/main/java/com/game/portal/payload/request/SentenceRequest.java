package com.game.portal.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SentenceRequest {
    private String sentence;
}
