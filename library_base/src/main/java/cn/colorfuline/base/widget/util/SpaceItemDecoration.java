package cn.colorfuline.base.widget.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/4/12.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 上边距
     */
    private int top;
    /**
     * 下边距
     */
    private int bottom;
    /**
     * 左边距
     */
    private int left;
    /**
     * 右边距
     */
    private int right;

    /**
     *
     * @param space
     */
    public SpaceItemDecoration(int space) {
        this.top = space;
        this.bottom = space;
        this.left = space;
        this.right = space;
    }

    /**
     *
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public SpaceItemDecoration(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = top;
        outRect.bottom = bottom;
        outRect.left = left;
        outRect.right = right;
    }
}