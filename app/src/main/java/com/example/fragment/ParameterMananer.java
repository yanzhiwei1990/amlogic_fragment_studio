package com.example.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.fragment.ItemAdapter.ItemDetail;

import android.content.Context;

public class ParameterMananer {
    
	private static final String TAG = "ParameterMananer";
	private Context mContext;
	
	public static final String ITEM_SATALLITE              = "satellite";
	public static final String ITEM_TRANSPONDER            = "transponder";
	public static final String ITEM_SATALLITE_OPTION       = "satallite_option";
    public static final String ITEM_TRANSPONDER_OPTION     = "tansponder_option";
	
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
	
	private int mCurrentSatellite = 0;
	private String mCurrentListType = ITEM_SATALLITE;
	private final String[] ALL_SATALLITE = {"001 013.OE Ku_HOTBIRD 6", "002 013.OE Ku_HOTBIRD 6", "003 013.OE Ku_HOTBIRD 6",
			                                "004 013.OE Ku_HOTBIRD 6", "005 013.OE Ku_HOTBIRD 6", "006 013.OE Ku_HOTBIRD 6",
			                                "007 013.OE Ku_HOTBIRD 6"};
	private int mCurrentTransponder = 0;
	private final String[] ALL_TRANSPONDER = {"001 10723 H 29900008", "001 10723 H 29900008", "001 10723 H 29900008",
			                                  "001 10723 H 29900008", "001 10723 H 29900008", "001 10723 H 29900008",
			                                  "001 10723 H 29900008"};
	
	public int getCurrentSatellite() {
		return mCurrentSatellite;
	}
	
	public void setCurrentSatellite(int position) {
		mCurrentSatellite = position;
	}
	
	public String getCurrentListType() {
		return mCurrentListType;
	}
	
	public void setCurrentListType(String type) {
		mCurrentListType = type;
	}
	
	public int getCurrentTransponder() {
		return mCurrentTransponder;
	}
	
	public void setCurrentTransponder(int position) {
		mCurrentTransponder = position;
	}
	
	public LinkedList<ItemDetail> getTransponderList() {
		LinkedList<ItemDetail> list = new LinkedList<ItemDetail>();
		String[] all = ALL_TRANSPONDER;
		if (all == null) {
			return null;
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
	
	public LinkedList<ItemDetail> getItemList(String type) {
		if (ITEM_SATALLITE.equals(type)) {
			return getSatelliteList();
		} else if (ITEM_TRANSPONDER.equals(type)) {
			return getTransponderList();
		} else {
			return new LinkedList<ItemDetail>();
		}
	}
	
	public String getParameterListTitle(int type, int position) {
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
}
