package com.droidlogic.fragment;

import java.util.LinkedList;

import com.droidlogic.fragment.ItemAdapter.ItemDetail;
import com.droidlogic.fragment.ItemListView.ListItemFocusedListener;
import com.droidlogic.fragment.ItemListView.ListItemSelectedListener;
import com.droidlogic.fragment.ItemListView.ListSwitchedListener;
import com.droidlogic.fragment.ItemListView.ListTypeSwitchedListener;
import com.droidlogic.fragment.R.color;
import com.droidlogic.fragment.dialog.CustomDialog;
import com.droidlogic.fragment.dialog.DialogCallBack;
import com.droidlogic.fragment.dialog.DialogManager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import static com.droidlogic.fragment.ParameterMananer.ITEM_SATALLITE;
import static com.droidlogic.fragment.ParameterMananer.ITEM_TRANSPONDER;

public class ScanDishSetupFragment extends Fragment {

	private static final String TAG = "ScanDishSetupFragment";
	private ItemAdapter mItemAdapterItem = null;
	private ItemAdapter mItemAdapterOption = null;
	private ItemListView mListViewItem = null;
	private ItemListView mListViewOption = null;
	private LinkedList<ItemDetail> mItemDetailItem = new LinkedList<ItemDetail>();
	private LinkedList<ItemDetail> mItemDetailOption = new LinkedList<ItemDetail>();
	private String mCurrentListType = ParameterMananer.KEY_SATALLITE;
	private String mCurrentListFocus = ItemListView.LIST_LEFT;
	private ParameterMananer mParameterMananer;
	private LinearLayout mSatelliteQuickkey;
	private LinearLayout mSatelliteQuickkey1;
	private LinearLayout mSatelliteQuickkey2;
    private TextView mItemTitleTextView;
    private TextView mOptionTitleItemTextView;
    private DialogManager mDialogManager = null;
	
	/*public static ScanDishSetupFragment newInstance() {
        return new ScanDishSetupFragment();
    }*/
    public ScanDishSetupFragment() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.d(TAG, "onCreateView");
		mParameterMananer = ((ScanMainActivity)getActivity()).getParameterMananer();
		mDialogManager = ((ScanMainActivity)getActivity()).getDialogManager();
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
		mListViewItem.setTag(/*mParameterMananer.getCurrentListType()*/ITEM_SATALLITE);

		mItemTitleTextView = (TextView) rootView.findViewById(R.id.listview_item_title);
        mItemTitleTextView.setText(ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? R.string.list_type_satellite : R.string.list_type_transponder);
		mListViewItem.requestFocus();

		mListViewItem.setListItemSelectedListener(mListItemSelectedListener);
		mListViewItem.setListItemFocusedListener(mListItemFocusedListener);
		mListViewItem.setListSwitchedListener(mListSwitchedListener);
		mListViewItem.setListTypeSwitchedListener(mListTypeSwitchedListener);
		mListViewItem.setListType(/*mParameterMananer.getCurrentListType()*/ItemListView.ITEM_SATALLITE);
		mListViewItem.setDataCallBack(mSingleSelectDialogCallBack);
		
		mItemDetailOption.addAll(mParameterMananer.getCompleteParameterList(mParameterMananer.getCurrentListType(), mParameterMananer.getCurrentSatelliteIndex()));
		mItemAdapterOption = new ItemAdapter(mItemDetailOption, getActivity());
		mListViewOption.setAdapter(mItemAdapterOption);
		mListViewItem.setTag(ParameterMananer.ITEM_SATALLITE_OPTION);
		mListViewItem.setSelection(ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? mParameterMananer.getCurrentSatelliteIndex() : mParameterMananer.getCurrentTransponderIndex());

		mOptionTitleItemTextView = (TextView) rootView.findViewById(R.id.listview_option_title);
        mOptionTitleItemTextView.setText(mParameterMananer.getParameterListTitle(mParameterMananer.getCurrentListType(), ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? mParameterMananer.getCurrentSatelliteIndex() : mParameterMananer.getCurrentTransponderIndex())/*"Ku_NewSat2"*/);

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

	private CustomDialog mCurrentCustomDialog = null;
	private CustomDialog mCurrentSubCustomDialog = null;
	private DialogCallBack mSingleSelectDialogCallBack = new DialogCallBack() {
		@Override
		public void onStatusChange(View view, String parameterKey, Bundle data) {
			Log.d(TAG, "onStatusChange parameterKey = " + parameterKey + ", data = " + data);
			switch (parameterKey) {
				case ParameterMananer.KEY_LNB_TYPE:
					if (data != null && "selected".equals(data.getString("action"))) {
						mParameterMananer.saveIntParameters(parameterKey, data.getInt("position"));
						if (mCurrentCustomDialog != null && TextUtils.equals(parameterKey, mCurrentCustomDialog.getDialogKey())) {
							mCurrentCustomDialog.updateListView(mCurrentCustomDialog.getDialogTitle(), mCurrentCustomDialog.getDialogKey(), data.getInt("position"));
							mItemAdapterOption.reFill(mParameterMananer.getCompleteParameterList(mParameterMananer.getCurrentListType(), mParameterMananer.getCurrentSatelliteIndex()));
						}
						if (data.getInt("position") == 2) {
							mCurrentSubCustomDialog = mDialogManager.buildLnbCustomedItemDialog(mSingleSelectDialogCallBack);
							if (mCurrentSubCustomDialog != null) {
								mCurrentSubCustomDialog.showDialog();
							}
						}
					}
					break;
				case ParameterMananer.KEY_LNB_CUSTOM:
					if (data != null && "onClick".equals(data.getString("action"))) {
						if ("ok".equals(data.getString("button"))) {
							Log.d(TAG, "ok in clicked");
							mParameterMananer.saveStringParameters(parameterKey, data.getString("value1") + "," + data.getString("value2"));
						} else if ("cancel".equals(data.getString("button"))) {
							Log.d(TAG, "cancel in clicked");
						}
					}
					break;
				case ParameterMananer.KEY_UNICABLE:
					if (data != null && 0 == data.getInt("position") && ("left".equals(data.getString("action")) || "right".equals(data.getString("action")))) {
						if ("left".equals(data.getString("action"))) {
							Log.d(TAG, "unicable switch left in clicked");
							mParameterMananer.saveIntParameters(ParameterMananer.KEY_UNICABLE_SWITCH, 0);
						} else if ("right".equals(data.getString("action"))) {
							Log.d(TAG, "unicable switch right in clicked");
							mParameterMananer.saveIntParameters(ParameterMananer.KEY_UNICABLE_SWITCH, 1);
						}
						if (mCurrentCustomDialog != null) {
							mCurrentCustomDialog.updateListView(mCurrentCustomDialog.getDialogTitle(), mCurrentCustomDialog.getDialogKey(), 0);
						}
					} else if ("selected".equals(data.getString("action")) && data != null && data.getInt("position") > 0 && data.getInt("position") < 4) {
						int unicableSwitchPosition = data.getInt("position");
						switch (unicableSwitchPosition) {
							case 1:
								mCurrentSubCustomDialog = mDialogManager.buildLnbCustomedItemDialog(mSingleSelectDialogCallBack);
								if (mCurrentSubCustomDialog != null) {
									mCurrentSubCustomDialog.showDialog();
								}
								break;
							case 2:
								mCurrentSubCustomDialog = mDialogManager.buildLnbCustomedItemDialog(mSingleSelectDialogCallBack);
								if (mCurrentSubCustomDialog != null) {
									mCurrentSubCustomDialog.showDialog();
								}
								break;
							case 3:
								mCurrentSubCustomDialog = mDialogManager.buildLnbCustomedItemDialog(mSingleSelectDialogCallBack);
								if (mCurrentSubCustomDialog != null) {
									mCurrentSubCustomDialog.showDialog();
								}
								break;
							default:
								break;
						}
					}

					break;
				case ParameterMananer.KEY_LNB_POWER:
				case ParameterMananer.KEY_22_KHZ:
				case ParameterMananer.KEY_TONE_BURST:
				case ParameterMananer.KEY_DISEQC1_0:
				case ParameterMananer.KEY_DISEQC1_1:
				case ParameterMananer.KEY_MOTOR:
					if (data != null && "selected".equals(data.getString("action"))) {
						mParameterMananer.saveIntParameters(parameterKey, data.getInt("position"));
						if (data.getInt("position") == 0 && mCurrentCustomDialog != null && TextUtils.equals(parameterKey, mCurrentCustomDialog.getDialogKey())) {
							mCurrentCustomDialog.updateListView(mCurrentCustomDialog.getDialogTitle(), mCurrentCustomDialog.getDialogKey(), data.getInt("position"));
							//mItemAdapterOption.reFill(mParameterMananer.getCompleteParameterList(mParameterMananer.getCurrentListType(), mParameterMananer.getCurrentSatellite()));
						} else if (data.getInt("position") == 1 && mCurrentCustomDialog != null && TextUtils.equals(parameterKey, mCurrentCustomDialog.getDialogKey())){
							mCurrentCustomDialog.updateListView(mCurrentCustomDialog.getDialogTitle(), mCurrentCustomDialog.getDialogKey(), data.getInt("position"));
							mCurrentSubCustomDialog = mDialogManager.buildDiseqc1_2_ItemDialog(mSingleSelectDialogCallBack);
							if (mCurrentSubCustomDialog != null) {
								mCurrentSubCustomDialog.showDialog();
							}
						}
					}
					break;
				case ParameterMananer.KEY_DISEQC1_2:
					if (data != null) {
						mParameterMananer.saveIntParameters(parameterKey, data.getInt("position"));
					}
					if (data != null && "selected".equals(data.getString("action"))) {
						int diseqc1_2_position = data.getInt("position");
						mParameterMananer.saveIntParameters(parameterKey, diseqc1_2_position);
						boolean needbreak = false;
						switch (diseqc1_2_position) {
							case 7://move
								mParameterMananer.dishMove(mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_MOVE_DIRECTION), mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_MOVE_STEP));
								break;
							case 9://save to position
								mParameterMananer.storeDishPosition(mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_CURRENT_POSITION));
								break;
							case 10://move to position
								mParameterMananer.moveDishToPosition(mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_CURRENT_POSITION));
								break;
							default:
								needbreak = true;
								break;
						}
						if (!needbreak && mCurrentSubCustomDialog != null && mCurrentSubCustomDialog.isShowing()) {
							mCurrentSubCustomDialog.updateDiseqc1_2_Dialog();
						} else {
							Log.d(TAG, "mCurrentSubCustomDialog null or need break or not displayed");
						}
					} else if (data != null && ("left".equals(data.getString("action")) || "right".equals(data.getString("action")))) {
						int position = data.getInt("position");
						boolean needbreak = false;
						switch (position) {
							case 2://dish limts status
								mParameterMananer.saveIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_LIMITS_STATUS, "left".equals(data.getString("action")) ? 0 : 1);
								break;
							case 3://dish limts east
								int value = mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_EAST_LIMITS);
								if ("left".equals(data.getString("action"))) {
									if (value != 0) {
										value = value - 1;
									}
								} else {
									if (value != 180) {
										value = value + 1;
									}
								}
								mParameterMananer.saveIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_EAST_LIMITS, value);
								break;
							case 4://dish limts west
								int westvalue = mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_WEST_LIMITS);
								if ("left".equals(data.getString("action"))) {
									if (westvalue != 0) {
										westvalue = westvalue - 1;
									}
								} else {
									if (westvalue != 180) {
										westvalue = westvalue + 1;
									}
								}
								mParameterMananer.saveIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_WEST_LIMITS, westvalue);
								break;
							case 5://dish moce direction
								int directionvalue = mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_MOVE_DIRECTION);
								if ("left".equals(data.getString("action"))) {
									if (directionvalue != 0) {
										directionvalue = directionvalue - 1;
									}
								} else {
									if (directionvalue != 2) {
										directionvalue = directionvalue + 1;
									}
								}
								mParameterMananer.saveIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_MOVE_DIRECTION, directionvalue);
								break;
							case 6://dish moce step
								int stepvalue = mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_MOVE_STEP);
								if ("left".equals(data.getString("action"))) {
									if (stepvalue != 0) {
										stepvalue = stepvalue - 1;
									}
								} else {
									if (stepvalue != 180) {
										stepvalue = stepvalue + 1;
									}
								}
								mParameterMananer.saveIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_MOVE_STEP, stepvalue);
								break;
							case 8://dish position
								int positionvalue = mParameterMananer.getIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_CURRENT_POSITION);
								if ("left".equals(data.getString("action"))) {
									if (positionvalue != 0) {
										positionvalue = positionvalue - 1;
									}
								} else {
									if (positionvalue != 255) {
										positionvalue = positionvalue + 1;
									}
								}
								mParameterMananer.saveIntParameters(ParameterMananer.KEY_DISEQC1_2_DISH_CURRENT_POSITION, positionvalue);
								break;
							default:
								needbreak = true;
								break;
						}
						if (!needbreak && mCurrentSubCustomDialog != null && mCurrentSubCustomDialog.isShowing()) {
							mCurrentSubCustomDialog.updateDiseqc1_2_Dialog();
						} else {
							Log.d(TAG, "mCurrentSubCustomDialog null or need break or not displayed");
						}
					}
					break;
				case ParameterMananer.KEY_FUNCTION:
					if (data != null) {
						String action = data.getString("action");
						String listtype = data.getString("listtype");
						switch (action) {
							case "add":
								if (ITEM_SATALLITE.equals(data.getString("listtype"))) {
									mCurrentCustomDialog = mDialogManager.buildAddSatelliteDialogDialog(null, mSingleSelectDialogCallBack);
								} else if (ITEM_TRANSPONDER.equals(data.getString("listtype"))) {
									mCurrentCustomDialog = mDialogManager.buildAddTransponderDialogDialog(null, mSingleSelectDialogCallBack);
								} else {
									Log.d(TAG, "not sure");
									mCurrentCustomDialog = null;
								}
								if (mCurrentCustomDialog != null) {
									mCurrentCustomDialog.showDialog();
								}
								break;
							case "edit":
								if (ITEM_SATALLITE.equals(data.getString("listtype"))) {
									mCurrentCustomDialog = mDialogManager.buildAddSatelliteDialogDialog(data.getString("parameter"), mSingleSelectDialogCallBack);
								} else if (ITEM_TRANSPONDER.equals(data.getString("listtype"))) {
									mCurrentCustomDialog = mDialogManager.buildAddTransponderDialogDialog(data.getString("parameter"), mSingleSelectDialogCallBack);
								} else {
									Log.d(TAG, "not sure");
									mCurrentCustomDialog = null;
								}
								if (mCurrentCustomDialog != null) {
									mCurrentCustomDialog.showDialog();
								}
								break;
							case "delete":
								if (ITEM_SATALLITE.equals(data.getString("listtype"))) {
									mCurrentCustomDialog = mDialogManager.buildRemoveSatelliteDialogDialog(data.getString("parameter"), mSingleSelectDialogCallBack);
								} else if (ITEM_TRANSPONDER.equals(data.getString("listtype"))) {
									mCurrentCustomDialog = mDialogManager.buildRemoveTransponderDialogDialog(data.getString("parameter"), mSingleSelectDialogCallBack);
								} else {
									Log.d(TAG, "not sure");
									mCurrentCustomDialog = null;
								}
								if (mCurrentCustomDialog != null) {
									mCurrentCustomDialog.showDialog();
								}
								break;
							case "scan":
								Intent intent = new Intent();
								intent.setClassName("org.dtvkit.inputsource", "org.dtvkit.inputsource.DtvkitDvbsSetup");
								getActivity().startActivityForResult(intent, ScanMainActivity.REQUEST_CODE_START_SETUP_ACTIVITY);
								//getActivity().finish();
								break;
							default:
								break;
						}
					}
					break;
				default:
					break;
			}
		}
	};

	ListItemSelectedListener mListItemSelectedListener = new ListItemSelectedListener() {

		@Override
		public void onListItemSelected(int position, String type) {
			Log.d(TAG, "onListItemSelected position = " + position + ", type = " + type);
			if (ItemListView.LIST_LEFT.equals(mCurrentListFocus)) {
				String listtype = mParameterMananer.getCurrentListType();
				if (ITEM_SATALLITE.equals(listtype)) {
					LinkedList<ItemDetail> items = mParameterMananer.getSatelliteList();
					mParameterMananer.setCurrentSatellite(items != null ? items.get(position).getFirstText() : "null");
				} else if (ParameterMananer.ITEM_TRANSPONDER.equals(listtype)) {
                    LinkedList<ItemDetail> items = mParameterMananer.getTransponderList();
					mParameterMananer.setCurrentTransponder(items != null ? (items.get(position).getFirstText()) : "null");
				}
				mItemDetailItem.clear();
				mItemAdapterItem.reFill(mParameterMananer.getItemList(listtype));
            } else if (ItemListView.LIST_RIGHT.equals(mCurrentListFocus)) {
			    if (position == 0 || position == 1) {
			        Log.d(TAG, "satellite or transponder no sub menu");
			        return;
                }
                mCurrentCustomDialog = mDialogManager.buildItemDialogById(position, mSingleSelectDialogCallBack);
				if (mCurrentCustomDialog != null) {
					mCurrentCustomDialog.showDialog();
				}
				/*int optionitemIndex = position;
				AlertDialog singleSelectDialog = null;
				switch (optionitemIndex) {
					case 0:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_SATALLITE, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 1:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_TRANSPONDER, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 2:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_LNB_TYPE, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 3:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_UNICABLE, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 4:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_LNB_POWER, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 5:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_22_KHZ, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 6:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_TONE_BURST, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 7:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_DISEQC1_0, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 8:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_DISEQC1_1, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					case 9:
						singleSelectDialog = mDialogManager.buildSelectSingleItemDialog(ParameterMananer.KEY_MOTOR, mSingleSelectDialogCallBack);
						singleSelectDialog.show();
						break;
					default:
						break;
				}*/
			}
		}
		
	};
	
	ListItemFocusedListener mListItemFocusedListener = new ListItemFocusedListener() {

		@Override
		public void onListItemFocused(View parent, int position, String type) {
			Log.d(TAG, "onListItemFocused position = " + position + ", type = " + type);
			/*if (ItemListView.LIST_LEFT.equals(mCurrentListFocus) && ItemListView.isRightList(type)) {
            	mListViewOption.cleanChoosed();
            }*/
			if (ItemListView.LIST_LEFT.equals(mCurrentListFocus)) {
				/*mItemDetailOption.clear();
				mItemAdapterOption.reFill(mParameterMananer.getCompleteParameterList(type, position));
				if (mItemAdapterOption.getCount() > 0) {
					mListViewOption.setSelection(0);
				}*/
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
			if (ITEM_SATALLITE.equals(mCurrentListType)) {
				mListViewItem.setSelection(mParameterMananer.getCurrentSatelliteIndex());
			} else if (ParameterMananer.ITEM_TRANSPONDER.equals(mCurrentListType)) {
				mListViewItem.setSelection(mParameterMananer.getCurrentTransponderIndex());
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
			if (ITEM_SATALLITE.equals(mCurrentListType)) {
				mListViewItem.setSelection(mParameterMananer.getCurrentSatelliteIndex());
			} else {
				mListViewItem.setSelection(mParameterMananer.getCurrentTransponderIndex());
			}
			mListViewItem.requestFocus();
            mItemTitleTextView.setText(ITEM_SATALLITE.equals(mParameterMananer.getCurrentListType()) ? R.string.list_type_satellite : R.string.list_type_transponder);
			mItemAdapterOption.reFill(mParameterMananer.getCompleteParameterList(mParameterMananer.getCurrentListType(), ITEM_SATALLITE.equals(mCurrentListType) ? mParameterMananer.getCurrentSatelliteIndex() : mParameterMananer.getCurrentTransponderIndex()));
			mListViewOption.cleanChoosed();
            mOptionTitleItemTextView.setText(mParameterMananer.getParameterListTitle(mParameterMananer.getCurrentListType(), ParameterMananer.ITEM_TRANSPONDER.equals(mParameterMananer.getCurrentListType()) ? mParameterMananer.getCurrentSatelliteIndex() : mParameterMananer.getCurrentTransponderIndex())/*"Ku_NewSat2"*/);
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

