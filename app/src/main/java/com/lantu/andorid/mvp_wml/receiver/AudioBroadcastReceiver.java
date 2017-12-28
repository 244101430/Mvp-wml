package com.lantu.andorid.mvp_wml.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.lantu.andorid.mvp_wml.utils.ToastUtils;

import java.util.Date;

/**
 * 音频广播
 * Created by wml on 2017/12/14.
 */

public class AudioBroadcastReceiver {

    /**
     * 是否注册成功
     */
    private boolean isRegisterSuccess = false;
    private Context mContext;

    /**
     * 注册成功广播
     */
    private String ACTION_MUSICSUCCESS = "com.zlm.hp.music.success_" + new Date().getTime();
    //空音乐
    public static final String ACTION_NULLMUSIC = "com.zlm.hp.null.music";
    //添加音乐
    public static final String ACTION_ADDMUSIC = "com.zlm.hp.add.music";
    //初始化音乐
    public static final String ACTION_INITMUSIC = "com.zlm.hp.init.music";
    //点击播放音乐
    public static final String ACTION_PLAYMUSIC = "com.zlm.hp.play.music";
    //继续播放
    public static final String ACTION_RESUMEMUSIC = "com.zlm.hp.resume.music";
    //点击暂停播放
    public static final String ACTION_PAUSEMUSIC = "com.zlm.hp.pause.music";
    //点击音乐快进
    public static final String ACTION_SEEKTOMUSIC = "com.zlm.hp.seekto.music";
    //点击上一首
    public static final String ACTION_PREMUSIC = "com.zlm.hp.pre.music";
    //点击下一首
    public static final String ACTION_NEXTMUSIC = "com.zlm.hp.next.music";

    //播放器开始播放
    public static final String ACTION_SERVICE_PLAYMUSIC = "com.zlm.hp.service.play.music";
    //播放器暂停
    public static final String ACTION_SERVICE_PAUSEMUSIC = "com.zlm.hp.service.pause.music";
    //播放器唤醒
    public static final String ACTION_SERVICE_RESUMEMUSIC = "com.zlm.hp.service.resume.music";
    //播放器播放中
    public static final String ACTION_SERVICE_PLAYINGMUSIC = "com.zlm.hp.service.playing.music";
    //播放错误
    public static final String ACTION_SERVICE_PLAYERRORMUSIC = "com.zlm.hp.service.playerror.music";


    //
    public static final String ACTION_SINGERPICLOADED = "com.zlm.hp.singerpic.loaded";


    private BroadcastReceiver mAudioBroadcastReceiver;
    private IntentFilter mAudioIntentFilter;
    private AudioReceiverListener mAudioReceiverListener;

    public AudioBroadcastReceiver(Context context) {
        this.mContext = context;
        mAudioIntentFilter = new IntentFilter();
        //
        mAudioIntentFilter.addAction(ACTION_MUSICSUCCESS);
        mAudioIntentFilter.addAction(ACTION_NULLMUSIC);
        // mAudioIntentFilter.addAction(ACTION_MUSICRESTART);
        mAudioIntentFilter.addAction(ACTION_ADDMUSIC);
        mAudioIntentFilter.addAction(ACTION_INITMUSIC);
        mAudioIntentFilter.addAction(ACTION_PLAYMUSIC);
        mAudioIntentFilter.addAction(ACTION_RESUMEMUSIC);
        mAudioIntentFilter.addAction(ACTION_PAUSEMUSIC);
        mAudioIntentFilter.addAction(ACTION_SEEKTOMUSIC);
        mAudioIntentFilter.addAction(ACTION_PREMUSIC);
        mAudioIntentFilter.addAction(ACTION_NEXTMUSIC);

        mAudioIntentFilter.addAction(ACTION_SERVICE_PLAYMUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_PAUSEMUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_RESUMEMUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_PLAYINGMUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_PLAYERRORMUSIC);

    }

    private Handler mAudioHanddler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mAudioReceiverListener != null) {
                Intent intent = (Intent) msg.obj;
                if (intent.getAction().equals(ACTION_MUSICSUCCESS)) { // 注册广播成功
                    isRegisterSuccess = true;
                    ToastUtils.showToast("注册广播成功");
                } else {
                    mAudioReceiverListener.onReceive(mContext, intent);
                }
            }

        }
    };

    /**
     * 注册广播
     *
     * @param context
     */
    public void registerReceiver(Context context) {
        if (mAudioBroadcastReceiver == null) {
            mAudioBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Message msg = new Message();
                    msg.obj = intent;
                    mAudioHanddler.sendMessage(msg);
                }
            };
            mContext.registerReceiver(mAudioBroadcastReceiver, mAudioIntentFilter);
            // 发送注册成功广播
            Intent successIntent = new Intent(ACTION_MUSICSUCCESS);
            successIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            mContext.sendBroadcast(successIntent);
        }
    }

    /**
     * 取消广播
     *
     * @param context
     */
    public void unregisterReceiver(Context context) {
        if (mAudioBroadcastReceiver != null && isRegisterSuccess) {
            mContext.unregisterReceiver(mAudioBroadcastReceiver);
        }
    }

    public void setAudioReceiverListener(AudioReceiverListener audioReceiverListener) {
        this.mAudioReceiverListener = audioReceiverListener;
    }

    public interface AudioReceiverListener {
        void onReceive(Context context, Intent intent);
    }
}
