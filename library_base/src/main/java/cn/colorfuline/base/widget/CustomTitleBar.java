package cn.colorfuline.base.widget;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.colorfuline.base.R;


/**
 * Created by Administrator on 2016/4/26.
 */
public class CustomTitleBar extends AppBarLayout {
    /**
     * 返回键
     */
    private ImageButton imgBack;
    /**
     * 标题
     */
    private TextView tvTitle;
    /**
     * 右侧图片
     */
    private ImageButton imgRight;
    /**
     * 标题控件根布局
     */
    public RelativeLayout layoutTitle;
    /**
     * 右侧文本控件
     */
    private Button btnRight;

    public CustomTitleBar(Context context) {
        super(context);
        initView();
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_actionbar, this, true);
        imgBack = (ImageButton) findViewById(R.id.img_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        imgRight = (ImageButton) findViewById(R.id.img_right);
        layoutTitle = (RelativeLayout) findViewById(R.id.layout_title);
        btnRight = (Button) findViewById(R.id.btn_right);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(CharSequence title) {
        if (title != null)
            tvTitle.setText(title);
    }

    /**
     * 设置标题
     *
     * @param stringId
     */
    public void setTitle(int stringId) {
        if (stringId > 0)
            tvTitle.setText(stringId);
    }

    /**
     * 设置是否显示标题
     *
     * @param visible
     */
    public void setTitleVisible(boolean visible) {
        tvTitle.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置title颜色
     *
     * @param color
     */
    public void setTitleBarColor(int color) {
        layoutTitle.setBackgroundColor(color);
        setBackgroundColor(color);
    }

    /**
     * 设置右边textview是否显示
     *
     * @param visible
     */
    public void setTvRightVisible(boolean visible) {
        btnRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置右边Textview内容
     *
     * @param string
     * @param onClickListener
     */
    public void setRightTextViewStr(String string, OnClickListener onClickListener) {
        if (btnRight != null) {
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText(string);
            btnRight.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置返回键是否显示
     *
     * @param visible
     */
    public void setBackVisible(boolean visible) {
        if (visible) {
            imgBack.setVisibility(View.VISIBLE);
            imgBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ((Activity) getContext()).onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            imgBack.setVisibility(View.GONE);
        }
    }

    /**
     * @param resource
     */
    public void setBackImageResource(int resource) {
        imgBack.setImageResource(resource);
    }

    /**
     * 设置是否显示
     *
     * @param visible
     * @param onClickListener
     */
    public void setBackVisible(boolean visible, OnClickListener onClickListener) {
        if (visible) {
            imgBack.setVisibility(View.VISIBLE);
            imgBack.setOnClickListener(onClickListener);
        } else {
            imgBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置菜单键是否显示
     *
     * @param visible
     */
    public void setOptionVisible(boolean visible) {
        imgRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    /**
     * 设置菜单
     *
     * @param visible
     * @param onClickListener
     */
    public void setOptionVisible(boolean visible, OnClickListener onClickListener) {
        if (visible) {
            imgRight.setVisibility(View.VISIBLE);
            imgRight.setOnClickListener(onClickListener);
        } else {
            imgRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右上角菜单图片
     *
     * @param resourceId
     */
    public void setOptionImageResource(int resourceId) {
        if (resourceId > 0) {
            imgRight.setImageResource(resourceId);
            imgRight.setVisibility(View.VISIBLE);
        }
    }

    public void refreshTitleSize(){
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.default_title_text_size));
    }

}
