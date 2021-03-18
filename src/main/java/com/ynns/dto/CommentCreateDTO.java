package com.ynns.dto;

import lombok.Data;

/**
 * 接收内容
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
