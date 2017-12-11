package com.lantu.andorid.mvp_wml.injector.components;

import android.content.Context;

import com.lantu.andorid.mvp_wml.injector.modules.ApplicationModule;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 提供全局单例数据
 * Created by wml8743 on 2017/8/31.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();
    RxBus getRxBus();
}
