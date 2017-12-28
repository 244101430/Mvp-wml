package com.lantu.andorid.mvp_wml.injector.components;


import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.DownloadActivityModule;
import com.lantu.andorid.mvp_wml.ui.test.download.DownloadActivity;

import dagger.Component;

/**
 * Created by wml on 2017/12/21.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = DownloadActivityModule.class)
public interface DownloadActivityComponent {
    void injector(DownloadActivity activity);
}
