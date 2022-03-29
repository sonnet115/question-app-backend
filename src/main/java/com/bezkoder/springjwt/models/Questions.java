package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private String type;
    private String imagePath;
    private String audioPath;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ques_set_id", nullable = false)
    private QuestionSets questionSets;

    @OneToMany(mappedBy = "questions",cascade = CascadeType.ALL)
    private List<QuestionOptions> questionOptions;

    @OneToMany(mappedBy = "questions", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers;
}
