package com.example.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.util.AttributeSet;
import android.util.Log;

public class ItemListView extends ListView implements OnItemSelectedListener {
    private static final String TAG = "GuideListView";

    public static final String ITEM_SATALLITE              = ParameterMananer.ITEM_SATALLITE;//"item_satallite";
    public static final String ITEM_TRANSPONDER            = ParameterMananer.ITEM_TRANSPONDER;//"item_tansponder";
    public static final String ITEM_SATALLITE_OPTION       = ParameterMananer.ITEM_SATALLITE_OPTION;//"item_satallite_option";
    public static final String ITEM_TRANSPONDER_OPTION     = ParameterMananer.ITEM_TRANSPONDER_OPTION;//"item_tansponder_option";

    private Context mContext;
    private int selectedPosition = 0;
    private String mListType = ITEM_SATALLITE;
    private ViewGroup rootView = null;
    private ListItemSelectedListener mListItemSelectedListener;
    private ListItemFocusedListener mListItemFocusedListener;
    private ListSwitchedListener mListSwitchedListener;
    
    public static final String LIST_LEFT = "left";
    public static final String LIST_RIGHT = "right";

    public ItemListView(Context context) {
        super(context);
    }
    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        setRootView();
        setOnItemSelectedListener(this);
    }

    public boolean dispatchKeyEvent (KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (selectedPosition == 0)
                        return true;
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (selectedPosition == getAdapter().getCount() - 1)
                        return true;
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (isLeftList(mListType)) {
                        return true;
                    } else if (isRightList(mListType)) {
                    	if (mListSwitchedListener != null) {
                    		mListSwitchedListener.onListSwitched(LIST_LEFT);
                    	}
                    	return super.dispatchKeyEvent(event);
                    }
                    return super.dispatchKeyEvent(event);
                    //break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                	if (isLeftList(mListType)) {
                		if (mListSwitchedListener != null) {
                    		mListSwitchedListener.onListSwitched(LIST_RIGHT);
                    	}
                        return super.dispatchKeyEvent(event);
                    } else if (isRightList(mListType)) {
                    	return true;
                    }
                	return super.dispatchKeyEvent(event);
                    //break;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_NUMPAD_ENTER:
                	if (isLeftList(mListType)) {
                		if (mListItemSelectedListener != null) {
                			mListItemSelectedListener.onListItemSelected(getSelectedItemPosition(), mListType);
                		}
                    } else if (isRightList(mListType)) {
                    	return true;
                    }
                	return super.dispatchKeyEvent(event);
                    //break;
            }

            View selectedView = getSelectedView();
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if ( selectedView != null) {
                        clearChoosed(selectedView);
                    }
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if ( selectedView != null) {
                        //setItemTextColor(selectedView, false);
                    }
                    break;
            }
        }

        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    	Log.d(TAG, "onItemSelected mListType = " + mListType +", position = " + position + ", id = " + id);
        selectedPosition = position;
        if (view != null) {
            /*if (hasFocus()) {
                setItemTextColor(view, true);
            } else {
                setItemTextColor(view, false);
            }*/
            setChoosed(view);
        }
        if (view != null && mListItemFocusedListener != null) {
            mListItemFocusedListener.onListItemFocused((View)parent, position, mListType);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    protected void onFocusChanged (boolean gainFocus, int direction, Rect previouslyFocusedRect) {
    	Log.d(TAG, "onFocusChanged mListType = " + mListType + ", gainFocus = " + gainFocus +", direction = " + direction);
        View item = getSelectedView();
        if (item != null) {
            if (gainFocus) {
                cleanChoosed();
                //setItemTextColor(item, true);
                setChoosed(item);
            } else {
                //setItemTextColor(item, false);
            	clearChoosed(item);
            }
        }
    }

    private void setItemTextColor (View view, boolean focused) {
        if (focused) {
            int color_text_focused = mContext.getResources().getColor(R.color.common_focus);
            for (int i = 0; i < ((ViewGroup)view).getChildCount(); i++) {
                View child = ((ViewGroup)view).getChildAt(i);
                if (child instanceof TextView) {
                    ((TextView)child).setTextColor(color_text_focused);
                }
            }
        } else {
            int color_text_item = mContext.getResources().getColor(R.color.common_item_background);
            for (int i = 0; i < ((ViewGroup)view).getChildCount(); i++) {
                View child = ((ViewGroup)view).getChildAt(i);
                if (child instanceof TextView) {
                    ((TextView)child).setTextColor(color_text_item);
                }
            }
        }
    }

    private void setRootView() {
        rootView = ((ViewGroup)((Activity)mContext).findViewById(android.R.id.content));
    }

    private boolean hasNextFocusView(int dec) {
        if (FocusFinder.getInstance().findNextFocus(rootView, this, dec) == null) {
            return false;
        } else {
            return true;
        }
    }

    public void cleanChoosed() {
    	int color_text_item = mContext.getResources().getColor(R.color.common_item_background);
        for (int i = 0; i < getChildCount(); i ++) {
            View view = getChildAt(i);
            view.setBackgroundColor(color_text_item);
        }
    }

    public void clearChoosed(View view) {
    	int color_text_item = mContext.getResources().getColor(R.color.common_item_background);
    	view.setBackgroundColor(color_text_item);
    }

    public void setChoosed(View view) {
    	int color_text_focused = mContext.getResources().getColor(R.color.common_focus);
    	view.setBackgroundColor(color_text_focused);
    }

    public void setListType(String type) {
    	mListType = type;
    }
    
    public void setListItemSelectedListener(ListItemSelectedListener l) {
        mListItemSelectedListener = l;
    }
    
    public void setListItemFocusedListener(ListItemFocusedListener l) {
        mListItemFocusedListener = l;
    }

    public void setListSwitchedListener(ListSwitchedListener l) {
        mListSwitchedListener = l;
    }
    
    public interface ListItemSelectedListener {
        void onListItemSelected(int position, String type);
    }
    
    public interface ListItemFocusedListener {
        void onListItemFocused(View parent, int position, String type);
    }
    
    public interface ListSwitchedListener {
        void onListSwitched(String direction);
    }
    
    static public boolean isLeftList(String type) {
    	if (ITEM_SATALLITE.equals(type) || ITEM_TRANSPONDER.equals(type)) {
    		return true;
    	}
    	return false;
    }
    
    static public boolean isRightList(String type) {
    	if (ITEM_SATALLITE_OPTION.equals(type) || ITEM_TRANSPONDER_OPTION.equals(type)) {
    		return true;
    	}
    	return false;
    }
}


