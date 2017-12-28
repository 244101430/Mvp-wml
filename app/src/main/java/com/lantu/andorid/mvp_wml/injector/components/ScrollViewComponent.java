package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.ScreenshotModule;
import com.lantu.andorid.mvp_wml.injector.modules.ScrollViewModule;
import com.lantu.andorid.mvp_wml.ui.other.ScrollViewActivity;
import com.lantu.andorid.mvp_wml.ui.screenshot.ScreenshotActivity;

import dagger.Component;

/**
 * Created by wml on 2017/10/18.
 */
@PerActivity
@Component(modules = ScrollViewModule.class)
public interface ScrollViewComponent {
    void inject(ScrollViewActivity activity);
}
