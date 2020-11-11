package com.innova.dto.request;

import javax.persistence.Column;

public class EditUserRoleForm {
    @Column(name = "id")
    private String id;

    @Column(name = "writerid")
    private String writerid;

    public String getWriterid() {
        return writerid;
    }

    public void setWriterid(String writerid) {
        this.writerid = writerid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
