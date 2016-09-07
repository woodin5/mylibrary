package com.wmz.mylibrary.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmz.mylibrary.R;
import com.wmz.mylibrary.base.BaseDialog;

/**
 * Created by xiaoyunfei on 2015/11/13.
 */
public class LoadingDialog extends BaseDialog {
    private ImageView progressImg;
    private TextView showText;
    private AnimationDrawable drawable;
    private String showTextString = null;

    public LoadingDialog(Context context) {
        super(context);
        init(R.layout.layout_dialog_loading);
        getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        assignViews();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        init(R.layout.layout_dialog_loading);
        getWindow().setLayout(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        assignViews();
    }

    private void assignViews() {
        progressImg = (ImageView) findView(R.id.progress_img);
        showText = (TextView) findView(R.id.showTextTv);
        drawable = (AnimationDrawable) progressImg.getBackground();
        drawable.start();
    }

    public void setShowText(String text) {
        showTextString = text;
    }

    @Override
    public void show() {
        super.show();
        if (showTextString != null || !"".equals(showTextString)) {
            if (showText != null) {
                showText.setText(showTextString);
                showText.setVisibility(View.VISIBLE);
            }
        }

    }
}
