package com.douzone.pingpong.controller.api.dto;

import lombok.Data;

@Data
public class forgetEmailResponse {
    private String email;

    public forgetEmailResponse(String email) {
        this.email= email;
    }
}
