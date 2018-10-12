package com.example.fragment;

import java.util.LinkedList;

import com.example.fragment.ItemAdapter.ItemDetail;
import com.example.fragment.ItemListView.ListItemFocusedListener;
import com.example.fragment.ItemListView.ListItemSelectedListener;
import com.example.fragment.ItemListView.ListSwitchedListener;
import com.example.fragment.ItemListView.ListTypeSwitchedListener;
import com.example.fragment.R.color;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
	private LinearLayout mSatelliteQuickkey;
	private LinearLayout mSatelliteQuickkey1;
	private LinearLayout mSatelliteQuickkey2;
    private TextView mItemTitleTextView;
    private TextView mOptionTitleItemTextView;
	
	/*public static ScanDishSetupFragment newInstance() {
        return new ScanDishSetupFragment();
    }*/
    public ScanDishSetupFragment() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.d(TAG, "onCreateView");
		mParameterMananer = ((ScanMainActivity)getActivity()).getParameterMananer();
		View rootView = inflater.inflate(R.layout.fragment_dish_setup, container, false);
		mSatelliteQuickkey1 = (LinearLayout) rootView.findViewById(R.id.function_key1);
		mSatelliteQuickkey2 = (LinearLayout) rootView.findViewById(R.id.function_key2);
		creatFour1();
		creatFour2();

		mListViewItem = (ItemListView) rootView.findViewById(R.id.listview_item);
		mListViewOption = (ItemListView) rootView.findViewById(R.id.listview_option);
		mItemDetailItem.addAll(mParameterMananer.getItemList(mParameterMananer.getCurrentListType()));
		mItemAdapterItem = new ItemAdapter(mItemDetailItem, getActivity());
		mListViewItem.setAdapter(mItemAdapterItem);
		mListViewItem.setTag(mParameterMananer.getCurrentListType()/*ParameterMananer.ITEM_SATALLITE*/);

		mItemTitleTextView = (TextView) rootView.findViewById(R.id.listview_item_title);
        mItemTitleTextView.setText(ParameterMananer.ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? R.string.list_type_satellite : R.string.list_type_transponder);
		mListViewItem.requestFocus();

		mListViewItem.setListItemSelectedListener(mListItemSelectedListener);
		mListViewItem.setListItemFocusedListener(mListItemFocusedListener);
		mListViewItem.setListSwitchedListener(mListSwitchedListener);
		mListViewItem.setListTypeSwitchedListener(mListTypeSwitchedListener);
		mListViewItem.setListType(mParameterMananer.getCurrentListType()/*ItemListView.ITEM_SATALLITE*/);
		
		mItemDetailOption.addAll(mParameterMananer.getCompleteParameterList(mParameterMananer.getCurrentListType(), mParameterMananer.getCurrentSatellite()));
		mItemAdapterOption = new ItemAdapter(mItemDetailOption, getActivity());
		mListViewOption.setAdapter(mItemAdapterOption);
		mListViewItem.setTag(ParameterMananer.ITEM_SATALLITE_OPTION);
		mListViewItem.setSelection(ParameterMananer.ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? mParameterMananer.getCurrentSatellite() : mParameterMananer.getCurrentTransponder());

		mOptionTitleItemTextView = (TextView) rootView.findViewById(R.id.listview_option_title);
        mOptionTitleItemTextView.setText(mParameterMananer.getParameterListTitle(mParameterMananer.getCurrentListType(), ParameterMananer.ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? mParameterMananer.getCurrentSatellite() : mParameterMananer.getCurrentTransponder())/*"Ku_NewSat2"*/);

		//mListViewOption.setSelectionAfterHeaderView();
		mListViewOption.setListItemSelectedListener(mListItemSelectedListener);
		mListViewOption.setListItemFocusedListener(mListItemFocusedListener);
		mListViewOption.setListSwitchedListener(mListSwitchedListener);
		mListViewOption.setListType(ItemListView.ITEM_SATALLITE_OPTION);
		mListViewOption.cleanChoosed();
		return rootView;
	}

	private void changeSatelliteQuickkeyLayout() {
		mSatelliteQuickkey.removeAllViews();
		mSatelliteQuickkey.addView(mSatelliteQuickkey1);
		mSatelliteQuickkey.addView(mSatelliteQuickkey2);
	}

	private void creatFour1() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) inflater.inflate(R.layout.four_display1, null);
		mSatelliteQuickkey1.removeAllViews();
		mSatelliteQuickkey1.addView(view);
	}

	private void creatFour2() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) inflater.inflate(R.layout.four_display2, null);
		mSatelliteQuickkey2.removeAllViews();
		mSatelliteQuickkey2.addView(view);
	}

	private void creatConfirmandExit1() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) inflater.inflate(R.layout.confirm_exit_display, null);
		mSatelliteQuickkey1.removeAllViews();
		mSatelliteQuickkey1.addView(view);
	}

	private void creatSatelliteandScan2() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) inflater.inflate(R.layout.satellite_scan_display, null);
		mSatelliteQuickkey2.removeAllViews();
		mSatelliteQuickkey2.addView(view);
	}

	private void creatSetlimitandSetlocation1() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) inflater.inflate(R.layout.limit_location_display, null);
		mSatelliteQuickkey1.removeAllViews();
		mSatelliteQuickkey1.addView(view);
	}

	private void creatEditandExit2() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) inflater.inflate(R.layout.edit_exit_wheel_display, null);
		mSatelliteQuickkey2.removeAllViews();
		mSatelliteQuickkey2.addView(view);
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

            	creatFour1();
            	creatFour2();
            } else if (ItemListView.LIST_RIGHT.equals(mCurrentListFocus)) {
            	//mListViewItem.cleanChoosed();
            	mListViewOption.requestFocus();
				creatConfirmandExit1();
				creatSatelliteandScan2();
            }
			if (ParameterMananer.ITEM_SATALLITE.equals(mCurrentListType)) {
				mListViewItem.setSelection(mParameterMananer.getCurrentSatellite());
			} else {
				mListViewItem.setSelection(mParameterMananer.getCurrentTransponder());
			}
		}
		
	};

	ListTypeSwitchedListener mListTypeSwitchedListener = new ListTypeSwitchedListener() {
		@Override
		public void onListTypeSwitched(String listtype) {
			mCurrentListType = listtype;
			mParameterMananer.setCurrentListType(mCurrentListType);
			mListViewItem.cleanChoosed();
			mItemAdapterItem.reFill(mParameterMananer.getItemList(mParameterMananer.getCurrentListType()));
			if (ParameterMananer.ITEM_SATALLITE.equals(mCurrentListType)) {
				mListViewItem.setSelection(mParameterMananer.getCurrentSatellite());
			} else {
				mListViewItem.setSelection(mParameterMananer.getCurrentTransponder());
			}
			mListViewItem.requestFocus();
            mItemTitleTextView.setText(ParameterMananer.ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? R.string.list_type_satellite : R.string.list_type_transponder);
			mItemAdapterOption.reFill(mParameterMananer.getCompleteParameterList(mParameterMananer.getCurrentListType(), ParameterMananer.ITEM_SATALLITE.equals(mCurrentListType) ? mParameterMananer.getCurrentSatellite() : mParameterMananer.getCurrentTransponder()));
			mListViewOption.cleanChoosed();
            mOptionTitleItemTextView.setText(mParameterMananer.getParameterListTitle(mParameterMananer.getCurrentListType(), ParameterMananer.ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? mParameterMananer.getCurrentSatellite() : mParameterMananer.getCurrentTransponder())/*"Ku_NewSat2"*/);
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

