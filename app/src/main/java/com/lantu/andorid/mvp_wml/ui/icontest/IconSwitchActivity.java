package com.lantu.andorid.mvp_wml.ui.icontest;

import android.app.Activity;
import android.view.View;

import com.lantu.andorid.mvp_wml.AndroidApplication;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import com.lantu.andorid.mvp_wml.utils.IconUtils;

import butterknife.OnClick;

/**
 * 更换桌面图标demo
 */
public class IconSwitchActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    /**
     * 启动函数
     *
     * @param activity
     */
    public static final void launcher(Activity activity) {
        BaseSwipeBackActivity.laucher(activity, IconSwitchActivity.class);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_icon_switch;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }


    @OnClick({R.id.button, R.id.button2, R.id.button3})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                AndroidApplication.setIcon(IconUtils.ICON_TAG);
//                IconUtils.switchIcon(this, IconUtils.ICON_TAG);
                break;
            case R.id.button2:
                AndroidApplication.setIcon(IconUtils.ICON_TAG_1212);
//                IconUtils.switchIcon(this, IconUtils.ICON_TAG_1212);
                break;
            case R.id.button3:
                AndroidApplication.setIcon(IconUtils.ICON_DEFAULT);
//                IconUtils.switchIcon(this,"");
                break;
        }
    }
}
