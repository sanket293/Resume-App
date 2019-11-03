package com.sanketvagadiya.ui.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfileViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("this is profile");

    }

    public LiveData<String> getText(){return mText;}
}
