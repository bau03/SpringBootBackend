package com.innova.dto.request;

import javax.persistence.Column;

public class WriterForm {
    @Column(name = "job")
    private String job;

    @Column(name="education")
    private String education;

    @Column(name="biography")
    private String biography;

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
}
