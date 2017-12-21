package com.lantu.andorid.mvp_wml.ui.test.download;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.dl7.downloaderlib.entity.FileInfo;
import com.dl7.downloaderlib.model.DownloadStatus;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerDownloadActivityComponent;
import com.lantu.andorid.mvp_wml.injector.modules.DownloadActivityModule;
import com.lantu.andorid.mvp_wml.rxbus.event.VideoEvent;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;
import com.lantu.andorid.mvp_wml.ui.base.IRxBusPresenter;
import com.lantu.andorid.mvp_wml.utils.Logger;
import com.lantu.andorid.mvp_wml.widget.LrcSeekBar;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 下载测试
 */
public class DownloadActivity extends BaseSwipeBackActivity<IDownloadActivityPresenter> implements IDownloadActivityView, View.OnClickListener {

    @BindView(R.id.download_start)
    protected Button download_start;
    @BindView(R.id.download_pause)
    protected Button download_pause;
    @BindView(R.id.download_cancel)
    protected Button download_cancel;
    @BindView(R.id.lrcSeekBar)
    protected LrcSeekBar lrcSeekBar;

    public static final void launcher(Activity activity){
        BaseSwipeBackActivity.laucher(activity, DownloadActivity.class);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_download;
    }

    @Override
    protected void initInjector() {
        DaggerDownloadActivityComponent.builder()
                .applicationComponent(getAppComponent())
                .downloadActivityModule(new DownloadActivityModule(this))
                .build()
                .injector(this);
    }

    @Override
    protected void initViews() {
        mPresenter.registerRxBus(FileInfo.class, new Action1<FileInfo>() {
            @Override
            public void call(FileInfo info) {
                switch (info.getStatus()){
                    case DownloadStatus.NORMAL:
                        Logger.e("DownloadStatus.NORMAL");
                        break;
                    case DownloadStatus.WAIT:
                        Logger.e("DownloadStatus.WAIT");
                        break;
                    case DownloadStatus.START:
                        Logger.e("DownloadStatus.START");

                        break;
                    case DownloadStatus.DOWNLOADING:
                        Logger.e("DownloadStatus.DOWNLOADING");
                        download_seekbar((info.getLoadBytes()* 100)/info.getTotalBytes() );
                        break;
                    case DownloadStatus.STOP:
                        Logger.e("DownloadStatus.STOP");
                        break;
                    case DownloadStatus.ERROR:
                        Logger.e("DownloadStatus.ERROR");
                        break;
                    case DownloadStatus.COMPLETE:
                        Logger.e("DownloadStatus.COMPLETE");
                        break;
                    case DownloadStatus.CANCEL:
                        Logger.e("DownloadStatus.CANCEL");
                        break;
                }
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @OnClick({R.id.download_start, R.id.download_pause, R.id.download_cancel})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download_start:
                mPresenter.download_start();
                break;
            case R.id.download_pause:
                mPresenter.download_pause();

                break;
            case R.id.download_cancel:
                mPresenter.download_cancel();
                download_cancel();
                break;
        }
    }

    @Override
    public void download_start() {

    }

    @Override
    public void download_pause() {

    }

    @Override
    public void download_cancel() {
        lrcSeekBar.setProgress(0);
    }

    @Override
    public void download_seekbar(long progress) {
        lrcSeekBar.setProgress((int) progress);
    }
}
