package com.eduhub.company.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.eduhub.company.R;
import com.eduhub.company.fragments.nav_frag.Chat;
import com.eduhub.company.fragments.nav_frag.Home;
import com.eduhub.company.fragments.nav_frag.Info;
import com.eduhub.company.fragments.nav_frag.Profile;
import com.eduhub.company.fragments.nav_frag.Ask;

public class Dashboard extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tabLayout = findViewById(R.id.main_tabLayout);
        viewPager = findViewById(R.id.viewPager);
        boolean doubleBackToExitPressedOnce = false;
        int[] tabIcons = {
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_chat_black_24dp,
                R.drawable.ic_file_upload_black_24dp,
                R.drawable.ic_info_outline_black_24dp,
                R.drawable.ic_account_circle_black_24dp
        };
        final String[] tabNames={
                "Home",
                "Chat",
                "Ask",
                "Info",
                "Profile"
        };


        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[1]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[2]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[3]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[4]));

        PagerAdapter pagerAdapter =  new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(tabNames[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

class PagerAdapter extends FragmentPagerAdapter {

    int count;

    public PagerAdapter(FragmentManager fm,int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new Home();
            case 1:
                return new Chat();
            case 2:
                return new Ask();
            case 3:
                return new Info();
            case 4:
                return new Profile();
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
