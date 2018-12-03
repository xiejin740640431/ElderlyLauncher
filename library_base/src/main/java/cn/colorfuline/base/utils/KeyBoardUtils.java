/**
 * Copyright © 2015 zouhuo. All rights reserved.
 *
 * @Title: KeyBoardUtils.java
 * @Prject: BangBang
 * @Package: com.bangbang.utils
 * @Description: TODO
 * @author: XJ
 * @date: 2015-8-22 下午5:03:46
 * @version: V1.0
 */
package cn.colorfuline.base.utils;

/**
 * @ClassName: KeyBoardUtils
 * @Description: 软键盘工具类
 * @author: Administrator
 * @date: 2015-8-22 下午5:03:46  
 */

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 */
public class KeyBoardUtils {
    /**
     * 打卡软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
