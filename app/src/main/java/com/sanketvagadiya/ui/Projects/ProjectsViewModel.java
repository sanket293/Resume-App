package com.sanketvagadiya.ui.Projects;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sanketvagadiya.Constants.Constants;
import com.sanketvagadiya.Model.ProjectModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectsViewModel extends ViewModel {
    private RequestQueue mQueue;
    private JSONObject jsonObject = new JSONObject();

    public ProjectsViewModel(Context context) {
        mQueue = Volley.newRequestQueue(context);
         }




//    public List<ProjectModel> getProjectList(Context mContext) {
//        ProjectDetailsAsync projectDetailsAsync = new ProjectDetailsAsync();
//
//        return (List<ProjectModel>) projectDetailsAsync.execute(Constants.PROJECT_URL);
//    }

    public List<ProjectModel> getProjectList() {
        final List<ProjectModel> projectList = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.PROJECT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json", "response" + response);
                        try {
                            JSONArray jsonArray = response.getJSONArray(Constants.PROJECT_JSONARRAY);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String projectName = jsonObject.getString(Constants.PROJECT_JSON_PROJECTNAME);
                                String imageUrl = jsonObject.getString(Constants.PROJECT_JSON_IMAGEURL);
                                String projectUrl = jsonObject.getString(Constants.PROJECT_JSON_PROJECTURL);
                                projectList.add(new ProjectModel(projectName, imageUrl, projectUrl));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("json", ">>>>>>>>" + error);
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        Log.e("project view model", "project list json" + projectList.size());
        return projectList;
    }


    public JSONObject getProjectListJson() {

        final JSONObject[] jsonObject = {null};

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.PROJECT_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                jsonObject[0] = response;

                try {
                    wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);

        return jsonObject[0];
    }
}

