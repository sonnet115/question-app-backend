package com.game.portal.payload.request;

import com.game.portal.models.QuestionOptions;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionRequest {
    private String questionText;
    private String type;
    private String image;
    private String audio;
    private Long queSetID;
    private List<QuestionOptions> questionOptions;
    private ArrayList<String> answers;
}
