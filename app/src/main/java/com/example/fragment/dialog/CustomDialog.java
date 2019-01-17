package com.example.fragment.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fragment.ParameterMananer;
import com.example.fragment.R;

import java.util.LinkedList;

public class CustomDialog extends AlertDialog {

    private static final String TAG = "CustomDialog";
    private String mDialogType;
    private String mDialogTitleText;
    private String mDialogKeyText;
    private DialogCallBack mDialogCallBack;
    private Context mContext;
    private ParameterMananer mParameterMananer;
    private AlertDialog mAlertDialog = null;
    private View mDialogView = null;
    private TextView mDialogTitle = null;
    private DialogItemListView mListView = null;
    private ProgressBar mStrengthProgressBar = null;
    private ProgressBar mQualityProgressBar = null;
    private TextView mStrengthTextView = null;
    private TextView mQualityTextView = null;
    private LinkedList<DialogItemAdapter.DialogItemDetail> mItemList = new LinkedList<DialogItemAdapter.DialogItemDetail>();
    private DialogItemAdapter mItemAdapter = null;

    public static final String DIALOG_SAVING = "saving";
    public static final String DIALOG_ADD_TRANSPONDER = "add_transponder";
    public static final String DIALOG_EDIT_TRANSPONDER = "edit_transponder";
    public static final String DIALOG_ADD_SATELLITE = "add_satellite";
    public static final String DIALOG_EDIT_SATELLITE = "edit_satellite";
    public static final String DIALOG_CONFIRM = "confirm";

    public static final String DIALOG_SET_SELECT_SINGLE_ITEM = "select_single_item";
    public static final String DIALOG_SET_EDIT_SWITCH_ITEM = "edit_switch_item";
    public static final String DIALOG_SET_EDIT_ITEM = "edit_item";
    public static final String DIALOG_SET_PROGRESS_ITEM = "progress_item";

    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_SATALLITE = "Satallite";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_TRANOPONDER = "Transponder";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE = "LNB Type";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_UNICABLE = "Unicable";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_LNB_POWER = "LNB Power";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_22KHZ = "22KHz";
    public static final String DIALOG_SET_SELECT_SINGLE_TONE_BURST = "ToneBurst";
    public static final String DIALOG_SET_SELECT_SINGLE_DISEQC1_0 = "DiSEqC1.0";
    public static final String DIALOG_SET_SELECT_SINGLE_DISEQC1_1 = "DiSEqC1.1";
    public static final String DIALOG_SET_SELECT_SINGLE_MOTOR = "Motor";
    public static final String[] ID_DIALOG_TITLE_COLLECTOR = {DIALOG_SET_SELECT_SINGLE_ITEM_SATALLITE, DIALOG_SET_SELECT_SINGLE_ITEM_TRANOPONDER, DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE,
            DIALOG_SET_SELECT_SINGLE_ITEM_UNICABLE, DIALOG_SET_SELECT_SINGLE_ITEM_LNB_POWER, DIALOG_SET_SELECT_SINGLE_ITEM_22KHZ, DIALOG_SET_SELECT_SINGLE_TONE_BURST, DIALOG_SET_SELECT_SINGLE_DISEQC1_0,
            DIALOG_SET_SELECT_SINGLE_DISEQC1_1, DIALOG_SET_SELECT_SINGLE_MOTOR};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE_LIST = {"5150", "9750/10600", "Customize"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_LNB_CUSTOM_TYPE_LIST = {"first freq", "sencond freg"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_UNICABLE_LIST = {"off", "on"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_LNB_POWER_LIST = {"13V", "18V", "off", "13/18V"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_22KHZ_LIST = {"on", "off", "auto"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_TONE_BURST_LIST = {"None", "ToneburstA", "ToneburstB"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_0_LIST = {"None", "1/4", "2/4", "3/4", "4/4"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_1_LIST = {"None", "1/16", "2/16", "3/16", "4/16", "5/16", "6/16", "7/16"};
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_MOTOR_LIST = {"None", "DiSEqc1.2"};

    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DISH_LIMITS = "Dish Limits Status";
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DISH_LIMITS_LIST = {"off", "on"};
    //public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_SET_DISH_LIMITS = "Set Dish Limits East";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_SET_EAST_DISH_LIMITS = "Set Dish Limits East";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_SET_WEST_DISH_LIMITS = "Set Dish Limits West";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DIRECTTION = "Move Directtion";
    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DIRECTTION_LIST = {"East", "West", "Center"};
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_STEP = "Move Step";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_MOVE = "Move";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_POSITION = "Current Position";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_SAVE_TO_POSITION = "Save To Position";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_MOVE_TO_POSITION = "Move To Position";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_STRENGTH = "Strength";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_QUALITY = "Quality";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_SAVE = "Save";
    public static final String DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_SCAN= "Scan";

    public static final String[] DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST = {DIALOG_SET_SELECT_SINGLE_ITEM_SATALLITE, DIALOG_SET_SELECT_SINGLE_ITEM_TRANOPONDER, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DISH_LIMITS,
            DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_SET_EAST_DISH_LIMITS, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_SET_WEST_DISH_LIMITS, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DIRECTTION,
            DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_STEP, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_MOVE, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_POSITION,
            DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_SAVE_TO_POSITION, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_MOVE_TO_POSITION, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_STRENGTH,
            DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_QUALITY, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_SAVE, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_SCAN
    };

    public static final String DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE = "Unicable";
    public static final String DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_SWITCH = "Unicable Switch";
    public static final String DIALOG_SET_EDIT_SWITCH_ITEM_USER_BAND = "User Band";
    public static final String DIALOG_SET_EDIT_SWITCH_ITEM_UB_FREQUENCY = "UB Frequency";
    public static final String DIALOG_SET_EDIT_SWITCH_ITEM_POSITION = "Position";
    public static final String[] DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_LIST = {DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_SWITCH, DIALOG_SET_EDIT_SWITCH_ITEM_USER_BAND, DIALOG_SET_EDIT_SWITCH_ITEM_UB_FREQUENCY, DIALOG_SET_EDIT_SWITCH_ITEM_POSITION};
    public static final String[] DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_SWITCH_LIST = {"off", "on"};
    public static final String[] DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_USER_BAND_LIST = {"0", "1", "2", "3", "4", "5", "6", "7"};
    public static final String[] DIALOG_SET_EDIT_ITEM_UNICABLE_UB_FREQUENCY_LIST = {"22KHz"};
    public static final String[] DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_POSITION_LIST = {"off", "on"};

    public CustomDialog(Context context, String type, DialogCallBack callback, ParameterMananer mananer) {
        super(context);
        this.mContext = context;
        this.mDialogType = type;
        this.mDialogCallBack = callback;
        this.mParameterMananer = mananer;
    }

    /*public void initDialogWithButton(Context context, final String key, String positive, String negative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        mAlertDialog = builder.create();
        /*mAlertDialog = builder.setPositiveButton(positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putInt("which", which);
                bundle.putString("key", key);
                if (mDialogCallBack != null) {
                    mDialogCallBack.onStatusChange(null, mDialogType, bundle);
                }
            }
        })
                .setNegativeButton(negative, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("which", which);
                        bundle.putString("key", key);
                        if (mDialogCallBack != null) {
                            mDialogCallBack.onStatusChange(null, mDialogType, bundle);
                        }
                    }
                }).create();*/
       /* mDialogView = View.inflate(mContext, R.layout.select_single_item_dialog, null);
        mDialogTitle = (TextView) mDialogView.findViewById(R.id.dialog_title);
        mListView = (DialogItemListView) mDialogView.findViewById(R.id.select_single_item_listview);
    }*/

    public interface DialogUiCallBack {
        void onStatusChange(View view, String dialogtype, Bundle data);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    public String getDialogType() {
        return mDialogType;
    }

    public String getDialogKey() {
        return mDialogKeyText;
    }

    public String getDialogTitle() {
        return mDialogTitleText;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> buildLnbItem(int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE_LIST.length; i++) {
            boolean needSelect = select == i ? true : false;
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_SELECT, DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE_LIST[i], "", needSelect);
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> buildUnicableItem() {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_LIST.length; i++) {
            if (i == 0) {
                item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT_SWITCH, DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_LIST[i], DIALOG_SET_SELECT_SINGLE_ITEM_UNICABLE_LIST[mParameterMananer.getIntParameters(ParameterMananer.DIALOG_SET_ITEM_UNICABLE_KEY_LIST[i])], false);
            } else {
                //need to type in frequency
                item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_DISPLAY, DIALOG_SET_EDIT_SWITCH_ITEM_UNICABLE_LIST[i], "", false);
            }
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> buildLnbPowerItem(int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_LNB_POWER_LIST.length; i++) {
            boolean needSelect = select == i ? true : false;
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_SELECT, DIALOG_SET_SELECT_SINGLE_ITEM_LNB_POWER_LIST[i], "", needSelect);
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> build22KhzItem(int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_22KHZ_LIST.length; i++) {
            boolean needSelect = select == i ? true : false;
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_SELECT, DIALOG_SET_SELECT_SINGLE_ITEM_22KHZ_LIST[i], "", needSelect);
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> buildToneBurstItem(int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_TONE_BURST_LIST.length; i++) {
            boolean needSelect = select == i ? true : false;
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_SELECT, DIALOG_SET_SELECT_SINGLE_ITEM_TONE_BURST_LIST[i], "", needSelect);
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> buildDiseqc1_0_Item(int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_0_LIST.length; i++) {
            boolean needSelect = select == i ? true : false;
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_SELECT, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_0_LIST[i], "", needSelect);
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> buildDiseqc1_1_Item(int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_1_LIST.length; i++) {
            boolean needSelect = select == i ? true : false;
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_SELECT, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_1_LIST[i], "", needSelect);
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> buildMotorItem(int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_MOTOR_LIST.length; i++) {
            boolean needSelect = select == i ? true : false;
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_SELECT, DIALOG_SET_SELECT_SINGLE_ITEM_MOTOR_LIST[i], "", needSelect);
            items.add(item);
        }
        return items;
    }

    private LinkedList<DialogItemAdapter.DialogItemDetail> getSelectSingleItemsByKey(String title, String key, int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> items = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        switch (key) {
            case ParameterMananer.KEY_LNB_TYPE:
                items.addAll(buildLnbItem(select));
                break;
            case ParameterMananer.KEY_UNICABLE:
                items.addAll(buildUnicableItem());
                break;
            case ParameterMananer.KEY_LNB_POWER:
                items.addAll(buildLnbPowerItem(select));
                break;
            case ParameterMananer.KEY_22_KHZ:
                items.addAll(build22KhzItem(select));
                break;
            case ParameterMananer.KEY_TONE_BURST:
                items.addAll(buildToneBurstItem(select));
                break;
            case ParameterMananer.KEY_DISEQC1_0:
                items.addAll(buildDiseqc1_0_Item(select));
                break;
            case ParameterMananer.KEY_DISEQC1_1:
                items.addAll(buildDiseqc1_1_Item(select));
                break;
            case ParameterMananer.KEY_MOTOR:
                items.addAll(buildMotorItem(select));
                break;
            default:
                break;
        }
        return items;
    }

    public void initListView(String title, String key, int select) {
        initListDialog(mContext);
        LinkedList<DialogItemAdapter.DialogItemDetail> itemlist = getSelectSingleItemsByKey(title, key, select);
        mItemAdapter = new DialogItemAdapter(itemlist, mContext);
        mListView.setAdapter(mItemAdapter);
        if (!ParameterMananer.KEY_UNICABLE.equals(key) && select >= 0 && select < itemlist.size()) {
            mListView.setSelection(select);
        } else {
            mListView.setSelection(0);
        }
        mDialogTitle.setText(title);
        mDialogTitleText = title;
        mListView.setKey(key);
        mDialogKeyText = key;
        mListView.setDialogCallBack(mDialogCallBack);
        mAlertDialog.setView(mDialogView);
    }

    public void updateListView(String title, String key, int select) {
        LinkedList<DialogItemAdapter.DialogItemDetail> itemlist = getSelectSingleItemsByKey(title, key, select);
        if (select >= 0 && select < itemlist.size()) {
            mListView.setSelection(select);
        } else {
            mListView.setSelection(0);
        }
        mItemAdapter.reFill(itemlist);
    }

    private void initListDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        mAlertDialog = builder.create();
        mDialogView = View.inflate(mContext, R.layout.select_single_item_dialog, null);
        mDialogTitle = (TextView) mDialogView.findViewById(R.id.dialog_title);
        mListView = (DialogItemListView) mDialogView.findViewById(R.id.select_single_item_listview);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    public void showDialog() {
        if (mAlertDialog != null && !mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    public void dismissDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    /*public AlertDialog creatSelectSingleItemDialog(final String title, String key, int select) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog dialog = builder.create();
        View dialogView = View.inflate(mContext, R.layout.select_single_item_dialog, null);
        TextView dialogTtile = (TextView) dialogView.findViewById(R.id.dialog_title);
        dialogTtile.setText(title);
        DialogItemListView listView = (DialogItemListView) dialogView.findViewById(R.id.select_single_item_listview);
        LinkedList<DialogItemAdapter.DialogItemDetail> itemlist = getSelectSingleItemsByKey(title, key, select);
        DialogItemAdapter itemAdapter = new DialogItemAdapter(itemlist, mContext);
        listView.setAdapter(itemAdapter);
        if (select >= 0 && select < itemlist.size()) {
            listView.setSelection(select);
        } else {
            listView.setSelection(0);
        }
        listView.setTitle(title);
        listView.setKey(key);
        listView.setDialogCallBack(mDialogCallBack);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mDialogCallBack != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putInt("position", position);
                    mDialogCallBack.onStatusChange(title, bundle);
                }
            }
        });*/
        /*dialog.setView(dialogView);
        return dialog;
    }*/

    public void initLnbCustomedItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mAlertDialog = builder.create();
        mDialogView = View.inflate(mContext, R.layout.set_custom_lnb, null);
        mDialogTitle = (TextView) mDialogView.findViewById(R.id.dialog_title);

        mDialogTitle.setText(DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE_LIST[2]);
        final EditText editText1 = (EditText)mDialogView.findViewById(R.id.edittext_frequency1);
        final EditText editText2 = (EditText)mDialogView.findViewById(R.id.edittext_frequency2);
        final Button ok = (Button) mDialogView.findViewById(R.id.button1);
        final Button cancel = (Button) mDialogView.findViewById(R.id.button2);

        ok.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogCallBack != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "onClick");
                    bundle.putString("key", ParameterMananer.KEY_LNB_CUSTOM);
                    bundle.putString("button", "ok");
                    bundle.putString("value1", editText1.getText() != null ? editText1.getText().toString() : "");
                    bundle.putString("value2", editText2.getText() != null ? editText2.getText().toString() : "");
                    mDialogCallBack.onStatusChange(ok, ParameterMananer.KEY_LNB_CUSTOM, bundle);
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss();
                    }
                }
            }
        });
        cancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogCallBack != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "onClick");
                    bundle.putString("key", ParameterMananer.KEY_LNB_CUSTOM);
                    bundle.putString("button", "cancel");
                    bundle.putString("value1", editText1.getText() != null ? editText1.getText().toString() : "");
                    bundle.putString("value2", editText2.getText() != null ? editText2.getText().toString() : "");
                    mDialogCallBack.onStatusChange(ok, ParameterMananer.KEY_LNB_CUSTOM, bundle);
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss();
                    }
                }
            }
        });
        mAlertDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDialogCallBack != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "onDismiss");
                    bundle.putString("key", ParameterMananer.KEY_LNB_CUSTOM);
                    bundle.putString("button", "cancel");
                    bundle.putString("value1", editText1.getText() != null ? editText1.getText().toString() : "");
                    bundle.putString("value2", editText2.getText() != null ? editText2.getText().toString() : "");
                    mDialogCallBack.onStatusChange(ok, ParameterMananer.KEY_LNB_CUSTOM, bundle);
                }
            }
        });

        String value = mParameterMananer.getStringParameters(ParameterMananer.KEY_LNB_CUSTOM);
        String[] resultValue = {"", ""};
        if (value != null) {
            String[] allvalue = value.split(",");
            if (allvalue != null && allvalue.length > 0 && allvalue.length <= 2) {
                for (int i = 0; i < allvalue.length; i++) {
                    resultValue[i] = allvalue[i];
                }
            }
        }

        editText1.setHint(resultValue[0]);
        editText2.setHint(resultValue[1]);
        mAlertDialog.setView(mDialogView);
        mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /*LinkedList<DialogItemAdapter.DialogItemDetail> itemlist = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        String value = mParameterMananer.getStringParameters(ParameterMananer.KEY_LNB_CUSTOM);
        String[] resultValue = new String[DIALOG_SET_SELECT_SINGLE_ITEM_LNB_CUSTOM_TYPE_LIST.length];
        LinearLayout layout0 = new LinearLayout(mContext);
        LinearLayout.LayoutParams paras = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout0.setOrientation(LinearLayout.VERTICAL);
        layout0.setLayoutParams(paras);

        LinearLayout layout1 = new LinearLayout(mContext);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.setLayoutParams(paras);
        TextView itemname = new TextView(mContext);
        itemname.setText("itemname1");
        itemname.setLayoutParams(paras);


        layout0.addView(layout1, paras);
        if (value != null) {
            String[] allvalue = value.split(",");
            if (allvalue != null && allvalue.length > 0 && allvalue.length <= DIALOG_SET_SELECT_SINGLE_ITEM_LNB_CUSTOM_TYPE_LIST.length) {
                for (int i = 0; i < allvalue.length; i++) {
                    resultValue[i] = allvalue[i];
                }
            }
        }
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_LNB_CUSTOM_TYPE_LIST.length; i++) {
            item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT, DIALOG_SET_SELECT_SINGLE_ITEM_LNB_CUSTOM_TYPE_LIST[i], resultValue[i], false);
            itemlist.add(item);
        }
        mItemAdapter = new DialogItemAdapter(itemlist, mContext);
        mListView.setAdapter(mItemAdapter);
        mDialogTitle.setText(DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE_LIST[2]);
        mDialogTitleText = DIALOG_SET_SELECT_SINGLE_ITEM_LNB_TYPE_LIST[2];
        mListView.setKey(ParameterMananer.KEY_LNB_CUSTOM);
        mDialogKeyText = ParameterMananer.KEY_LNB_CUSTOM;
        mListView.setDialogCallBack(mDialogCallBack);
        mAlertDialog.setView(mDialogView);*/
    }

    public void initDiseqc1_2_ItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mAlertDialog = builder.create();
        mDialogView = View.inflate(mContext, R.layout.set_diseqc_1_2, null);
        mDialogTitle = (TextView) mDialogView.findViewById(R.id.dialog_title);
        mListView = (DialogItemListView) mDialogView.findViewById(R.id.switch_edit_item_list);
        mStrengthProgressBar = (ProgressBar)mDialogView.findViewById(R.id.strength_progressbar);
        mQualityProgressBar = (ProgressBar)mDialogView.findViewById(R.id.quality_progressbar);
        mStrengthTextView = (TextView)mDialogView.findViewById(R.id.strength_percent);
        mQualityTextView = (TextView)mDialogView.findViewById(R.id.quality_percent);
        mStrengthProgressBar.setProgress(mParameterMananer.getStrengthStatus());
        mQualityProgressBar.setProgress(mParameterMananer.getQualityStatus());
        mStrengthTextView.setText(mParameterMananer.getStrengthStatus() + "%");
        mQualityTextView.setText(mParameterMananer.getQualityStatus() + "%");

        LinkedList<DialogItemAdapter.DialogItemDetail> itemlist = new LinkedList<DialogItemAdapter.DialogItemDetail>();
        DialogItemAdapter.DialogItemDetail item = null;
        for (int i = 0; i < DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST.length; i++) {
            item = null;
            switch (i) {
                case 0:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_DISPLAY, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], mParameterMananer.getStringParameters(mParameterMananer.KEY_SATALLITE), false);
                    break;
                case 1:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_DISPLAY, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], mParameterMananer.getStringParameters(mParameterMananer.KEY_TRANSPONDER), false);
                    break;
                case 2:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT_SWITCH, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DISH_LIMITS_LIST[mParameterMananer.getIntParameters(mParameterMananer.KEY_DISEQC1_2_DISH_LIMITS_STATUS)], false);
                    break;
                case 3:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT_SWITCH, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], String.valueOf(mParameterMananer.getIntParameters(mParameterMananer.KEY_DISEQC1_2_DISH_EAST_LIMITS)), false);
                    break;
                case 4:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT_SWITCH, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], String.valueOf(mParameterMananer.getIntParameters(mParameterMananer.KEY_DISEQC1_2_DISH_WEST_LIMITS)), false);
                    break;
                case 5:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT_SWITCH, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST_DIRECTTION_LIST[mParameterMananer.getIntParameters(mParameterMananer.KEY_DISEQC1_2_DISH_MOVE_DIRECTION)], false);
                    break;
                case 6:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT_SWITCH, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], String.valueOf(mParameterMananer.getIntParameters(mParameterMananer.KEY_DISEQC1_2_DISH_MOVE_STEP)), false);
                    break;
                case 7:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_DISPLAY, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], "Press To Move", false);
                    break;
                case 8:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_EDIT_SWITCH, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], String.valueOf(mParameterMananer.getIntParameters(mParameterMananer.KEY_DISEQC1_2_DISH_CURRENT_POSITION)), false);
                    break;
                case 9:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_DISPLAY, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], "Press To Save", false);
                    break;
                case 10:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_DISPLAY, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], "Press To Move", false);
                    break;
                /*case 11:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_PROGRESS, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], mParameterMananer.getStrengthStatus() + "%", false);
                    break;
                case 12:
                    item = new DialogItemAdapter.DialogItemDetail(DialogItemAdapter.DialogItemDetail.ITEM_PROGRESS, DIALOG_SET_SELECT_SINGLE_ITEM_DISEQC1_2_LIST[i], mParameterMananer.getQualityStatus() + "%", false);
                    break;*/
                default:
                    Log.d(TAG, "initDiseqc1_2_ItemDialog unkown key");
                    break;
            }
            if (item != null) {
                itemlist.add(item);
            }
        }

        mItemAdapter = new DialogItemAdapter(itemlist, mContext);
        mListView.setAdapter(mItemAdapter);

        mDialogTitle.setText(CustomDialog.DIALOG_SET_SELECT_SINGLE_ITEM_MOTOR_LIST[1]);
        mDialogTitleText = CustomDialog.DIALOG_SET_SELECT_SINGLE_ITEM_MOTOR_LIST[1];
        mListView.setKey(ParameterMananer.KEY_DISEQC1_2);
        mDialogKeyText = ParameterMananer.KEY_DISEQC1_2;
        mListView.setDialogCallBack(mDialogCallBack);

        final TextView save = (TextView) mDialogView.findViewById(R.id.status_save);
        final TextView scan = (TextView) mDialogView.findViewById(R.id.status_scan);

        save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogCallBack != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "onClick");
                    bundle.putString("key", ParameterMananer.KEY_LNB_CUSTOM);
                    bundle.putString("button", "save");
                    mDialogCallBack.onStatusChange(save, ParameterMananer.KEY_DISEQC1_2, bundle);
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss();
                    }
                }
            }
        });
        scan.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogCallBack != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "onClick");
                    bundle.putString("key", ParameterMananer.KEY_LNB_CUSTOM);
                    bundle.putString("button", "scan");
                    mDialogCallBack.onStatusChange(scan, ParameterMananer.KEY_DISEQC1_2, bundle);
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss();
                    }
                }
            }
        });
        mAlertDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDialogCallBack != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "onDismiss");
                    bundle.putString("key", ParameterMananer.KEY_DISEQC1_2);
                    mDialogCallBack.onStatusChange(null, ParameterMananer.KEY_DISEQC1_2, bundle);
                }
            }
        });

        mAlertDialog.setView(mDialogView);
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
                mDialogCallBack.onStatusChange(null, mDialogType, bundle);
            }
        })
        .setNegativeButton(getContext().getString(R.string.dialog_cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putInt("which", which);
                mDialogCallBack.onStatusChange(null, mDialogType, bundle);
            }
        }).create();
        dialog.setContentView(R.layout.confirm);
        return dialog;
    }
}
