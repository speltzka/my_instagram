package com.example.myinstagram;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myinstagram.fragments.ComposeFragment;
import com.example.myinstagram.fragments.ProfileFragment;
import com.example.myinstagram.fragments.TimelineFragment;

public class InstaFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Timeline", "Compose","Profile" };
    private Context context;

    public InstaFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TimelineFragment.newInstance(position);
        } else if (position == 1) {
            return ComposeFragment.newInstance(position);
        } else {
            return ProfileFragment.newInstance(position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
