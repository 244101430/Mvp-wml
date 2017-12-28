package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.ui.screenshot.IScreenshotPresenter;
import com.lantu.andorid.mvp_wml.ui.screenshot.IScreenshotView;
import com.lantu.andorid.mvp_wml.ui.screenshot.ScreenshotPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml on 2017/10/18.
 */
@Module
public class ScreenshotModule {
    private final IScreenshotView mView;

    public ScreenshotModule(IScreenshotView mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IScreenshotPresenter providePresenter() {
        return new ScreenshotPresenter(mView);
    }
}
