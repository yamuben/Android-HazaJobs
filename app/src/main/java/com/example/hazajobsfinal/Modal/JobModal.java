package com.example.hazajobsfinal.Modal;

public class JobModal {
    private String title;
    private String description;
    private String jobId;
    private String country;
    private String city;
    private String jobRecommendation;
    private String jobSalary;
    private String jobOwner;


    public JobModal(){}

    public JobModal(String title, String salary, String desc, String id, String recommendation, String country, String city, String jobOwner){
        this.title = title;
        this.jobSalary = salary;
        this.description  = desc;
        this.jobId = id;
        this.country = country;
        this.city = city;
        this.jobRecommendation = recommendation;
        this.jobOwner = jobOwner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJobRecommendation() {
        return jobRecommendation;
    }

    public void setJobRecommendation(String jobRecommendation) {
        this.jobRecommendation = jobRecommendation;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobOwner() {
        return jobOwner;
    }

    public void setJobOwner(String jobOwner) {
        this.jobOwner = jobOwner;
    }
}
