package com.eduhub.company.activities;

import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.eduhub.company.R;
import com.eduhub.company.fragments.nav_frag_parent.ChatFragP;
import com.eduhub.company.fragments.nav_frag_parent.HomeFragP;
import com.eduhub.company.fragments.nav_frag_parent.ProfileFragP;

public class MainActivityParent extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parent);

        tabLayout = findViewById(R.id.main_tabLayout);
        viewPager = findViewById(R.id.viewPager);
        boolean doubleBackToExitPressedOnce = false;
        int[] tabIcons = {
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_chat_black_24dp,
                R.drawable.ic_account_circle_black_24dp
        };
        final String[] tabNames={
                "Home",
                "Chat",
                "Profile"
        };


        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[1]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[2]));

        PagerAdapterP pagerAdapter =  new PagerAdapterP(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(3);
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

class PagerAdapterP extends FragmentPagerAdapter {

    int count;

    public PagerAdapterP(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new HomeFragP();
            case 1:
                return new ChatFragP();
            case 2:
                return new ProfileFragP();
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
