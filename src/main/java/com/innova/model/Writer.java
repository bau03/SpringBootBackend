package com.innova.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="writer_user", schema = "public")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "job")
    private String job;

    @Column(name="education")
    private String education;

    @Column(name="biography")
    private String biography;

    public Date getTimestap() {
        return timestap;
    }

    public void setTimestap(Date timestap) {
        this.timestap = timestap;
    }

    private Date timestap;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Writer(){

    }
    public Writer(String job, String education, String biography){
        this.job = job;
        this.education = education;
        this.biography = biography;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
