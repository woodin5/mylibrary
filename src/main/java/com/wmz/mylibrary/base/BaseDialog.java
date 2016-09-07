package com.wmz.mylibrary.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

/**
 * Created by wmz on 7/7/16.
 */
public class BaseDialog extends Dialog {

    protected Context context;
    /**
     * 点击监听器
     */
    protected View.OnClickListener mLeftListener;
    protected View.OnClickListener mRightListener;

    public BaseDialog(Context context, View.OnClickListener leftListener,View.OnClickListener rightListener) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题
        this.context = context;
        this.mLeftListener = leftListener;
        this.mRightListener = rightListener;
    }

    public BaseDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void init(int resId) {
        this.setContentView(resId);
        this.getWindow().setLayout(-1, -2);
    }

    protected View findView(int id) {
        return this.findViewById(id);
    }

}
