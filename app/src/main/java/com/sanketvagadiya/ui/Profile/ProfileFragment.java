package com.sanketvagadiya.ui.Profile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sanketvagadiya.Model.WorkModel;
import com.sanketvagadiya.R;
import com.sanketvagadiya.ui.Work.WorkFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    private Context mContext;
    private RequestQueue mQueue;
    private JSONObject jsonObject = new JSONObject();
    private ListView lvWorkExperience;
    private TextView tvProfileName;
    private TextView tvProfileSummary;
    private ImageView ivProfileImage, ivLinkedIn, ivPlayStore, ivGithub;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel
                .class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);

        tvProfileName = root.findViewById(R.id.tvProfileName);
        tvProfileSummary = root.findViewById(R.id.tvProfileSummary);
        ivProfileImage = root.findViewById(R.id.ivProfileImage);

        ivLinkedIn = root.findViewById(R.id.ivLinkedIn);
        ivPlayStore = root.findViewById(R.id.ivPlayStore);
        ivGithub = root.findViewById(R.id.ivGithub);

        ivLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(Constants.LINKEDIN_URL);
            }
        });
        ivPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(Constants.PLAYSTORE_URL);
            }
        });
        ivGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(Constants.GITHUB_URL);
            }
        });

        return root;
    }

    private void goToUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
        profileDatafromJson();

    }


    private void profileDatafromJson() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.PROFILE_URL, null, new Response.Listener<JSONObject>() {
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
                            JSONObject object = jsonObject.getJSONObject(Constants.PROFILE_JSON_OBJECT);
                            String profileName = object.getString(Constants.PROFILE_JSON_PROFILE_NAME);
                            String imageUrl = object.getString(Constants.PROFILE_JSON_IMAGEURL);
                            String profileSummary = object.getString(Constants.PROFILE_JSON_PROFILE_SUMMARY);


                            tvProfileName.setText(profileName);
                            tvProfileSummary.setText(profileSummary);

                            Glide.with(getContext())
                                    .load(imageUrl)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(ivProfileImage);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        mQueue.add(request);
    }

}
