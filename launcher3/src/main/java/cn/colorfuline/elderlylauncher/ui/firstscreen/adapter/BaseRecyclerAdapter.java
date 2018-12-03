package cn.colorfuline.elderlylauncher.ui.firstscreen.adapter;

import android.support.v7.widget.RecyclerView;

import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemClickListener;
import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemLongClickListener;


/**
 * Created by Administrator on 2016/4/13.
 */
public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
