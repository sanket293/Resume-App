package com.sanketvagadiya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sanketvagadiya.Constants.Constants;
import com.sanketvagadiya.Model.ProjectModel;
import com.sanketvagadiya.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {
    private List<ProjectModel> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            Thread.sleep(1000);
            startActivity(new Intent(this, MainActivity.class));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




}
