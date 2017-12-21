package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.loacl.table.DaoSession;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;
import com.lantu.andorid.mvp_wml.ui.base.IRxBusPresenter;
import com.lantu.andorid.mvp_wml.ui.test.download.DownloadActivity;
import com.lantu.andorid.mvp_wml.ui.test.download.DownloadActivityPresenter;
import com.lantu.andorid.mvp_wml.ui.test.download.IDownloadActivityPresenter;
import com.lantu.andorid.mvp_wml.ui.test.download.IDownloadActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/12/21.
 */
@Module
public class DownloadActivityModule {
    private final IDownloadActivityView mView;

    public DownloadActivityModule(DownloadActivity mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IDownloadActivityPresenter providePresenter(DaoSession daoSession, RxBus rxBus){
        return new DownloadActivityPresenter(mView,daoSession.getVideoInfoDao(), rxBus);
    }
}
