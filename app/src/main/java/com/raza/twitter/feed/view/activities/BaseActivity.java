package com.raza.twitter.feed.view.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.raza.twitter.feed.prefs.Constants;
import com.raza.twitter.feed.view.fragments.BaseFragment;

import java.util.Stack;
/**
 * Created by jaffarraza on 02/08/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.FragmentNavigationHelper {

	public BaseFragment mCurrentFragment;
	private SharedPreferences prefs;
	private Stack<Fragment> mFragments = new Stack<Fragment>();
	private Dialog dialog;
	public boolean isActivityRunning;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);

		LayoutInflater inflater = getLayoutInflater();
		View contentView = inflater.inflate(getLayoutId(), null);
		RelativeLayout rel = new RelativeLayout(this);
		rel.addView(contentView);
		setContentView(rel);

		initViews(savedInstanceState);
	}

	public void initViews(Bundle savedInstanceState) {

	}

	public abstract int getLayoutId();

	@Override
	public void addFragment(BaseFragment f, boolean clearBackStack, boolean addToBackstack) {
//		addFragment(f, R.id.fragment_container, clearBackStack, addToBackstack);
	}

	public void addFragment(BaseFragment f, int layoutId, boolean clearBackStack, boolean addToBackstack) {
//		if (clearBackStack) {
//			clearFragmentBackStack();
//		}
//		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//		transaction.add(R.id.fragment_container, f);
//		if(addToBackstack) {
//			transaction.addToBackStack(null);
//		}
//		transaction.commitAllowingStateLoss();
//
//		mCurrentFragment = f;
//		mFragments.push(f);
//
//		onFragmentBackStackChanged();
	}

	@Override
	public void replaceFragment(BaseFragment f, boolean clearBackStack, boolean addToBackstack) {
//		replaceFragment(f, R.id.fragment_container, clearBackStack, addToBackstack);
	}

	public void replaceFragment(BaseFragment f, int layoutId, boolean clearBackStack, boolean addToBackstack) {
		if(clearBackStack) {
			clearFragmentBackStack();
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(layoutId, f);
		if(addToBackstack) {
			transaction.addToBackStack(null);
		}
		transaction.commit();

		mCurrentFragment = f;
		mFragments.push(f);

		onFragmentBackStackChanged();
	}

	@Override
	public void onBack() {
		try {
			if(getSupportFragmentManager().getBackStackEntryCount() <= 1) {
				finish();
				return;
			}
			getSupportFragmentManager().popBackStack();
			mFragments.pop();
			mCurrentFragment = (BaseFragment) (mFragments.isEmpty() ? null : ((mFragments.peek() instanceof BaseFragment) ? mFragments.peek() : null));

			onFragmentBackStackChanged();
		} catch (Exception e) {
			finish();
		}
	}

	public void clearFragmentBackStack() {
		FragmentManager fm = getSupportFragmentManager();
		for(int i = 0; i < fm.getBackStackEntryCount() - 1; i++) {    
			fm.popBackStack();
		}
		if(!mFragments.isEmpty()) {
			Fragment homeFragment = mFragments.get(0);
			mFragments.clear();
			mFragments.push(homeFragment);
		}
	}

	public void onFragmentBackStackChanged() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
				finish();
				return true;
			} else {
				onBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void hideKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public void showKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	public void showForcedKeyboard(View v) {
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(v, 0);
	}

	public BaseFragment getCurrentFragment() {
		return mCurrentFragment;
	}

	public void setCurrentFragment(BaseFragment mCurrentFragment) {
		this.mCurrentFragment = mCurrentFragment;
	}

	public SharedPreferences getPrefs() {
		return prefs;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
