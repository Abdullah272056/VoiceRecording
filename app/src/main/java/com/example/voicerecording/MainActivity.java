package com.example.voicerecording;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.voicerecording.fragment.Recorder;
import com.example.voicerecording.fragment.Recording;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        //setSupportActionBar(toolbar);


        tabLayout.addTab(tabLayout.newTab().setText("Recorder"));
        tabLayout.addTab(tabLayout.newTab().setText("Recording"));


        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    public  class PagerAdapter extends FragmentPagerAdapter {
        int tabCount;
        public PagerAdapter(@NonNull FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount=tabCount;
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            // it is fragment position
            switch (position){
                case 0:
                    return  new Recorder();
                case 1:
                    return  new Recording();
            }
            return null;
        }
        @Override
        public int getCount() {
            return tabCount;
        }
    }


}