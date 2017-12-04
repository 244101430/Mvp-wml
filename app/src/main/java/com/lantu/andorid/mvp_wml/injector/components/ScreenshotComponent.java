package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.ScreenshotModule;
import com.lantu.andorid.mvp_wml.ui.screenshot.ScreenshotActivity;

import dagger.Component;

/**
 * Created by wml8743 on 2017/10/18.
 */
@PerActivity
@Component(modules = ScreenshotModule.class)
public interface ScreenshotComponent {
    void inject(ScreenshotActivity activity);
}
