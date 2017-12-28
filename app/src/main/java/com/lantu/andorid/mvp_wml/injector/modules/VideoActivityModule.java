package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;
import com.lantu.andorid.mvp_wml.ui.video.IVideoActivityPresenter;
import com.lantu.andorid.mvp_wml.ui.video.VideoActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml on 2017/12/11.
 */

@Module
public class VideoActivityModule {
    private final IBaseView mView;

    public VideoActivityModule(IBaseView mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IVideoActivityPresenter providePersenter(){
        return new VideoActivityPresenter(mView);
    }
}
