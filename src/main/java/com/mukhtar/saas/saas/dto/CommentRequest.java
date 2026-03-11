package com.mukhtar.saas.saas.dto;

import lombok.Data;

@Data
public class CommentRequest {

    private Long taskId;
    private String comment;
}