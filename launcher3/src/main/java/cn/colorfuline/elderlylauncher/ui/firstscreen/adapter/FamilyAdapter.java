package cn.colorfuline.elderlylauncher.ui.firstscreen.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.bean.ServiceBean;

/**
 * Created by Administrator on 2016/4/14.
 */
public class FamilyAdapter extends BaseRecyclerAdapter<FamilyAdapter.ViewHolder> {
    private List<ServiceBean> list;
    private int itemHeight;
    private RecyclerView recyclerView;

    public FamilyAdapter(RecyclerView recyclerView, List<ServiceBean> list, int itemHeight) {
        this.recyclerView = recyclerView;
        this.list = list;
        this.itemHeight = itemHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_family, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        if (itemHeight > 0) {
            viewHolder.convertView.getLayoutParams().height = itemHeight;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (position < list.size()) {
            ServiceBean bean = list.get(position);
            holder.convertView.setClickable(true);
            holder.tv.setText(bean.getName());
            if (bean.getImgId() != 0) {
                holder.img.setImageResource(bean.getImgId());
                holder.convertView.setClickable(true);
                switch (bean.getName()) {
                    case "家政":
                        holder.img.setImageResource(R.mipmap.ser_housekeeping);
                        break;
                    case "配餐":
                        holder.img.setImageResource(R.mipmap.ser_catering);
                        break;
                    case "生活用品":
                        holder.img.setImageResource(R.mipmap.ser_dailyuse);
                        break;
                    case "旅游":
                        holder.img.setImageResource(R.mipmap.ser_tourism);
                        break;
                    case "日间照料":
                        holder.img.setImageResource(R.mipmap.ser_day_care);
                        break;
                    case "直播":
                        holder.img.setImageResource(R.mipmap.ser_live);
                        break;
                }
            }
        }else{
            holder.tv.setVisibility(View.INVISIBLE);
            holder.img.setVisibility(View.INVISIBLE);
            holder.convertView.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        int spanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
        int itemCount = list == null ? 0 : list.size();
        int index=((itemCount % spanCount == 0 ? (itemCount / spanCount) : (itemCount / spanCount) + 1)) * spanCount;
        return index;
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.img_service)
        ImageView img;
        @BindView(R.id.tv_service)
        TextView tv;
        @BindView(R.id.convertView)
        RelativeLayout convertView;

        public ViewHolder(View itemView) {
            super(itemView, onItemClickListener);
            ButterKnife.bind(this, itemView);
            itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    itemHeight2 = convertView.getMeasuredHeight();
                    return true;
                }
            });
        }
    }

    private int itemHeight2;

    public int getItemHeight() {
        return itemHeight2;
    }
}
