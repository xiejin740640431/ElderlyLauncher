package cn.colorfuline.elderlylauncher.ui.firstscreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.colorfuline.elderlylauncher.R;
import cn.colorfuline.elderlylauncher.bean.InformationBean;
import cn.colorfuline.elderlylauncher.https.imageLoader.ImageLoader;
import cn.colorfuline.elderlylauncher.ui.firstscreen.interfac.OnItemClickListener;


public class InforListAdapter extends RecyclerView.Adapter<InforListAdapter.ViewHolder> {
    List<InformationBean> informationList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public InforListAdapter(List<InformationBean> informationList) {
        this.informationList = informationList;
    }

    @Override
    public InforListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(InforListAdapter.ViewHolder holder, final int position) {
        InformationBean informationBean = informationList.get(position);
        holder.tvTitle.setText(informationBean.getTitle());
        ImageLoader.getInstance().load(holder.itemView.getContext(), informationBean.getQiniuImgLink(), R.mipmap.huodong_, holder.ivTitleimg);
    }

    @Override
    public int getItemCount() {
        return informationList == null ? 0 : informationList.size();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTitleimg;
        TextView tvTitle;
        ViewHolder(View view) {
            super(view);
            tvTitle=(TextView)view.findViewById(R.id.tv_title);
            ivTitleimg=(ImageView) view.findViewById(R.id.iv_infor_titleimg);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    private String getWeekDay(String week){
        String weekDay=week;
        switch (week.toUpperCase()){
            case "SUNDAY":
                weekDay="星期日";
                break;
            case "MONDAY":
                weekDay="星期一";
                break;
            case "TUESDAY":
                weekDay="星期二";
                break;
            case "WEDNESDAY":
                weekDay="星期三";
                break;
            case "THURSDAY":
                weekDay="星期四";
                break;
            case "FRIDAY":
                weekDay="星期五";
                break;
            case "SATURDAY":
                weekDay="星期六";
                break;
        }
        return weekDay;
    }

}
