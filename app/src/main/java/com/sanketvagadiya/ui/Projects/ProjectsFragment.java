package com.sanketvagadiya.ui.Projects;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sanketvagadiya.Constants.Constants;
import com.sanketvagadiya.Model.ProjectModel;
import com.sanketvagadiya.R;
import com.sanketvagadiya.ui.BlankViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectsFragment extends Fragment {

    private ProjectsViewModel mViewModel;
    private Context mContext;
    private List<ProjectModel> projectList = new ArrayList<>();
    private RequestQueue mQueue;
    private JSONObject jsonObject = new JSONObject();
    private ListAdapter adapter;
    private ListView lvProjectList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getActivity();

        View root = inflater.inflate(R.layout.projects_fragment, container, false);

        lvProjectList = root.findViewById(R.id.lvProjectList);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
        sendProjectListJson();

    }


    public void sendProjectListJson() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.PROJECT_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonObject = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("err", "err" + error);
            }
        });


        mQueue.addRequestFinishedListener(
                new RequestQueue.RequestFinishedListener<Object>() {
                    @Override
                    public void onRequestFinished(Request<Object> request) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants.PROJECT_JSONARRAY);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String projectName = jsonObject.getString(Constants.PROJECT_JSON_PROJECTNAME);
                                String imageUrl = jsonObject.getString(Constants.PROJECT_JSON_IMAGEURL);
                                String projectUrl = jsonObject.getString(Constants.PROJECT_JSON_PROJECTURL);
                                projectList.add(new ProjectModel(projectName, imageUrl, projectUrl));
                            }
                            if (projectList.size() > 0) {
                                adapter = new ListAdapter();
                                lvProjectList.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        mQueue.add(request);
    }


    public class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return projectList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            final ViewHolder holder;

            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.project_listview_adapter, viewGroup, false);

                holder.tvProjectName = (TextView) convertView.findViewById(R.id.tvProjectName);
                holder.ivProjectImage = (ImageView) convertView.findViewById(R.id.ivProjectImage);

                holder.tvProjectName.setText(projectList.get(i).getProjectName());

                Glide.with(getContext())
                        .load(projectList.get(i).getImageUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.ivProjectImage);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            return convertView;

        }
    }

    public class ViewHolder {
        TextView tvProjectName;
        ImageView ivProjectImage;
    }


}
