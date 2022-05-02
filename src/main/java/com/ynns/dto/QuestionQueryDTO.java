package com.ynns.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private Integer size;
    private Integer currentPage;
    private String search;
}
