package com.lantu.andorid.mvp_wml.ui.video;

import android.app.Activity;
import android.content.res.Configuration;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by wml8743 on 2017/12/11.
 */

public interface IVideoActivityPresenter extends IBasePresenter{
    /**
     * 初始播放控件
     */
    void initVideo(Activity activity, StandardGSYVideoPlayer mVideoPlayer);

    /**
     * 旋转控制
     * @param newConfig
     * @param mVideoPlayer 播放控件
     */
    void onConfigurationChanged(Activity activity, Configuration newConfig, StandardGSYVideoPlayer mVideoPlayer);

    /**
     * 返回
     */
    void onBackPressed(Activity activity);

    /**
     * 页面暂停
     */
    void onPause();

    /**
     * 页面展示
     */
    void onResume();

    /**
     * 页面销毁
     */
    void onDestory();
}
