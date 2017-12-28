package com.lantu.andorid.mvp_wml.injector.modules;

import android.content.Context;

import com.lantu.andorid.mvp_wml.AndroidApplication;
import com.lantu.andorid.mvp_wml.loacl.table.DaoSession;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml on 2017/8/31.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication mApplication;
    private final DaoSession mDaoSession;
    private final RxBus mRxBus;

    public ApplicationModule(AndroidApplication mApplication,DaoSession daoSession, RxBus mRxBus) {
        this.mApplication = mApplication;
        this.mDaoSession = daoSession;
        this.mRxBus = mRxBus;
    }


    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication.getApplication();
    }

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return mRxBus;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        return mDaoSession;
    }
}
