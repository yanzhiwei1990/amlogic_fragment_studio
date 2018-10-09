package com.example.fragment.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.example.fragment.R;

public class CustomDialog extends AlertDialog {

    private static final String TAG = "CustomDialog";
    private String mDialogType;
    private DialogCallBack mDialogCallBack;

    public static final String DIALOG_SAVING = "saving";
    public static final String DIALOG_ADD_TRANSPONDER = "add_transponder";
    public static final String DIALOG_EDIT_TRANSPONDER = "edit_transponder";
    public static final String DIALOG_ADD_SATELLITE = "add_satellite";
    public static final String DIALOG_EDIT_SATELLITE = "edit_satellite";
    public static final String DIALOG_CONFIRM = "confirm";

    public CustomDialog(Context context, String type, DialogCallBack callback) {
        super(context);
        this.mDialogType = type;
        mDialogCallBack = callback;
        customedByType(mDialogType);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    private void customedByType(String type) {

    }

    public CustomDialog getDialog(String type) {
        switch (type) {
            case DIALOG_SAVING:
                return creatSavingDialog();
            case DIALOG_ADD_TRANSPONDER:
                return creatAddTransponderDialog();
            case DIALOG_EDIT_TRANSPONDER:
                return creatEditTransponderDialog();
            case DIALOG_ADD_SATELLITE:
                return creatAddSatelliteDialog();
            case DIALOG_EDIT_SATELLITE:
                return creatEditSatelliteDialog();
            case DIALOG_CONFIRM:
                return creatConfirmDialog();
            default:
                Log.w(TAG, "getCustomDialog not exist!");
                return null;
        }
    }

    public CustomDialog creatSavingDialog() {
        setContentView(R.layout.saving);
        return this;
    }

    public CustomDialog creatAddTransponderDialog() {
        //setTitle(R.string.add);

        return this;
    }

    public CustomDialog creatEditTransponderDialog() {
        AlertDialog dialog = null;
        AlertDialog.Builder builder = null;

        return this;
    }

    public CustomDialog creatAddSatelliteDialog() {
        AlertDialog dialog = null;
        AlertDialog.Builder builder = null;

        return this;
    }

    public CustomDialog creatEditSatelliteDialog() {
        AlertDialog dialog = null;
        AlertDialog.Builder builder = null;

        return this;
    }

    public CustomDialog creatConfirmDialog() {
        CustomDialog dialog = null;
        AlertDialog.Builder builder = null;
        dialog = (CustomDialog) builder.setPositiveButton(getContext().getString(R.string.dialog_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putInt("which", which);
                mDialogCallBack.onStatusChange(mDialogType, bundle);
            }
        })
        .setNegativeButton(getContext().getString(R.string.dialog_cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putInt("which", which);
                mDialogCallBack.onStatusChange(mDialogType, bundle);
            }
        }).create();
        dialog.setContentView(R.layout.confirm);
        return dialog;
    }
}
