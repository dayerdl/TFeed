package com.raza.twitter.feed.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.raza.twitter.feed.bLogic.ImageUtils;
import com.raza.twitter.feed.prefs.Constants;


/**
 *
 * @author jaffar.raza
 */
public abstract class BaseFragment extends Fragment {

    private View view;
    private SharedPreferences prefs;

    public boolean isFragmentRunning;
    public ProgressDialog progressDialog;
    public FragmentNavigationHelper fragmentHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentHelper = (FragmentNavigationHelper) activity;
        } catch (Exception e) {
        }
        prefs = activity.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        view.setClickable(true);
        isFragmentRunning = true;

        setRetainInstance(true);
        progressDialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
    }

    public FragmentNavigationHelper getHelper() {
        return this.fragmentHelper;
    }

    public View getParentView() {
        return this.view;
    }

    public abstract int getLayoutId();


    public void initViews(View parent, Bundle savedInstanceState) {

    }

    public SharedPreferences getSharedPreferences() {
        return prefs;
    }

    public String getTitle() {
        return "";
    }

    public int getBackgroundResId() {
        return android.R.drawable.editbox_background;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideKeyboard();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public void hideKeyboard() {
        if (getView() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void showKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void hideKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    public void hideKeyboard(View input) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    public void loadImage(String url, ImageView imageView) {
        ImageUtils.loadImage(getActivity(), url, imageView);
    }

    public void loadResizedImage(String url, ImageView imageView, int reqWidth, int reqHeight) {
//        ImageUtils.loadResizedImage(getActivity(), url, imageView, reqWidth, reqHeight);
    }

    public void loadImage(String url, ImageView imageView, int radius, int margin) {
        ImageUtils.loadImage(getActivity(), url, imageView, radius, margin);
    }



    /**
     * An interface to load and make navigation. The parent activity must provide an implementation for this interface.
     *
     * @author khawarraza
     */
    public interface FragmentNavigationHelper {

        public void addFragment(BaseFragment f, boolean clearBackStack, boolean addToBackstack);

        public void addFragment(BaseFragment f, int layoutId, boolean clearBackStack, boolean addToBackstack);

        public void replaceFragment(BaseFragment f, boolean clearBackStack, boolean addToBackstack);

        public void replaceFragment(BaseFragment f, int layoutId, boolean clearBackStack, boolean addToBackstack);

        public void onBack();


    }



}
