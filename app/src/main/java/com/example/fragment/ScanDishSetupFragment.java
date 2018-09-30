package com.example.fragment;

import java.util.LinkedList;

import com.example.fragment.ItemAdapter.ItemDetail;
import com.example.fragment.ItemListView.ListItemFocusedListener;
import com.example.fragment.ItemListView.ListItemSelectedListener;
import com.example.fragment.ItemListView.ListSwitchedListener;
import com.example.fragment.R.color;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ScanDishSetupFragment extends Fragment {

	private static final String TAG = "ScanDishSetupFragment";
	private ItemAdapter mItemAdapterItem = null;
	private ItemAdapter mItemAdapterOption = null;
	private ItemListView mListViewItem = null;
	private ItemListView mListViewOption = null;
	private LinkedList<ItemDetail> mItemDetailItem = new LinkedList<ItemDetail>();
	private LinkedList<ItemDetail> mItemDetailOption = new LinkedList<ItemDetail>();
	private String mCurrentListType = ParameterMananer.ITEM_SATALLITE;
	private String mCurrentListFocus = ItemListView.LIST_LEFT;
	private ParameterMananer mParameterMananer;
	
	public static ScanDishSetupFragment newInstance() {
        return new ScanDishSetupFragment();
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParameterMananer = ((ScanMainActivity)getActivity()).getParameterMananer();
		View rootView = inflater.inflate(R.layout.fragment_dish_setup, container, false);
		//View headView1 = inflater.inflate(R.layout.list_head, null, false);
		//View headView2 = inflater.inflate(R.layout.list_head, null, false);
		mListViewItem = (ItemListView) rootView.findViewById(R.id.listview_item);
		mListViewOption = (ItemListView) rootView.findViewById(R.id.listview_option);
		mItemDetailItem.addAll(mParameterMananer.getItemList(mParameterMananer.getCurrentListType()));
		mItemAdapterItem = new ItemAdapter(mItemDetailItem, getActivity());
		mListViewItem.setAdapter(mItemAdapterItem);
		//mListViewItem.setLabelFor(123);
		mListViewItem.setTag(ParameterMananer.ITEM_SATALLITE);

		//mListViewItem.setOnItemClickListener(mOnItemClickListener);
		TextView textView1 = (TextView) rootView.findViewById(R.id.listview_item_title);
		textView1.setText("Satellite");

		mListViewItem.requestFocus();

		//mListViewItem.setOnFocusChangeListener(mOnFocusChangeListener);
		//mListViewItem.setOnItemSelectedListener(mOnItemSelectedListener);
		mListViewItem.setListItemSelectedListener(mListItemSelectedListener);
		mListViewItem.setListItemFocusedListener(mListItemFocusedListener);
		mListViewItem.setListSwitchedListener(mListSwitchedListener);
		mListViewItem.setListType(ItemListView.ITEM_SATALLITE);
		
		mItemDetailOption.addAll(mParameterMananer.getCompleteParameterList(mParameterMananer.getCurrentListType(), mParameterMananer.getCurrentSatellite()));
		mItemAdapterOption = new ItemAdapter(mItemDetailOption, getActivity());
		mListViewOption.setAdapter(mItemAdapterOption);
		//mListViewOption.setLabelFor(456);
		mListViewItem.setTag(ParameterMananer.ITEM_SATALLITE_OPTION);
		//mListViewOption.setOnItemClickListener(mOnItemClickListener);
		TextView textView2 = (TextView) rootView.findViewById(R.id.listview_option_title);
		textView2.setText("Ku_NewSat2");

		mListViewOption.setSelectionAfterHeaderView();
		//mListViewOption.setOnFocusChangeListener(mOnFocusChangeListener);
		//mListViewOption.setOnItemSelectedListener(mOnItemSelectedListener);
		mListViewOption.setListItemSelectedListener(mListItemSelectedListener);
		mListViewOption.setListItemFocusedListener(mListItemFocusedListener);
		mListViewOption.setListSwitchedListener(mListSwitchedListener);
		mListViewOption.setListType(ItemListView.ITEM_SATALLITE_OPTION);
		mListViewOption.cleanChoosed();
		return rootView;
	}
	
	/*AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onItemClick view = " + view.getLabelFor() + ", position = " + position + ", id = " + id);
		}
		
	};
	
	AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onItemSelected view = " + view.getLabelFor() + ", position = " + position + ", id = " + id + ", " + mListViewItem.getSelectedItemPosition());
			//view.setBackgroundColor(color.common_focus);

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onNothingSelected onNothingSelected");
		}
		
	};
	
	OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onFocusChange view = " + v.getLabelFor() + ", hasFocus = " + hasFocus);
		}
		
	};*/
	
	ListItemSelectedListener mListItemSelectedListener = new ListItemSelectedListener() {

		@Override
		public void onListItemSelected(int position, String type) {
			Log.d(TAG, "onListItemSelected position = " + position + ", type = " + type);
			if (ItemListView.LIST_LEFT.equals(mCurrentListFocus)) {
				String listtype = mParameterMananer.getCurrentListType();
				if (ParameterMananer.ITEM_SATALLITE.equals(listtype)) {
					mParameterMananer.setCurrentSatellite(position);
				} else if (ParameterMananer.ITEM_TRANSPONDER.equals(listtype)) {
					mParameterMananer.setCurrentTransponder(position);
				}
				mItemDetailItem.clear();
				mItemAdapterItem.reFill(mParameterMananer.getItemList(listtype));
            }
		}
		
	};
	
	ListItemFocusedListener mListItemFocusedListener = new ListItemFocusedListener() {

		@Override
		public void onListItemFocused(View parent, int position, String type) {
			Log.d(TAG, "onListItemFocused position = " + position + ", type = " + type);
			if (ItemListView.LIST_LEFT.equals(mCurrentListFocus) && ItemListView.isRightList(type)) {
            	mListViewOption.cleanChoosed();
            }
			if (ItemListView.LIST_LEFT.equals(mCurrentListFocus)) {
				mItemDetailOption.clear();
				mItemAdapterOption.reFill(mParameterMananer.getCompleteParameterList(type, position));
				if (mItemAdapterOption.getCount() > 0) {
					mListViewOption.setSelection(0);
				}
				mListViewOption.cleanChoosed();
            } else if (ItemListView.LIST_RIGHT.equals(mCurrentListFocus)) {
            	mListViewItem.cleanChoosed();
            }
		}
	};
	
	ListSwitchedListener mListSwitchedListener = new ListSwitchedListener() {

		@Override
		public void onListSwitched(String direction) {
			Log.d(TAG, "onListSwitched direction = " + direction);
			if (direction != null) {
                mCurrentListFocus = direction;
			}
            if (ItemListView.LIST_LEFT.equals(mCurrentListFocus)) {
            	mListViewOption.cleanChoosed();
            	mListViewItem.requestFocus();
            } else if (ItemListView.LIST_RIGHT.equals(mCurrentListFocus)) {
            	mListViewItem.cleanChoosed();
            	mListViewOption.requestFocus();
            }
		}
		
	};
	
	/*public boolean dispatchKeyEvent (KeyEvent event) {
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
                	if (mListViewItem.requestFocus()) {
                		
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                	if (mListViewOption.requestFocus())
                        return true;
                    break;
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
                        setItemTextColor(selectedView, false);
                    }
                    break;
            }
        }

        return super.dispatchKeyEvent(event);
    }*/
}

