package com.lantu.andorid.mvp_wml.injector.components;

import android.app.Activity;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.ActivityModule;

import dagger.Component;

/**
 * Created by wml8743 on 2017/8/31.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
