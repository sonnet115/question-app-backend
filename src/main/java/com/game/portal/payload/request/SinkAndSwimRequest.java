package com.game.portal.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SinkAndSwimRequest {
    private byte[] image;
    private String answer;
    private String name;
    private String active;
}
