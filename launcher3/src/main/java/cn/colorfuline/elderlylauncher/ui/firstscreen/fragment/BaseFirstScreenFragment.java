package cn.colorfuline.elderlylauncher.ui.firstscreen.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.bean.DownloadBean;
import cn.colorfuline.elderlylauncher.services.DownloadService;
import cn.colorfuline.elderlylauncher.utils.StorageUtil;


/**
 * Created by Administrator on 2016/4/12.
 */
public abstract class BaseFirstScreenFragment extends Fragment {
    protected FrameLayout rootView;
    /**
     * Pop菜单
     */
    private PopupWindow popupWindow;

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
        getLayoutInflater(savedInstanceState).inflate(getLayout(), rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* SDK19：可以设置状态栏透明，但是半透明的SYSTEM_BAR_BACKGROUNDS会不好看；
        * SDK21：可以设置状态栏颜色，并且可以清除SYSTEM_BAR_BACKGROUNDS，但是不能设置状态栏字体颜色（默认的白色字体在浅色背景下看不清楚）；
        * SDK23：状态栏为浅色（SYSTEM_UI_FLAG_LIGHT_STATUS_BAR） 字体白色，设置后字体就回反转为黑色。
        */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getActivity(). getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            if(Build.VERSION_CODES.M> Build.VERSION.SDK_INT &&
//                    Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
//                //  在5.0上增加了WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS 和 getWindow().setStatusBarColor(int color)
//                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.transparent_2));// SDK21
//            }
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
//                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        }
    }

    /**
     * @return
     */
    public abstract int getLayout();

    /**
     * 显示下载确定dialog
     * @param packageName
     * @param message
     * @param title
     * @param downloadUrl
     */
    public void showConfirmDialog(final String packageName,String message,final String title,final String downloadUrl){
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.str_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String filePath = StorageUtil.FileCachePath.getApkDownLoadPath(getContext().getApplicationContext(), title);
                        ArrayList<DownloadBean> list = new ArrayList<>();
                        list.add(new DownloadBean(packageName, title, StorageUtil.FileCachePath.getApkDownLoadPath(getContext().getApplicationContext(), title), downloadUrl));
                        Intent downloadIntent = new Intent(getActivity(), DownloadService.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(DownloadService.EXTRA_ACTION, DownloadService.ACTION_DOWNLOAD);
                        bundle.putParcelableArrayList(DownloadService.EXTRA_DOWNLOAD_LIST, list);
                        downloadIntent.putExtras(bundle);
                        getActivity().startService(downloadIntent);
                    }
                }).setNegativeButton(getResources().getString(R.string.str_cancle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }




}
