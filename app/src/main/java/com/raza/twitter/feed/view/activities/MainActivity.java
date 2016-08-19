package com.raza.twitter.feed.view.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.raza.twitter.feed.R;
import com.raza.twitter.feed.bLogic.Utils;
import com.raza.twitter.feed.view.fragments.TwitterFeed;
import com.raza.twitter.feed.view.fragments.UserTimeLine;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jaffarraza on 10/08/16.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.page_strip)
    PagerTabStrip pagerHeader;
    @Bind(R.id.pager)
    ViewPager vpPager;


    private PagerAdapter vPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ButterKnife.bind(this);

        //Initialize Pager Adapter for View Pager.
        vPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(vPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.arabic) {
            Utils.getInstance().setLocale(this, "ar");
            return true;
        }
        if (id == R.id.english) {
            Utils.getInstance().setLocale(this, "en");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show UserTimeLine Fragment
                    return UserTimeLine.newInstance(0);
                case 1: // Fragment # 0 - This will show TwitterFeed
                    return TwitterFeed.newInstance(1, "@OLXEgypt");
                case 2: // Fragment # 1 - This will show TwitterFeed with different title
                    return TwitterFeed.newInstance(2, "@AndroidDev");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "User Timeline";
                    break;
                case 1:
                    title = "@OLXEgypt";
                    break;
                case 2:
                    title = "@AndroidDev";
                    break;
            }
            return title;
        }

    }


}
