package com.sanketvagadiya.Model;

import com.android.volley.toolbox.StringRequest;

public class ProjectModel {
    String projectName;
    String imageUrl;
    String projectUrl;

    public ProjectModel(String projectName, String imageUrl, String projectUrl) {
        this.projectName = projectName;
        this.imageUrl = imageUrl;
        this.projectUrl = projectUrl;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public ProjectModel() {

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
