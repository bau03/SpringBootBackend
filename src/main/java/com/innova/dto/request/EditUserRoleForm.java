package com.innova.dto.request;

import javax.persistence.Column;

public class EditUserRoleForm {
    @Column(name = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
