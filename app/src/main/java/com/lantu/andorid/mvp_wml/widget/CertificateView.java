package com.lantu.andorid.mvp_wml.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.utils.ShotUtil;

import butterknife.BindView;

/**
 * Created by wml8743 on 2017/10/18.
 */

public class CertificateView extends FrameLayout{

    private ScrollView scrollView;

    public CertificateView(@NonNull Context context) {
        super(context);
        initView();
    }

    public CertificateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CertificateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        addView(View.inflate(getContext(), R.layout.layout_certificate, null));
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    /**
     * 获取截图
     * @return
     */
    public Bitmap shotScrollView(){
        return ShotUtil.shotScrollView(scrollView);
    }

}
