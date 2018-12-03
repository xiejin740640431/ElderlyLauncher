package cn.colorfuline.elderlylauncher.ui.firstscreen.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ryg.library_indicatorfragment.TabInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/4/13.
 */
public class MFragmentPagerAdapter extends FragmentPagerAdapter {
    List<TabInfo> tabs = null;

    public MFragmentPagerAdapter(FragmentManager fm, ArrayList<TabInfo> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;
        if (tabs != null && pos < tabs.size()) {
            TabInfo tab = tabs.get(pos);
            if (tab == null)
                return null;
            fragment = tab.createFragment();
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        if (tabs != null && tabs.size() > 0)
            return tabs.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TabInfo tab = tabs.get(position);
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        tab.fragment = fragment;
        return fragment;
    }
}
