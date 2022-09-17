package com.game.portal.specifications;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria {
    private String key;
    private String type;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value, String type) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.type = type;
    }

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}