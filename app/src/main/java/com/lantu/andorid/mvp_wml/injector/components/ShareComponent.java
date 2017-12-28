package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.ShareModule;
import com.lantu.andorid.mvp_wml.ui.share.ShareActivity;

import dagger.Component;

/**
 * Created by wml on 2017/10/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ShareModule.class)
public interface ShareComponent {
    void inject(ShareActivity activity);
}
