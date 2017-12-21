package com.lantu.andorid.mvp_wml.ui.test.download;

import com.lantu.andorid.mvp_wml.DownloaderWrapper;
import com.lantu.andorid.mvp_wml.loacl.table.VideoInfo;
import com.lantu.andorid.mvp_wml.loacl.table.VideoInfoDao;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;
import com.lantu.andorid.mvp_wml.ui.base.IRxBusPresenter;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by wml8743 on 2017/12/21.
 */

public class DownloadActivityPresenter implements IDownloadActivityPresenter{

    private final IBaseView mView;
    private final VideoInfoDao mDbDao;
    private final RxBus mRxBus;

    protected VideoInfo info;

    public DownloadActivityPresenter(IDownloadActivityView mView, VideoInfoDao mDbDao, RxBus mRxBus) {
        this.mView = mView;
        this.mRxBus = mRxBus;
        this.mDbDao = mDbDao;

        info = new VideoInfo();
        info.setVid("123");
        info.setTitle("title");
        info.setVideoUrl("http://flv2.bn.netease.com/videolib3/1501/28/wlncJ2098/SD/wlncJ2098-mobile.mp4");
    }


    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = mRxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        mRxBus.addSubscription(this, subscription);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }

    @Override
    public void download_start() {
        DownloaderWrapper.start(info);
    }

    @Override
    public void download_pause() {
        DownloaderWrapper.stop(info);
    }

    @Override
    public void download_cancel() {
        DownloaderWrapper.cancel(info);
    }
}
