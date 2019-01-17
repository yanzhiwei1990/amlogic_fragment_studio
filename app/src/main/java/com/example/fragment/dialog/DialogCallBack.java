package com.example.fragment.dialog;

import android.os.Bundle;
import android.view.View;

public interface DialogCallBack {
    void onStatusChange(View view, String dialogtype, Bundle data);
}
