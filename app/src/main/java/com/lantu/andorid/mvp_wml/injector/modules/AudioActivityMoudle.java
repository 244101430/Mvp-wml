package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.ui.audio.AudioPresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml on 2017/12/11.
 */
@Module
public class AudioActivityMoudle {
    private final IBaseView mView;

    public AudioActivityMoudle(IBaseView mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    protected IBasePresenter providePresenter(){
        return new AudioPresenter(mView);
    }
}
