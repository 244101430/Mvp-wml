package com.lantu.andorid.mvp_wml.injector.modules;

import android.app.Activity;

import com.lantu.andorid.mvp_wml.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml on 2017/8/31.
 */
@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @PerActivity
    @Provides
    Activity getActivity(){
        return mActivity;
    }
}
