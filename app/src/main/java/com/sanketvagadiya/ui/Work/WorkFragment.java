package com.sanketvagadiya.ui.Work;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.sanketvagadiya.Model.WorkModel;
import com.sanketvagadiya.R;
import com.sanketvagadiya.ui.Projects.ProjectsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkFragment extends Fragment {

    private WorkViewModel workViewModel;
    private List<WorkModel> mWorkList = new ArrayList<>();
    private Context mContext;
    private RequestQueue mQueue;
    private JSONObject jsonObject = new JSONObject();
    private ListView lvWorkExperience;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.work_fragment, container, false);

        mContext = getContext();
        lvWorkExperience = root.findViewById(R.id.lvWorkExperience);

//        workViewModel = ViewModelProviders.of(this).get(WorkViewModel.class);
//        mWorkList = workViewModel.getWorkList();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
        sendWorkListJson();

    }

    private void sendWorkListJson() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.PAST_WORK_URL, null, new Response.Listener<JSONObject>() {
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
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants.PAST_WORK_JSONARRAY);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String roleName = jsonObject.getString(Constants.PAST_WORK_JSON_ROLE_NAME);
                                String companyName = jsonObject.getString(Constants.PAST_WORK_JSON_COMPANY_NAME);
                                String companyLocation = jsonObject.getString(Constants.PAST_WORK_JSON_LOCATION);
                                String joinFrom = jsonObject.getString(Constants.PAST_WORK_JSON_JOIN_FROM);
                                String joinTo = jsonObject.getString(Constants.PAST_WORK_JSON_JOIN_TO);
                                String jobResponsibilities = jsonObject.getString(Constants.PAST_WORK_JSON_RESPONSIBILITY);

                                String imageUrl = jsonObject.getString(Constants.PAST_WORK_JSON_IMAGEURL);

                                mWorkList.add(new WorkModel(roleName
                                        , companyName, companyLocation, joinFrom
                                        , joinTo, jobResponsibilities
                                        , imageUrl
                                ));
                            }

                            if (mWorkList.size() > 0) {
                                ListAdapter adapter = new ListAdapter();
                                lvWorkExperience.setAdapter(adapter);
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
            return mWorkList.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);


            if (view == null) {
                holder = new ViewHolder();

                view = inflater.inflate(R.layout.work_listview_adapter, viewGroup, false);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvWorkAdapterRoleName = view.findViewById(R.id.tvWorkAdapterRoleName);
            holder.tvWorkAdapterCompanyName = view.findViewById(R.id.tvWorkAdapterCompanyName);
            holder.tvWorkAdapterCompanyLocation = view.findViewById(R.id.tvWorkAdapterCompanyLocation);
            holder.tvWorkAdapterJoinFrom = view.findViewById(R.id.tvWorkAdapterJoinFrom);
            holder.tvWorkAdapterJoinTo = view.findViewById(R.id.tvWorkAdapterJoinTo);
            holder.tvWorkAdapterJobResponsibilities = view.findViewById(R.id.tvWorkAdapterJobResponsibilities);
            holder.ivCompanyImage = (ImageView) view.findViewById(R.id.ivCompanyImage);

            setData(holder, i);


            return view;
        }

        private void setData(ViewHolder holder, int i) {

            holder.tvWorkAdapterRoleName.setText(mWorkList.get(i).getRoleName());
            holder.tvWorkAdapterCompanyName.setText(mWorkList.get(i).getCompanyName());
            holder.tvWorkAdapterCompanyLocation.setText(mWorkList.get(i).getCompanyLocation());
            holder.tvWorkAdapterJoinFrom.setText(mWorkList.get(i).getJoinFrom());
            holder.tvWorkAdapterJoinTo.setText(mWorkList.get(i).getJoinTo());
            holder.tvWorkAdapterJobResponsibilities.setText(mWorkList.get(i).getJobResponsibilities());

            Glide.with(getContext())
                    .load(mWorkList.get(i).getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.ivCompanyImage);

        }
    }

    public class ViewHolder {

        TextView tvWorkAdapterRoleName,
                tvWorkAdapterCompanyName,
                tvWorkAdapterCompanyLocation,
                tvWorkAdapterJoinFrom,
                tvWorkAdapterJoinTo,
                tvWorkAdapterJobResponsibilities;
        ImageView ivCompanyImage;

    }


}
