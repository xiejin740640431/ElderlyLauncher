package cn.colorfuline.elderlylauncher.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ryg.library_indicatorfragment.TabInfo;
import com.ryg.library_indicatorfragment.TitleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.colorfuline.base.fragment.CFBaseFragment;
import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.ui.firstscreen.adapter.MFragmentPagerAdapter;

/**
 * Created by Administrator on 2016/4/13.
 */
public abstract class BaseTabFragment extends Fragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.titleIndicator)
    TitleIndicator titleIndicator;
    @BindView(R.id.viewPager_msg)
    ViewPager viewPagerMsg;
    protected FrameLayout rootView;
    //存放选项卡信息的列表
    protected ArrayList<TabInfo> tabs = new ArrayList<TabInfo>();
    /**
     * ViewPager适配器
     */
    private MFragmentPagerAdapter mFragmentPagerAdapter;
    /**
     * 最后选择的tab索引
     */
    private int mLastTab = -1;
    /**
     * 当前选中的tab索引
     */
    private int mCurrentTab = 0;
    /**
     * 界面create前，语音指定跳转界面
     */
    private int speechPosition = -1;
    /**
     * 界面create前，语音指定类型
     */
    private int speechType = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = (FrameLayout) inflater.inflate(R.layout.fragment_base_first_screen, container, false);
        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeAllViews();
            }
        }
        getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_tab, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, rootView);
        initViews();
    }

    /**
     * 初始化控件
     */
    private final void initViews() {
        //defSelectTab没有用selectPosition()方法会把mCurrentTab初始化值
        int defSelectTab = supplyTabs(tabs);
        mFragmentPagerAdapter = new MFragmentPagerAdapter(getChildFragmentManager(), tabs);
        viewPagerMsg.setAdapter(mFragmentPagerAdapter);
        viewPagerMsg.addOnPageChangeListener(this);
        viewPagerMsg.setOffscreenPageLimit(tabs.size());
        titleIndicator.init(mCurrentTab, tabs, viewPagerMsg);
        viewPagerMsg.setCurrentItem(mCurrentTab);
        mLastTab = mCurrentTab;
        setFragmentType(speechPosition, speechType);
    }

    /**
     * 添加一个选项卡
     *
     * @param tab
     */
    public void addTabInfo(TabInfo tab) {
        tabs.add(tab);
        mFragmentPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 从列表添加选项卡
     *
     * @param tabs
     */
    public void addTabInfos(ArrayList<TabInfo> tabs) {
        tabs.addAll(tabs);
        mFragmentPagerAdapter.notifyDataSetChanged();
    }


    /**
     * 在这里提供要显示的选项卡数据
     */
    protected abstract int supplyTabs(List<TabInfo> tabs);


    /**
     * 选中
     *
     * @param position
     */
    public void selectPosition(int position) {
        this.mCurrentTab = position;
        if (viewPagerMsg != null)
            viewPagerMsg.setCurrentItem(mCurrentTab);
    }

    /**
     * 选中
     *
     * @param position
     */
    public void selectPosition(int position, Object object) {
        selectPosition(position);
        setFragmentType(position, object);
    }

    /**
     * @param position
     * @param object
     */
    public void setFragmentType(int position, Object object) {
        if (mFragmentPagerAdapter != null && position >= 0 && position < mFragmentPagerAdapter.getCount()) {
            Fragment fragment = mFragmentPagerAdapter.getItem(position);
//            if (fragment instanceof MineFragment) {
//                if (object instanceof Integer) {
//                    ((MineFragment) fragment).chengeTag((Integer) object);
//                }
//            }
        } else {
            speechPosition = position;
            speechType = (int) object;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        titleIndicator.onScrolled((viewPagerMsg.getWidth() + viewPagerMsg.getPageMargin()) * position + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        titleIndicator.onSwitched(position);
        mCurrentTab = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mLastTab = mCurrentTab;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabs.clear();
        tabs = null;
        mFragmentPagerAdapter.notifyDataSetChanged();
        mFragmentPagerAdapter = null;
        viewPagerMsg.setAdapter(null);
        viewPagerMsg = null;
        titleIndicator = null;
    }
}
