/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;

import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.elderlylauncher.R;

public class ShortcutAndWidgetContainer extends ViewGroup {
    static final String TAG = "CellLayoutChildren";

    // These are temporary variables to prevent having to allocate a new object just to
    // return an (x, y) value from helper functions. Do NOT use them to maintain other state.
    private final int[] mTmpCellXY = new int[2];

    private final WallpaperManager mWallpaperManager;

    private boolean mIsHotseatLayout;

    private int mCellWidth;
    private int mCellHeight;

    private int mWidthGap;
    private int mHeightGap;

    private int mCountX;
    private int mCountY;

    private Launcher mLauncher;

    private boolean mInvertIfRtl = false;

    private boolean canvasLine = true;

    public void setCanvasLine(boolean canvasLine) {
        this.canvasLine = canvasLine;
    }

    public ShortcutAndWidgetContainer(Context context) {
        super(context);
        mLauncher = (Launcher) context;
        mWallpaperManager = WallpaperManager.getInstance(context);
    }

    public void setCellDimensions(int cellWidth, int cellHeight, int widthGap, int heightGap,
                                  int countX, int countY) {
        mCellWidth = cellWidth;
        mCellHeight = cellHeight;
        mWidthGap = widthGap;
        mHeightGap = heightGap;
        mCountX = countX;
        mCountY = countY;
    }

    public View getChildAt(int x, int y) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child.getLayoutParams();

            if ((lp.cellX <= x) && (x < lp.cellX + lp.cellHSpan) &&
                    (lp.cellY <= y) && (y < lp.cellY + lp.cellVSpan)) {
                return child;
            }
        }
        return null;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        @SuppressWarnings("all") // suppress dead code warning
        final boolean debug = true;
        if (debug) {
            // Debug drawing for hit space
//            Paint p = new Paint();
//            p.setColor(0x6600FF00);
//            for (int i = getChildCount() - 1; i >= 0; i--) {
//                final View child = getChildAt(i);
//                final CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child.getLayoutParams();
//                canvas.drawRect(lp.x, lp.y, lp.x + lp.width, lp.y + lp.height, p);
//            }
            if (canvasLine) {
                Paint linePaint = new Paint();
                linePaint.setStrokeWidth(5);
                linePaint.setColor(getResources().getColor(R.color.app_item_line));
                int viewHeight = getMeasuredHeight();
                int viewWidth = getMeasuredWidth();
                //第一条和最后一条线，偏移值
                int num = 3;
                canvas.drawLine(0, num, viewWidth, num, linePaint);
                canvas.drawLine(0, 0, 0, viewHeight, linePaint);
                float viewHeight3 = getMeasuredHeight() / 3;
                float viewWidth3 = getMeasuredWidth() / 3;
                for (int i = 0; i < mCountY; i++) {
                    for (int j = 0; j < mCountX; j++) {
                        View view = getChildAt(j, i);
                        int hSpan = 0;
                        int vSpan = 0;
                        if (view != null) {
                            CellLayout.LayoutParams lp = (CellLayout.LayoutParams) view.getLayoutParams();
                            hSpan = lp.cellHSpan;
                            vSpan = lp.cellVSpan;
                        }

                        // 每格 横线
                        float hLineStartX = viewWidth3 * j;
                        float hLineStartY = viewHeight3 * (i + 1);
                        if (i == mCountY - 1) {
                            hLineStartY -= num;
                        }
                        float hLineStopX = hLineStartX + viewWidth3;
                        float hLineStopY = hLineStartY;
                        canvas.drawLine(hLineStartX, hLineStartY, hLineStopX, hLineStopY, linePaint);

                        // 每格 竖线
                        if (hSpan <= 1 || (j + 1) == hSpan) {
                            float vLineStartX = viewWidth3 * (j + 1);
                            float vLineStartY = viewHeight3 * i;
                            if (i == 0) {
                                vLineStartY += num;
                            }
                            float vLineStopX = vLineStartX;
                            float vLineStopY = vLineStartY + viewHeight3;
                            canvas.drawLine(vLineStartX, vLineStartY, vLineStopX, vLineStopY, linePaint);
                        }
                    }
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSpecSize, heightSpecSize);

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChild(child);
            }
        }
    }

    public void setupLp(CellLayout.LayoutParams lp) {
        lp.setup(mCellWidth, mCellHeight, mWidthGap, mHeightGap, invertLayoutHorizontally(),
                mCountX);
    }

    // Set whether or not to invert the layout horizontally if the layout is in RTL mode.
    public void setInvertIfRtl(boolean invert) {
        mInvertIfRtl = invert;
    }

    public void setIsHotseat(boolean isHotseat) {
        mIsHotseatLayout = isHotseat;
        canvasLine = !isHotseat;
    }


    int getCellContentWidth() {
        final DeviceProfile grid = mLauncher.getDeviceProfile();
        return Math.min(getMeasuredHeight(), mIsHotseatLayout ?
                grid.hotseatCellWidthPx : grid.cellWidthPx);
    }

    int getCellContentHeight() {
        final DeviceProfile grid = mLauncher.getDeviceProfile();
        return Math.min(getMeasuredHeight(), mIsHotseatLayout ?
                grid.hotseatCellHeightPx : grid.cellHeightPx);
    }

    public void measureChild(View child) {
        final DeviceProfile grid = mLauncher.getDeviceProfile();
        final int cellWidth = mCellWidth;
        final int cellHeight = mCellHeight;
        CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child.getLayoutParams();
        if (!lp.isFullscreen) {
            lp.setup(cellWidth, cellHeight, mWidthGap, mHeightGap, invertLayoutHorizontally(),
                    mCountX);

            if (child instanceof LauncherAppWidgetHostView) {
                // Widgets have their own padding, so skip
            } else {
                // Otherwise, center the icon
                int cHeight = getCellContentHeight();
                int cellPaddingY = (int) Math.max(0, ((lp.height - cHeight) / 2f));
                int cellPaddingX = (int) (grid.edgeMarginPx / 2f);
                child.setPadding(cellPaddingX, cellPaddingY, cellPaddingX, 0);
            }
        } else {
            lp.x = 0;
            lp.y = 0;
            lp.width = getMeasuredWidth();
            lp.height = getMeasuredHeight();
        }
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
        int childheightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.height,
                MeasureSpec.EXACTLY);
        child.measure(childWidthMeasureSpec, childheightMeasureSpec);
    }

    public boolean invertLayoutHorizontally() {
        return mInvertIfRtl && Utilities.isRtl(getResources());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                CellLayout.LayoutParams lp = (CellLayout.LayoutParams) child.getLayoutParams();
                int childLeft = lp.x;
                int childTop = lp.y;
                child.layout(childLeft, childTop, childLeft + lp.width, childTop + lp.height);

                if (lp.dropped) {
                    lp.dropped = false;

                    final int[] cellXY = mTmpCellXY;
                    getLocationOnScreen(cellXY);
                    mWallpaperManager.sendWallpaperCommand(getWindowToken(),
                            WallpaperManager.COMMAND_DROP,
                            cellXY[0] + childLeft + lp.width / 2,
                            cellXY[1] + childTop + lp.height / 2, 0, null);
                }
            }
        }
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if (child != null) {
            Rect r = new Rect();
            child.getDrawingRect(r);
            requestRectangleOnScreen(r);
        }
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();

        // Cancel long press for all children
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            child.cancelLongPress();
        }
    }

    @Override
    protected void setChildrenDrawingCacheEnabled(boolean enabled) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = getChildAt(i);
            view.setDrawingCacheEnabled(enabled);
            // Update the drawing caches
            if (!view.isHardwareAccelerated() && enabled) {
                view.buildDrawingCache(true);
            }
        }
    }

    @Override
    protected void setChildrenDrawnWithCacheEnabled(boolean enabled) {
        super.setChildrenDrawnWithCacheEnabled(enabled);
    }
}
