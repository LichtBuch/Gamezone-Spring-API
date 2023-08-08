package com.lichtbuch.gamezone.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryCreateRequest {

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
