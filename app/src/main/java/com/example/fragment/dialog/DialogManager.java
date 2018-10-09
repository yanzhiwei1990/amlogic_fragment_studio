package com.example.fragment.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.example.fragment.ParameterMananer;

public class DialogManager {

    private static final String TAG = "DialogManager";
    private String mCurrentType;
    private ParameterMananer mParameterMananer;
    private Context mContext;

    public DialogManager(Context context, ParameterMananer mananer) {
        this.mContext = context;
        this.mParameterMananer = mananer;
    }

    public void setCurrentType(String type) {
        mCurrentType = type;
    }

    public String getCurrentType() {
        return mCurrentType;
    }


}
