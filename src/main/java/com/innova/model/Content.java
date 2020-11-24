package com.innova.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "content", schema = "public")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties(value = {"id","enabled","phoneNumber","age","activeSessions","email","password","roles","contentLike","contentDislike","commentLike","commentDislike"})
    private User user;

    @ManyToOne
    @JoinColumn(name="category_id",nullable = false)
    private Category category;

    @Column(name="content")
    private String content;

    @Column(name="content_like_number")
    private Integer content_like_number;

    @Column(name="comment_number")
    private Integer comment_number;

    @Column(name="content_dislike_number")
    private Integer content_dislike_number;

    @Column(name="contentHeader")
    private String contentHeader;

    @ManyToMany(mappedBy = "contentLike")
    @JsonIgnoreProperties(value = {"id","enabled","phoneNumber","age","activeSessions","email","lastname","surname","password","roles","contentLike","contentDislike","commentLike","commentDislike"})
    private Set<User> userLike = new HashSet<User>();

    @ManyToMany(mappedBy = "contentDislike")
    @JsonIgnoreProperties(value = {"id","enabled","phoneNumber","age","activeSessions","email","password","name","lastname","roles","contentLike","contentDislike","commentLike","commentDislike"})
    private Set<User> userDislike = new HashSet<User>();

    private Date timestap;

    public Content(){

    }

    public Content(String content,String contentHeader){
        this.content = content;
        this.contentHeader=contentHeader;
    }

    public Integer getComment_number() {
        return comment_number;
    }

    public void setComment_number(Integer comment_number) {
        this.comment_number = comment_number;
    }

    public Integer getContent_like_number() {
        return content_like_number;
    }

    public void setContent_like_number(Integer content_like_number) {
        this.content_like_number = content_like_number;
    }

    public Integer getContent_dislike_number() {
        return content_dislike_number;
    }

    public void setContent_dislike_number(Integer content_dislike_number) {
        this.content_dislike_number = content_dislike_number;
    }

    public Set<User> getUserDislike() {
        return userDislike;
    }

    public void setUserDislike(Set<User> userDislike) {
        this.userDislike = userDislike;
    }

    public Set<User> getUserLike() {
        return userLike;
    }

    public void setUserLike(Set<User> userLike) {
        this.userLike = userLike;
    }

    public String getContentHeader() {
        return contentHeader;
    }

    public void setContentHeader(String contentHeader) {
        this.contentHeader = contentHeader;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestap() {
        return timestap;
    }

    public void setTimestap(Date timestap) {
        this.timestap = timestap;
    }
}
