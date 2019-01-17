package com.example.fragment.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;

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

    public CustomDialog buildItemDialogById(int id, DialogCallBack callBack) {
        CustomDialog customDialog = new CustomDialog(mContext, CustomDialog.DIALOG_SET_SELECT_SINGLE_ITEM, callBack, mParameterMananer);
        if (id > CustomDialog.ID_DIALOG_TITLE_COLLECTOR.length - 1 || id > ParameterMananer.ID_DIALOG_KEY_COLLECTOR.length - 1) {
            return null;
        }
        customDialog.initListView(CustomDialog.ID_DIALOG_TITLE_COLLECTOR[id], ParameterMananer.ID_DIALOG_KEY_COLLECTOR[id], mParameterMananer.getIntParameters(ParameterMananer.ID_DIALOG_KEY_COLLECTOR[id]));
        return customDialog;
    }

    public CustomDialog buildLnbCustomedItemDialog(DialogCallBack callBack) {
        CustomDialog customDialog = new CustomDialog(mContext, CustomDialog.DIALOG_SET_EDIT_ITEM, callBack, mParameterMananer);
        customDialog.initLnbCustomedItemDialog();
        return customDialog;
    }

    public CustomDialog buildDiseqc1_2_ItemDialog(DialogCallBack callBack) {
        CustomDialog customDialog = new CustomDialog(mContext, CustomDialog.DIALOG_SET_EDIT_SWITCH_ITEM, callBack, mParameterMananer);
        customDialog.initDiseqc1_2_ItemDialog();
        return customDialog;
    }

    /*public AlertDialog buildItemDialogByKey(String key, DialogCallBack callBack) {
        CustomDialog customDialog = new CustomDialog(mContext, CustomDialog.DIALOG_SET_SELECT_SINGLE_ITEM, callBack, mParameterMananer);
        AlertDialog selectSingleItemDialog = customDialog.creatSelectSingleItemDialog(null, key, mParameterMananer.getIntParameters(key));
        return selectSingleItemDialog;
    }*/

    /*public AlertDialog buildSelectSingleItemDialog(String title, DialogCallBack callBack) {
        CustomDialog customDialog = new CustomDialog(mContext, CustomDialog.DIALOG_SET_SELECT_SINGLE_ITEM, callBack, mParameterMananer);
        AlertDialog selectSingleItemDialog = customDialog.creatSelectSingleItemDialog(title, null, mParameterMananer.getSelectSingleItemValueIndex(title));
        return selectSingleItemDialog;
    }*/
}
