package com.innova.dto.request;

import javax.persistence.Column;

public class ContentForm {
    @Column(name = "content")
    private String content;

    @Column(name = "category_id")
    private String categoryId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
