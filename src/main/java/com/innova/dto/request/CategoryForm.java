package com.innova.dto.request;

import javax.persistence.Column;

public class CategoryForm {
    @Column(name = "categoryName")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
