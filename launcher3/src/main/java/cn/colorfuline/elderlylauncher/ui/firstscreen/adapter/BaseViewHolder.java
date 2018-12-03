package cn.colorfuline.elderlylauncher.ui.firstscreen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemClickListener;
import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemLongClickListener;


/**
 * Created by Administrator on 2016/4/13.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    public BaseViewHolder(View view){
        super(view);
        view.setFocusable(true);
    }
    public BaseViewHolder(View view, OnItemClickListener onItemClickListener) {
        super(view);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(BaseViewHolder.this.onItemClickListener!=null)
                    BaseViewHolder.this.onItemClickListener.onItemClick(v,getLayoutPosition());
            }
        });
    }

    /**
     *
     * @param view
     * @param onItemLongClickListener
     */
    public BaseViewHolder(View view, OnItemLongClickListener onItemLongClickListener) {
        super(view);
        this.onItemLongClickListener = onItemLongClickListener;
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(BaseViewHolder.this.onItemLongClickListener!=null)
                    BaseViewHolder.this.onItemLongClickListener.onItemLongClick(v,getLayoutPosition());
                return true;
            }
        });
    }
    /**
     *
     * @param view
     * @param onItemLongClickListener
     */
    public BaseViewHolder(View view, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        super(view);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(BaseViewHolder.this.onItemClickListener!=null)
                    BaseViewHolder.this.onItemClickListener.onItemClick(v,getLayoutPosition());
            }
        });
        this.onItemLongClickListener = onItemLongClickListener;
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(BaseViewHolder.this.onItemLongClickListener!=null)
                    BaseViewHolder.this.onItemLongClickListener.onItemLongClick(v,getLayoutPosition());
                return true;
            }
        });
    }
}
