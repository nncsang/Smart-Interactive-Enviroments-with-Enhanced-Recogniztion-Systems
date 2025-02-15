package com.example.restclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;

import java.util.ArrayList;

public class VideoViewerActivity extends FragmentActivity {

    private class VideoPagerAdapter extends FragmentStatePagerAdapter {
        public VideoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return VideoPageFragment.create(position);
        }

        @Override
        public int getCount() {
            return VideoPageFragment.getPageCount();
        }
    }

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<String> vidIds;
    private ArrayList<String> vidTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Intent intent = getIntent();
        vidIds = new ArrayList<String>();
        vidTexts = new ArrayList<String>();
        // Use this cmd to put ArrayList<String> to intent
        //intent.putStringArrayListExtra("imgUrls", imgUrls);
        // And use this cmd to get ArrayList<String> from intent
        //imgUrls = intent.getStringArrayListExtra("imgUrls");
        for(int i = 0; i < Setting.mVideo.Videos.size(); i++)
            vidIds.add(Setting.mVideo.Videos.get(i));

        for(int i = 0; i < Setting.mVideo.Videos_Desc.size(); i++)
            vidTexts.add(Setting.mVideo.Videos_Desc.get(i));
        // imgTexts = intent.getStringArrayListExtra("imgTexts", imgTexts);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int screenHeight = display.getHeight();
        VideoPageFragment.init(vidIds, vidTexts, screenHeight);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPagerAdapter = new VideoPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setOffscreenPageLimit(2);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_viewer, menu);
        return true;
    }
}
