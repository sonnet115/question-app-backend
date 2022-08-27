package com.game.portal.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SinkAndSwimRequest {
    private MultipartFile image;
    private String answer;
}
