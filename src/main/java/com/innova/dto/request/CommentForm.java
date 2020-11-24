package com.innova.dto.request;

import javax.persistence.Column;

public class CommentForm {

    @Column(name = "comment")
    private String comment;

    @Column(name = "content_id")
    private String content_id;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }
}
