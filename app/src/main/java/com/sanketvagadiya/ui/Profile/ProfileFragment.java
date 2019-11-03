package com.sanketvagadiya.ui.Profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanketvagadiya.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel
                .class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);

        final TextView tvProfileName = root.findViewById(R.id.tvProfileName);
        final TextView tvProfileSummary = root.findViewById(R.id.tvProfileSummary);
        final ImageView ivProfileImage = root.findViewById(R.id.ivProfileImage);

        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                tvProfileName.setText(s);
            }
        });

        return root;
    }


}
