package com.example.isss_usr.ui.laporan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LaporanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LaporanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Blank");
    }

    public LiveData<String> getText() {
        return mText;
    }
}