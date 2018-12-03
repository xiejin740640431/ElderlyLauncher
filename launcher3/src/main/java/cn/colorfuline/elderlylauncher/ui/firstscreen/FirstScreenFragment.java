package cn.colorfuline.elderlylauncher.ui.firstscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ryg.library_indicatorfragment.TabInfo;

import java.util.List;

import cn.colorfuline.base.widget.CustomTitleBar;
import cn.colorfuline.elderlylauncher.ui.BaseTabFragment;
import cn.colorfuline.elderlylauncher.ui.firstscreen.fragment.InformationFragment;
import cn.colorfuline.elderlylauncher.ui.firstscreen.fragment.PersonFragment;
import cn.colorfuline.elderlylauncher.ui.firstscreen.fragment.ServiceFragment;

/**
 * Created by Administrator on 2017/3/10.
 */

public class FirstScreenFragment extends BaseTabFragment {
    View rootView;
    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        initUserHelper();
        tabs.add(new TabInfo(FRAGMENT_ONE, "服务", 0, 0, ServiceFragment.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, "资讯", 0, 0, InformationFragment.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, "个人", 0, 0, PersonFragment.class));
        return FRAGMENT_TWO;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initUserHelper() {
        UserHelper userHelper = UserHelper.getInstance();
        userHelper.init(getActivity());
    }
}
