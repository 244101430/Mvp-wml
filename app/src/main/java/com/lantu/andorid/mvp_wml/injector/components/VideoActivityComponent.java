package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.VideoActivityModule;
import com.lantu.andorid.mvp_wml.ui.video.VideoActivity;

import dagger.Component;

/**
 * Created by wml on 2017/12/11.
 */
@PerActivity
@Component(modules = VideoActivityModule.class)
public interface VideoActivityComponent {
    void inject(VideoActivity activity);
}
