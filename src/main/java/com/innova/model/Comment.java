package com.innova.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comment", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties(value = {"id","enabled","username","phoneNumber","age","activeSessions","email","password","roles","contentLike","contentDislike","commentLike","commentDislike"})
    private User user;

    @ManyToOne
    @JoinColumn(name="content_id", nullable = false)
    @JsonBackReference
    private Content content;

    @Column(name="comment")
    private String comment;

    @Column(name="comment_like_number")
    private Integer comment_like_number;

    @Column(name="comment_dislike_number")
    private Integer comment_dislike_number;

    @ManyToMany(mappedBy = "commentLike")
    @JsonIgnoreProperties(value = {"id","enabled","phoneNumber","age","activeSessions","email","lastname","surname","password","roles","contentLike","contentDislike","commentLike","commentDislike"})
    private Set<User> userLike = new HashSet<User>();

    @ManyToMany(mappedBy = "commentDislike")
    @JsonIgnoreProperties(value = {"id","enabled","phoneNumber","age","activeSessions","email","password","name","lastname","roles","contentLike","contentDislike","commentLike","commentDislike"})
    private Set<User> userDislike = new HashSet<User>();
    private Date timestap;

    public Comment(){

    }

    public Comment(String comment){
        this.comment = comment;
    }

    public Set<User> getUserLike() {
        return userLike;
    }

    public void setUserLike(Set<User> userLike) {
        this.userLike = userLike;
    }

    public Set<User> getUserDislike() {
        return userDislike;
    }

    public void setUserDislike(Set<User> userDislike) {
        this.userDislike = userDislike;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getComment_like_number() {
        return comment_like_number;
    }

    public void setComment_like_number(Integer comment_like_number) {
        this.comment_like_number = comment_like_number;
    }

    public Integer getComment_dislike_number() {
        return comment_dislike_number;
    }

    public void setComment_dislike_number(Integer comment_dislike_number) {
        this.comment_dislike_number = comment_dislike_number;
    }

    public Date getTimestap() {
        return timestap;
    }

    public void setTimestap(Date timestap) {
        this.timestap = timestap;
    }
}
