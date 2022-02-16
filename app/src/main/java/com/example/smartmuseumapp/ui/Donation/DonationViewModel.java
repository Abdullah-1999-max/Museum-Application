package com.example.smartmuseumapp.ui.Donation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DonationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DonationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Donation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}