package com.example.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.fragment.ItemAdapter.ItemDetail;

import android.content.Context;
import android.content.SharedPreferences;

public class ParameterMananer {
    
	private static final String TAG = "ParameterMananer";
	private Context mContext;
	
	public static final String ITEM_SATALLITE              = "satellite";
	public static final String ITEM_TRANSPONDER            = "transponder";
	public static final String ITEM_SATALLITE_OPTION       = "satallite_option";
    public static final String ITEM_TRANSPONDER_OPTION     = "tansponder_option";

    public static final String SAVE_SATELITE_POSITION = "satellite_position";
	public static final String SAVE_TRANSPONDER_POSITION = "transponder_position";
	public static final String SAVE_CURRENT_LIST_TYPE = "current_list_type";
	
	public ParameterMananer(Context context) {
		this.mContext = context;
	}
	
	public LinkedList<ItemDetail> getSatelliteList() {
		//need to add get function, debug as below
		LinkedList<ItemDetail> list = new LinkedList<ItemDetail>();
		String[] all = ALL_SATALLITE;
		if (all == null) {
			return list;
		}
		int current = getCurrentSatellite();
		int type = 0;
		for (int i = 0; i < all.length; i++) {
			if (current == i) {
				type = ItemDetail.SELECT_EDIT;
			} else {
				type = ItemDetail.NOT_SELECT_EDIT;
			}
			list.add(new ItemDetail(type, all[i], null));
		}

		return list;
	}
	
	private int mCurrentSatellite = -1;
	private String mCurrentListType = null;
	private final String[] ALL_SATALLITE = {"001 013.OE Ku_HOTBIRD 6", "002 013.OE Ku_HOTBIRD 6", "003 013.OE Ku_HOTBIRD 6",
			                                "004 013.OE Ku_HOTBIRD 6", "005 013.OE Ku_HOTBIRD 6", "006 013.OE Ku_HOTBIRD 6",
			                                "007 013.OE Ku_HOTBIRD 6"};
	private int mCurrentTransponder = -1;
	private final String[] ALL_TRANSPONDER = {"001 10723 H 29900008", "001 10723 H 29900008", "001 10723 H 29900008",
			                                  "001 10723 H 29900008", "001 10723 H 29900008", "001 10723 H 29900008",
			                                  "001 10723 H 29900008"};
	
	public int getCurrentSatellite() {
		if (mCurrentSatellite == -1) {
			mCurrentSatellite = getIntParameters(SAVE_SATELITE_POSITION);
			if (mCurrentSatellite == -1) {
				saveIntParameters(SAVE_SATELITE_POSITION, 0);
				mCurrentSatellite = 0;
			}
		}
		return mCurrentSatellite;
	}
	
	public void setCurrentSatellite(int position) {
		mCurrentSatellite = position;
		saveIntParameters(SAVE_SATELITE_POSITION, position);
	}
	
	public String getCurrentListType() {
		if (mCurrentListType == null) {
			mCurrentListType = getStringParameters(SAVE_CURRENT_LIST_TYPE);
			if (mCurrentListType == null) {
				saveStringParameters(SAVE_CURRENT_LIST_TYPE, ITEM_SATALLITE);
				mCurrentListType = ITEM_SATALLITE;
			}
		}
		return mCurrentListType;
	}
	
	public void setCurrentListType(String type) {
		mCurrentListType = type;
		saveStringParameters(SAVE_CURRENT_LIST_TYPE, type);
	}
	
	public int getCurrentTransponder() {
		//return getIntParameters(SAVE_TRANSPONDER_POSITION);
		if (mCurrentTransponder == -1) {
			mCurrentTransponder = getIntParameters(SAVE_TRANSPONDER_POSITION);
			if (mCurrentTransponder == -1) {
				saveIntParameters(SAVE_TRANSPONDER_POSITION, 0);
				mCurrentTransponder = 0;
			}
		}
		return mCurrentTransponder;
	}
	
	public void setCurrentTransponder(int position) {
		mCurrentTransponder = position;
		saveIntParameters(SAVE_TRANSPONDER_POSITION, position);
	}
	
	public LinkedList<ItemDetail> getTransponderList() {
		LinkedList<ItemDetail> list = new LinkedList<ItemDetail>();
		String[] all = ALL_TRANSPONDER;
		if (all == null) {
			return null;
		}
		int current = getCurrentTransponder();
		int type = 0;
		for (int i = 0; i < all.length; i++) {
			if (current == i) {
				type = ItemDetail.SELECT_EDIT;
			} else {
				type = ItemDetail.NOT_SELECT_EDIT;
			}
			list.add(new ItemDetail(type, all[i], null));
		}

		return list;
	}
	
	public LinkedList<ItemDetail> getItemList(String type) {
		if (ITEM_SATALLITE.equals(type)) {
			return getSatelliteList();
		} else if (ITEM_TRANSPONDER.equals(type)) {
			return getTransponderList();
		} else {
			return new LinkedList<ItemDetail>();
		}
	}

	public String getParameterListTitle(String type, int position) {
		String title = null;
		//need to add get function, debug as below
		title = "Ku_HOTBIRO6,7A,8";
		if (ITEM_SATALLITE.equals(type)) {
			title = "Ku_NewSat2";
		} else if (ITEM_TRANSPONDER.equals(type)) {
			title = "Ku_HOTBIRO6,7A,8";
		} else {
			return "";
		}
		return title;
	}
	
	private List<String> getParameterListType(String type, int position) {
		List<String> list = new ArrayList<String>();
		//need to add get function, debug as below
		list.add("LNB Type");
		list.add("LNB Power");
		list.add("22KHZ");
		list.add("Toneburst");
		list.add("DisEqC1.0");
		list.add("DisEqC1.1");
		list.add("Motor");
		return list;
	}
	
	private List<String> getParameterListValue(String type, int position) {
		List<String> list = new ArrayList<String>();
		//need to add get function, debug as below
		list.add("09750/10600");
		list.add("13/18V");
		list.add("Auto");
		list.add("BurstB");
		list.add("LNB2");
		list.add("LNB6");
		list.add("None");
		return list;
	}
	
	public LinkedList<ItemDetail> getCompleteParameterList(String type, int position) {
		List<String> parametertype = getParameterListType(type, position);
		List<String> parametervalue = getParameterListValue(type, position);
		LinkedList<ItemDetail> list = new LinkedList<ItemDetail>();
		if (parametertype.size() != parametervalue.size()) {
			return list;
		}

		int size = parametertype.size();
		for (int i = 0; i < parametertype.size(); i++) {
			list.add(new ItemDetail(ItemDetail.NONE_EDIT, parametertype.get(i), parametervalue.get(i)));
		}

		return list;
	}

	public void saveIntParameters(String key, int value) {
		SharedPreferences sp = mContext.getSharedPreferences("dish_parameter", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public int getIntParameters(String key) {
		SharedPreferences sp = mContext.getSharedPreferences("dish_parameter", Context.MODE_PRIVATE);
		return sp.getInt(key, -1);
	}

	public void saveStringParameters(String key, String value) {
		SharedPreferences sp = mContext.getSharedPreferences("dish_parameter", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getStringParameters(String key) {
		SharedPreferences sp = mContext.getSharedPreferences("dish_parameter", Context.MODE_PRIVATE);
		return sp.getString(key, null);
	}
}
