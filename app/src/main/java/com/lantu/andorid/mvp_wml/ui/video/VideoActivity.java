package com.lantu.andorid.mvp_wml.ui.video;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerVideoActivityComponent;
import com.lantu.andorid.mvp_wml.injector.modules.VideoActivityModule;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;

/**
 * 视频播放
 */
public class VideoActivity extends BaseSwipeBackActivity<IVideoActivityPresenter> implements IBaseView {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer mVideoPlayer;

    /**
     * 启动函数
     *
     * @param activity
     */
    public static void launcher(Activity activity) {
        activity.startActivity(new Intent(activity, VideoActivity.class));
        activity.overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeadTitle("视频");
        initVideo();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_video;
    }

    @Override
    protected void initInjector() {
        DaggerVideoActivityComponent.builder()
                .videoActivityModule(new VideoActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        DaggerVideoActivityComponent.builder()
                .videoActivityModule(new VideoActivityModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 初始化视频
     */
    private void initVideo() {
        mPresenter.initVideo(this, mVideoPlayer);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPresenter.onConfigurationChanged(this, newConfig, mVideoPlayer);
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }

}
