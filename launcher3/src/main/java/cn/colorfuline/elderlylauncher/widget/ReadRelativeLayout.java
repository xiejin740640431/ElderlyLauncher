package cn.colorfuline.elderlylauncher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/1/12.
 */

public class ReadRelativeLayout extends RelativeLayout implements View.OnClickListener {
    private OnClickListener onClickListener;
    public ReadRelativeLayout(Context context) {
        super(context);
        init();
    }

    public ReadRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReadRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     *
     */
    public void init(){
        setMOnClickListener(this);
    }


    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }

    /**
     * 设置监听
     */
    public void setMOnClickListener(OnClickListener l){
        super.setOnClickListener(l);
    }

    public void read(){
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if(view instanceof ReadTextView){
                ((ReadTextView)view).read();
            }
        }
    }

    @Override
    public void onClick(View v) {
        read();
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }
}
