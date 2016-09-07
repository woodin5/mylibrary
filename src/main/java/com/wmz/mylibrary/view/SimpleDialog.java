package com.wmz.mylibrary.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wmz.mylibrary.R;
import com.wmz.mylibrary.base.BaseDialog;

/**
 * Created by wmz on 7/7/16.
 */
public class SimpleDialog extends BaseDialog{

    private TextView mTvMsg;
    private Button mBtnLeft;
    private Button mBtnright;


    public SimpleDialog(Context context, View.OnClickListener leftListener,View.OnClickListener rightListener){
        super(context,leftListener,rightListener);
        init(R.layout.layout_dialog_simple);
        initView();
    }

    private void initView() {
        mTvMsg = (TextView) findView(R.id.tv_dialog_simple_msg);
        mBtnLeft = (Button) findViewById(R.id.btn_dialog_simple_left);
        mBtnright = (Button) findViewById(R.id.btn_dialog_simple_right);

        mBtnLeft.setOnClickListener(mLeftListener);
        mBtnright.setOnClickListener(mRightListener);
    }

    public void setMsg(String msg){
        mTvMsg.setText(msg);
    }

    public void setLeftBackground(int resId){
        mBtnLeft.setBackgroundResource(resId);
    }
    public void setRightBackground(int resId){
        mBtnright.setBackgroundResource(resId);
    }
}
