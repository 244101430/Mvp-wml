package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.PatternockModule;
import com.lantu.andorid.mvp_wml.ui.patternlock.PatternlockActivity;

import dagger.Component;

/**
 * Created by wml8743 on 2017/10/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PatternockModule.class)
public interface PatternlockComponent {
    void inject(PatternlockActivity activity);
}
