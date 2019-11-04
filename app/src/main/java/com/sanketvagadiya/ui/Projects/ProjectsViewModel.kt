package com.sanketvagadiya.ui.Projects

import android.content.Context
import android.os.AsyncTask
import android.util.Log

import androidx.lifecycle.ViewModel

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sanketvagadiya.Constants.Constants
import com.sanketvagadiya.Model.ProjectModel

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class ProjectsViewModel(context: Context) : ViewModel() {
    private val mQueue: RequestQueue
    private val jsonObject = JSONObject()

    //    public List<ProjectModel> getProjectList(Context mContext) {
    //        ProjectDetailsAsync projectDetailsAsync = new ProjectDetailsAsync();
    //
    //        return (List<ProjectModel>) projectDetailsAsync.execute(Constants.PROJECT_URL);
    //    }

    val projectList: List<ProjectModel>
        get() {
            val projectList = ArrayList<ProjectModel>()

            val request = JsonObjectRequest(Request.Method.GET, Constants.PROJECT_URL, null,
                    Response.Listener { response ->
                        Log.d("json", "response$response")
                        try {
                            val jsonArray = response.getJSONArray(Constants.PROJECT_JSONARRAY)

                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val projectName = jsonObject.getString(Constants.PROJECT_JSON_PROJECTNAME)
                                val imageUrl = jsonObject.getString(Constants.PROJECT_JSON_IMAGEURL)
                                val projectUrl = jsonObject.getString(Constants.PROJECT_JSON_PROJECTURL)
                                projectList.add(ProjectModel(projectName, imageUrl, projectUrl))
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }, Response.ErrorListener { error ->
                Log.d("json", ">>>>>>>>$error")
                error.printStackTrace()
            })
            mQueue.add(request)
            Log.e("project view model", "project list json" + projectList.size)
            return projectList
        }


    init {
        mQueue = Volley.newRequestQueue(context)
    }
}

