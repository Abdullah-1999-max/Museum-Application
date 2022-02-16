package com.example.smartmuseumapp.ui.Membership;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MembershipViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MembershipViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Membership fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}