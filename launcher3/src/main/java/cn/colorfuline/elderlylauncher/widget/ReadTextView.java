package cn.colorfuline.elderlylauncher.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.colorfuline.elderlylauncher.services.ReadScreenService;


/**
 * Created by Administrator on 2017/1/12.
 */

public class ReadTextView extends TextView {
    public ReadTextView(Context context) {
        super(context);
    }

    public ReadTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void read(){
        Intent intent = new Intent(ReadScreenService.ACTION_SPEAK);
        intent.putExtra(ReadScreenService.EXTRA_CONTENT, getText().toString());
        getContext().sendBroadcast(intent);
    }

}
