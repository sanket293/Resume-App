package com.sanketvagadiya.Model;

public class WorkModel {

    String roleName, companyName, CompanyLocation, JoinFrom, JoinTo, JobResponsibilities;
    String imageUrl;

    public WorkModel(String roleName, String companyName, String companyLocation, String joinFrom, String joinTo, String jobResponsibilities, String imageUrl) {
        this.roleName = roleName;
        this.companyName = companyName;
        CompanyLocation = companyLocation;
        JoinFrom = joinFrom;
        JoinTo = joinTo;
        JobResponsibilities = jobResponsibilities;
        this.imageUrl = imageUrl;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return CompanyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        CompanyLocation = companyLocation;
    }

    public String getJoinFrom() {
        return JoinFrom;
    }

    public void setJoinFrom(String joinFrom) {
        JoinFrom = joinFrom;
    }

    public String getJoinTo() {
        return JoinTo;
    }

    public void setJoinTo(String joinTo) {
        JoinTo = joinTo;
    }

    public String getJobResponsibilities() {
        return JobResponsibilities;
    }

    public void setJobResponsibilities(String jobResponsibilities) {
        JobResponsibilities = jobResponsibilities;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
