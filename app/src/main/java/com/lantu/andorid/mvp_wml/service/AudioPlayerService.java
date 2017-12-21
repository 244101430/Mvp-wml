package com.lantu.andorid.mvp_wml.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RemoteViews;

import com.lantu.andorid.mvp_wml.AndroidApplication;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.receiver.AudioBroadcastReceiver;
import com.lantu.andorid.mvp_wml.receiver.NotificationReceiver;
import com.lantu.andorid.mvp_wml.ui.audio.AudioInfo;
import com.lantu.andorid.mvp_wml.ui.audio.AudioMessage;
import com.lantu.andorid.mvp_wml.ui.audio.AudioPlayerManager;
import com.lantu.andorid.mvp_wml.ui.home.MainActivity;
import com.lantu.andorid.mvp_wml.utils.Logger;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by wml8743 on 2017/12/14.
 */

public class AudioPlayerService extends Service {

    /**
     * 播放器
     */
    private IjkMediaPlayer mMediaPlayer;

    /**
     * 播放线程
     */
    private Thread mPlayerThread = null;

    /**
     * 音频广播
     */
    private AudioBroadcastReceiver mAudioBroadcastReceiver;
    /**
     * 广播监听
     */
    private AudioBroadcastReceiver.AudioReceiverListener mAudioReceiverListener = new AudioBroadcastReceiver.AudioReceiverListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doAudioReceive(context, intent);
        }
    };
    /**
     * 是否正在快进
     */
    private boolean isSeekTo = false;

    ///////////////////////////////通知栏//////////////////////////////
    public static final int mNotificationPlayBarId = 19900420;

    private NotificationManager notificationManager;// 获取通知栏系统服务对象

    /**
     * 状态栏播放试图
     */
    private RemoteViews mNotifyPlayBarRemoteViews;
    /**
     *
     */
    private Notification mPlayBarNotification;

    /**
     *
     */
    private NotificationReceiver mNotificationReceiver;

    /**
     *
     */
    private NotificationReceiver.NotificationReceiverListener mNotificationReceiverListener = new NotificationReceiver.NotificationReceiverListener() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Message msg = new Message();
            msg.obj = intent;
            msg.what = 1;
            mNotificationHandler.sendMessage(msg);


        }
    };
    private Handler mNotificationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = (Intent) msg.obj;
            if (msg.what == 1) {
                doNotificationReceive(getApplicationContext(), intent);
            } else {
                doNotification(getApplicationContext(), intent);
            }

        }
    };
    private AudioPlayerManager audioPlayerManager;

    @Override
    public void onCreate() {
        super.onCreate();
        // 注册接收音频播放广播
        mAudioBroadcastReceiver = new AudioBroadcastReceiver(getApplicationContext());
        mAudioBroadcastReceiver.setAudioReceiverListener(mAudioReceiverListener);
        mAudioBroadcastReceiver.registerReceiver(getApplicationContext());

        // 注册通知栏广播
        mNotificationReceiver = new NotificationReceiver(getApplicationContext());
        mNotificationReceiver.setNotificationReceiverListener(mNotificationReceiverListener);
        mNotificationReceiver.registerReceiver(getApplicationContext());

        audioPlayerManager = AndroidApplication.getAudioPlayerManager();

        Logger.i("音频播放服务启动");
        //初始化通知栏
        initNotificationView();

    }

    /**
     * 初始化通知栏
     */
    private void initNotificationView() {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);// 获取通知栏系统服务对象
        int icon = R.mipmap.ic_launcher;
        CharSequence tickerText = "tickerText";
        long when = System.currentTimeMillis();
        mPlayBarNotification = new Notification(icon, tickerText, when);
        // FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉
        // FLAG_NO_CLEAR 该通知不能被状态栏的清除按钮给清除掉
        // FLAG_ONGOING_EVENT 通知放置在正在运行
        // FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
        mPlayBarNotification.flags |= Notification.FLAG_ONGOING_EVENT;

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mPlayBarNotification.contentIntent = pendingIntent;
        mNotifyPlayBarRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notify_playbar);

        AudioInfo curAudioInfo = audioPlayerManager.getCurAudioInfo();
        if (curAudioInfo != null) {
            Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INITMUSIC);
            doNotificationReceive(getApplicationContext(), initIntent);
        } else {
            Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULLMUSIC);
            doNotificationReceive(getApplicationContext(), nullIntent);
        }
    }

    /**
     * 处理通知栏广播
     *
     * @param context
     * @param intent
     */
    private void doNotificationReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case NotificationReceiver.NOTIFIATION_APP_PLAYMUSIC://通知栏app播放歌曲
                int playStatus = audioPlayerManager.getPlayStatus();
                if (playStatus == AudioPlayerManager.PAUSE) {
                    AudioInfo audioInfo = audioPlayerManager.getCurAudioInfo();
                    if (audioInfo != null) {
                        AudioMessage audioMessage = audioPlayerManager.getCurAudioMessage();
                        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_RESUMEMUSIC);
                        resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        sendBroadcast(resumeIntent);
                    }
                } else {
                    if (audioPlayerManager.getCurAudioMessage() != null) {
                        AudioMessage audioMessage = audioPlayerManager.getCurAudioMessage();
                        AudioInfo audioInfo = audioPlayerManager.getCurAudioInfo();
                        if (audioInfo != null) {
                            audioMessage.setAudioInfo(audioInfo);
                            Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_PLAYMUSIC);
                            resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                            resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            sendBroadcast(resumeIntent);
                        }
                    }
                }
                break;
            case NotificationReceiver.NOTIFIATION_APP_PAUSEMUSIC://通知栏app暂停歌曲
                int playStatus1 = audioPlayerManager.getPlayStatus();
                if (playStatus1 == AudioPlayerManager.PLAYING) {
                    Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_PAUSEMUSIC);
                    resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(resumeIntent);
                }
                break;
            case NotificationReceiver.NOTIFIATION_APP_NEXTMUSIC://通知栏app下一首歌曲
                Intent nextIntent = new Intent(AudioBroadcastReceiver.ACTION_NEXTMUSIC);
                nextIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(nextIntent);
                break;
            case NotificationReceiver.NOTIFIATION_APP_PREMUSIC:// 通知栏APP上一首
                Intent preIntent = new Intent(AudioBroadcastReceiver.ACTION_PREMUSIC);
                preIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(preIntent);
                break;
        }
    }

    /**
     * 处理通知栏视图
     *
     * @param context
     * @param intent
     */
    private void doNotification(Context context, Intent intent) {
        Intent buttonplayIntent = new Intent(NotificationReceiver.NOTIFIATION_APP_PLAYMUSIC);
        PendingIntent pendplayButtonIntent = PendingIntent.getBroadcast(AudioPlayerService.this, 0, buttonplayIntent, 0);
        mNotifyPlayBarRemoteViews.setOnClickPendingIntent(R.id.play, pendplayButtonIntent);

        Intent buttonpauseIntent = new Intent(NotificationReceiver.NOTIFIATION_APP_PAUSEMUSIC);
        PendingIntent pendpauseButtonIntent = PendingIntent.getBroadcast(AudioPlayerService.this, 0, buttonpauseIntent, 0);

        Intent buttonnextIntent = new Intent(NotificationReceiver.NOTIFIATION_APP_NEXTMUSIC);
        PendingIntent pendnextButtonIntent = PendingIntent.getBroadcast(AudioPlayerService.this, 0, buttonnextIntent, 0);

        mNotifyPlayBarRemoteViews.setOnClickPendingIntent(R.id.next, pendnextButtonIntent);

        Intent buttonprewtIntent = new Intent(NotificationReceiver.NOTIFIATION_APP_PREMUSIC);
        PendingIntent pendprewButtonIntent = PendingIntent.getBroadcast(AudioPlayerService.this, 0, buttonprewtIntent, 0);

        mNotifyPlayBarRemoteViews.setOnClickPendingIntent(R.id.prew, pendprewButtonIntent);

        String action = intent.getAction();
        if (AudioBroadcastReceiver.ACTION_NULLMUSIC.equals(action)) {//空音乐
            mNotifyPlayBarRemoteViews.setImageViewResource(R.id.singPic, R.mipmap.ic_launcher);// 显示专辑封面图片

            mNotifyPlayBarRemoteViews.setTextViewText(R.id.titleName, "testTItle");
            mNotifyPlayBarRemoteViews.setViewVisibility(R.id.play, View.VISIBLE);
            mNotifyPlayBarRemoteViews.setViewVisibility(R.id.pause, View.INVISIBLE);
        } else {
            AudioInfo curAudioInfo = audioPlayerManager.getCurAudioInfo();

            switch (action) {
                case AudioBroadcastReceiver.ACTION_INITMUSIC:
                    String titleName = curAudioInfo.getSongName() + " - " + curAudioInfo.getSongName();
                    mNotifyPlayBarRemoteViews.setTextViewText(R.id.titleName, titleName);
                    mNotifyPlayBarRemoteViews.setViewVisibility(R.id.play, View.VISIBLE);
                    mNotifyPlayBarRemoteViews.setViewVisibility(R.id.pause, View.INVISIBLE);
                    mNotifyPlayBarRemoteViews.setOnClickPendingIntent(R.id.play, pendplayButtonIntent);
                    break;
                case AudioBroadcastReceiver.ACTION_SINGERPICLOADED: // 显示专辑封面
                    Bitmap bm = null;
                    if (bm != null) {
                        mNotifyPlayBarRemoteViews.setImageViewBitmap(R.id.singPic, bm);// 显示专辑封面图片
                    } else {
                        mNotifyPlayBarRemoteViews.setImageViewResource(R.id.singPic, R.mipmap.ic_launcher);// 显示专辑封面图片
                    }
                    break;
                case AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC:
                case AudioBroadcastReceiver.ACTION_SERVICE_RESUMEMUSIC:
                    mNotifyPlayBarRemoteViews.setViewVisibility(R.id.play, View.INVISIBLE);
                    mNotifyPlayBarRemoteViews.setViewVisibility(R.id.pause, View.VISIBLE);
                    mNotifyPlayBarRemoteViews.setOnClickPendingIntent(R.id.pause, pendpauseButtonIntent);
                    break;
                case AudioBroadcastReceiver.ACTION_SERVICE_PAUSEMUSIC://播放器暂停
                    mNotifyPlayBarRemoteViews.setViewVisibility(R.id.play, View.VISIBLE);
                    mNotifyPlayBarRemoteViews.setViewVisibility(R.id.pause, View.INVISIBLE);
                    mNotifyPlayBarRemoteViews.setOnClickPendingIntent(R.id.play, pendplayButtonIntent);
                    break;
            }

        }

        mPlayBarNotification.contentView = mNotifyPlayBarRemoteViews;
//        startForeground(mNotificationPlayBarId, mPlayBarNotification);
        notificationManager.notify(mNotificationPlayBarId, mPlayBarNotification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mAudioBroadcastReceiver.unregisterReceiver(getApplicationContext());
        mNotificationReceiver.unregisterReceiver(getApplicationContext());
        stopForeground(true);
        releasePlayer();
        Logger.i("音频播放服务销毁");
        super.onDestroy();
    }

    /**
     * 音频广播处理
     *
     * @param context
     * @param intent
     */
    private void doAudioReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case AudioBroadcastReceiver.ACTION_NULLMUSIC:
                releasePlayer();
                resetPlayData();
                break;
            case AudioBroadcastReceiver.ACTION_PLAYMUSIC:
                ToastUtils.showToast("播放歌曲");
                //播放歌曲
                playMusic((AudioMessage) intent.getSerializableExtra(AudioMessage.KEY));
                break;
            case AudioBroadcastReceiver.ACTION_PAUSEMUSIC:
                //暂停歌曲
                ToastUtils.showToast("暂停歌曲");
                pauseMusic();
                break;
            case AudioBroadcastReceiver.ACTION_RESUMEMUSIC:
                //唤醒歌曲
                ToastUtils.showToast("唤醒歌曲");
                resumeMuseic((AudioMessage) intent.getSerializableExtra(AudioMessage.KEY));
                break;
            case AudioBroadcastReceiver.ACTION_SEEKTOMUSIC:
                //歌曲快进
                ToastUtils.showToast("歌曲快进");
                seekToMusic((AudioMessage) intent.getSerializableExtra(AudioMessage.KEY));
                break;
            case AudioBroadcastReceiver.ACTION_NEXTMUSIC:
                //下一首
                ToastUtils.showToast("下一首");
                nextMusic();
                break;
            case AudioBroadcastReceiver.ACTION_PREMUSIC:
                //上一首
                ToastUtils.showToast("上一首");
                preMusic();
                break;
        }
        if (action.equals(AudioBroadcastReceiver.ACTION_NULLMUSIC)
                || action.equals(AudioBroadcastReceiver.ACTION_INITMUSIC)
                || action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC)
                || action.equals(AudioBroadcastReceiver.ACTION_SERVICE_RESUMEMUSIC)
                || action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PAUSEMUSIC)) {

            //处理通知栏数据
            Message msg = new Message();
            msg.obj = intent;
            msg.what = 0;
            mNotificationHandler.sendMessage(msg);
        }

    }

    /**
     * 播放歌曲
     *
     * @param audioMessage
     */
    private void playMusic(AudioMessage audioMessage) {
        releasePlayer();
        AudioInfo audioInfo = audioMessage.getAudioInfo();

        if (audioPlayerManager.getCurAudioInfo() != null) {
            if (!audioPlayerManager.getCurAudioInfo().getHash().equals(audioInfo.getHash())) {
                // 设置当前播放数据
                audioPlayerManager.setCurAudioMessage(audioMessage);
                // 设置当前正在播放的歌曲数据
                audioPlayerManager.setCurAudioInfo(audioInfo);
                // 设置当前播放的索引
                audioPlayerManager.setPlayIndexHashID(audioInfo.getHash());

                // 发送init的广播
                Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INITMUSIC);
                initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(initIntent);
            }
        } else {
            // 设置当前播放数据
            audioPlayerManager.setCurAudioMessage(audioMessage);
            // 设置当前正在播放的歌曲数据
            audioPlayerManager.setCurAudioInfo(audioInfo);
            // 设置当前播放的索引
            audioPlayerManager.setPlayIndexHashID(audioInfo.getHash());

            //发送init的广播
            Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INITMUSIC);
            initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(initIntent);
        }


        playNetMusic(audioInfo.getUrl());

    }

    /**
     * 播放网络歌曲
     */
    private void playNetMusic(String url) {

        if (audioPlayerManager.getCurAudioMessage() != null && audioPlayerManager.getCurAudioInfo() != null) {
            try {
                mMediaPlayer = new IjkMediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.prepareAsync();

                mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                        mMediaPlayer.start();
                        isSeekTo = false;
                    }
                });

                mMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(IMediaPlayer iMediaPlayer) {
                        // 播放完成，执行下一首
                        nextMusic();
                    }
                });

                mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                        // 发送广播
                        Intent errorIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYERRORMUSIC);
                        errorIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        sendBroadcast(errorIntent);

                        ToastUtils.showToast("播放出错，1秒后播放下一首");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    //播放下一首
                                    nextMusic();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        return false;
                    }
                });

                mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(IMediaPlayer iMediaPlayer) {
                        if (audioPlayerManager.getCurAudioMessage() != null) {
                            AudioMessage audioMessage = audioPlayerManager.getCurAudioMessage();

                            if (audioMessage.getPlayProgress() > 0) {
                                isSeekTo = true;
                                mMediaPlayer.seekTo(audioMessage.getPlayProgress());
                            } else {
                                mMediaPlayer.start();
                            }

                            // 设置当前播放状态
                            audioPlayerManager.setPlayStatus(AudioPlayerManager.PLAYING);

                            // 发送play广播
                            Intent playIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC);
                            playIntent.putExtra(AudioMessage.KEY, audioMessage);
                            playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            sendBroadcast(playIntent);
                        }
                    }
                });

                if (mPlayerThread == null) {
                    mPlayerThread = new Thread(new PlayerRunable());
                    mPlayerThread.start();
                }

            } catch (Exception e) {
                Logger.e(e.getMessage());

                // 发送错误广播
                Intent errorIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYERRORMUSIC);
                audioPlayerManager.getCurAudioMessage().setErrorMsg(e.getMessage());
                errorIntent.putExtra(AudioMessage.KEY, audioPlayerManager.getCurAudioMessage());
                errorIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(errorIntent);

                ToastUtils.showToast("播放歌曲出错，1秒后播放下一首");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            //播放下一首
                            nextMusic();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }


    /**
     * 播放线程
     */
    private class PlayerRunable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                    if (!isSeekTo && mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                        if (audioPlayerManager.getCurAudioMessage() != null) {
                            audioPlayerManager.getCurAudioMessage().setPlayProgress(mMediaPlayer.getCurrentPosition());
                            audioPlayerManager.getCurAudioMessage().setPlayProgressTotal(mMediaPlayer.getDuration());
                            //发送正在播放中的广播
                            Intent playingIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYINGMUSIC);
                            playingIntent.putExtra(AudioMessage.KEY, audioPlayerManager.getCurAudioMessage());
                            playingIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            sendBroadcast(playingIntent);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 播放暂停
     */
    private void pauseMusic() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
        audioPlayerManager.setPlayStatus(AudioPlayerManager.PAUSE);
        Intent pauseIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PAUSEMUSIC);
        pauseIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(pauseIntent);
    }

    /**
     * 唤醒播放
     */
    private void resumeMuseic(AudioMessage audioMessage) {
        if (mMediaPlayer != null) {
            if (audioPlayerManager.getPlayStatus() == AudioPlayerManager.PAUSE){
                mMediaPlayer.start();
            }else {
                isSeekTo = true;
                mMediaPlayer.seekTo(audioMessage.getPlayProgress());
            }

        }
        audioPlayerManager.setPlayStatus(AudioPlayerManager.PLAYING);

        Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_RESUMEMUSIC);
        resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(resumeIntent);
    }

    /**
     * 快进
     *
     * @param audioMessage
     */
    private void seekToMusic(AudioMessage audioMessage) {
        if (mMediaPlayer != null) {
            isSeekTo = true;
            mMediaPlayer.seekTo(audioMessage.getPlayProgress());
        }
    }

    /**
     * 下一首
     */
    private void nextMusic() {
        AudioInfo audioInfo = audioPlayerManager.nextMusic(0);
        if (audioInfo == null) {
            releasePlayer();
            resetPlayData();
            // 发送空数据
            Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULLMUSIC);
            nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(nullIntent);
            return;
        }
        Logger.e("下一首歌曲为：" + audioInfo.getSongName());

        AudioMessage audioMessage = new AudioMessage();
        audioMessage.setAudioInfo(audioInfo);
        playMusic(audioMessage);
    }

    /**
     * 上一首
     */
    private void preMusic() {
        AudioInfo audioInfo = audioPlayerManager.preMusic(0);
        if (audioInfo == null) {
            releasePlayer();
            resetPlayData();
            // 发送空数据
            Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULLMUSIC);
            nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(nullIntent);
            return;
        }

        Logger.e("下一首歌曲为：" + audioInfo.getSongName());

        AudioMessage audioMessage = new AudioMessage();
        audioMessage.setAudioInfo(audioInfo);
        playMusic(audioMessage);

    }

    /**
     * 释放播放器
     */
    private void releasePlayer() {
        if (mPlayerThread != null) {
            mPlayerThread = null;
        }
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
            audioPlayerManager.setPlayStatus(AudioPlayerManager.STOP);
        }

    }

    /**
     * 重置播放器
     */
    private void resetPlayData() {
        audioPlayerManager.resetData();
    }
}
