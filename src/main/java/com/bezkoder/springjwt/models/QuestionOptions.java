package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class QuestionOptions {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionName;
    private String optionValue;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ques_id")
    private Questions questions;

}
