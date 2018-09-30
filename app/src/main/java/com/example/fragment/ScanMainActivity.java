package com.example.fragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.os.Build;

public class ScanMainActivity extends Activity {
    private static final String TAG = "ScanMainActivity";
	private Button mDishSetup = null;
	private Button mScanChannel = null;
	private int mCurrentFragment = 0;
    private ScanFragmentManager mScanFragmentManager = null;
    private ParameterMananer mParameterMananer = null;
	
	private static final int INIT_FRAGMENT = 0;
	private static final int SCAN_FRAGMENT = 1;
	private static final int DISH_SETUP_FRAGMENT = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mScanFragmentManager = new ScanFragmentManager(this);
		mScanFragmentManager.show(new PlaceholderFragment());
		mParameterMananer = new ParameterMananer(this);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mScanFragmentManager.removeRunnable();
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public class PlaceholderFragment extends Fragment {

		Button mDishSetup;
		Button mScanChannel;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			initButton(rootView);
			return rootView;
		}

		private void initButton(final View view) {
			if (view == null) {
				return;
			}
			mDishSetup = (Button) view.findViewById(R.id.button_dish_setup);
			mScanChannel = (Button) view.findViewById(R.id.button_scan_channel);
			mScanChannel.requestFocus();
			mDishSetup.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//getFragmentManager().beginTransaction().add(R.id.container, DishSetupFragment.newInstance()).commit();
					mScanFragmentManager.show(ScanDishSetupFragment.newInstance());
					Log.d(TAG, "ScanDishSetupFragment");
				}
			});
			mScanChannel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//getFragmentManager().beginTransaction().add(R.id.container, ScanChannelFragment.newInstance()).commit();
					mScanFragmentManager.show(ScanChannelFragment.newInstance());
					Log.d(TAG, "ScanChannelFragment");
				}
			});
		}
	}
	
	public ParameterMananer getParameterMananer() {
		return mParameterMananer;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mScanFragmentManager.isActive()) {
				mScanFragmentManager.popSideFragment();
				Log.d(TAG, "onKeyUp");
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}
}
