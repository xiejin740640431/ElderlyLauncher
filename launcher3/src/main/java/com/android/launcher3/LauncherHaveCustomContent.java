package com.android.launcher3;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;

import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.ui.firstscreen.FirstScreenFragment;

/**
 * Created by Administrator on 2017/3/9.
 */

public class LauncherHaveCustomContent extends Launcher {
    @Override
    protected boolean hasCustomContentToLeft() {
        return true;
    }

    @Override
    public void bindScreens(ArrayList<Long> orderedScreenIds) {
        super.bindScreens(orderedScreenIds);
        if(hasCustomContentToLeft()){
            View customView = getLayoutInflater().inflate(R.layout.layout_customview, null);
            CustomContentCallbacks customContentCallbacks = new CustomContentCallbacks() {

                @Override
                public void onShow(boolean fromResume) {

                }

                @Override
                public void onHide() {

                }

                @Override
                public void onScrollProgressChanged(float progress) {

                }

                @Override
                public boolean isScrollingAllowed() {
                    return true;
                }
            };
            addToCustomContentPage(customView, customContentCallbacks, "彩虹在线");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.framelayout_service, new FirstScreenFragment());
            transaction.commit();
        }
    }

}
