package com.lantu.andorid.mvp_wml.ui.patternlock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerPatternlockComponent;
import com.lantu.andorid.mvp_wml.injector.modules.PatternockModule;
import com.lantu.andorid.mvp_wml.ui.base.BaseActivity;
import com.lantu.andorid.mvp_wml.ui.home.MainActivity;
import com.lantu.andorid.mvp_wml.utils.Logger;

import java.util.List;

import butterknife.BindView;

public class PatternlockActivity extends BaseActivity<IPatternockPresenter> implements IPatternlockView, PatternLockViewListener {

    @BindView(R.id.pattern_lock_view)
    protected PatternLockView pattern_lock_view;

    /**
     * 启动函数
     * @param activity
     */
    public static void laucher(BaseActivity activity) {
        activity.startActivity(new Intent(activity, PatternlockActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initSystemBarColor(Color.GRAY);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_patternlock;
    }

    @Override
    protected void initInjector() {
        DaggerPatternlockComponent.builder()
                .applicationComponent(getAppComponent())
                .patternockModule(new PatternockModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        pattern_lock_view.addPatternLockListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void cleanPatternLockView() {
        pattern_lock_view.clearPattern();
    }

    @Override
    public void setPatternLockViewMode(int mode) {
        pattern_lock_view.setViewMode(mode);
    }

    @Override
    public void launcherMainActivity() {
        MainActivity.laucher(this);
    }

    @Override
    public void onStarted() {
        Logger.i("Pattern drawing started");
    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {
        Logger.i("Pattern progress: " + PatternLockUtils.patternToString(pattern_lock_view, progressPattern));
    }


    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        String value = PatternLockUtils.patternToString(pattern_lock_view, pattern);
        Logger.i("Pattern complete: " + value);
        mPresenter.setPassword(value);

    }

    @Override
    public void onCleared() {
        Logger.i("Pattern has been cleared");
    }
}
