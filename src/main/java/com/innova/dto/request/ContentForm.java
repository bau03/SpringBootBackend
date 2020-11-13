package com.innova.dto.request;

import javax.persistence.Column;

public class ContentForm {
    @Column(name = "content")
    private String content;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name="contentHeader")
    private String contentHeader;

    public String getContentHeader() {
        return contentHeader;
    }

    public void setContentHeader(String contentHeader) {
        this.contentHeader = contentHeader;
    }

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
