package com.lantu.andorid.mvp_wml.injector.modules;

import android.content.Context;

import com.lantu.andorid.mvp_wml.AndroidApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/8/31.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication mApplication;

    public ApplicationModule(AndroidApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication.getApplication();
    }
}
