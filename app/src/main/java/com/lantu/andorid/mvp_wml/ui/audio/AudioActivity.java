package com.lantu.andorid.mvp_wml.ui.audio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lantu.andorid.mvp_wml.AndroidApplication;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerAudioActivityComponent;
import com.lantu.andorid.mvp_wml.injector.modules.AudioActivityMoudle;
import com.lantu.andorid.mvp_wml.receiver.AudioBroadcastReceiver;
import com.lantu.andorid.mvp_wml.service.AudioPlayerService;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;
import com.lantu.andorid.mvp_wml.utils.Logger;
import com.lantu.andorid.mvp_wml.widget.LrcSeekBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 音频播放
 */
public class AudioActivity extends BaseSwipeBackActivity<IBasePresenter> implements IBaseView, View.OnClickListener {

    private String url = "http://noah-video-cdn.ifaclub.com/20171219.mp3?e=1513701975&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:b9TKM7CLsS0GirXTjfgnLzaVgso=";
//    private String url = SDCardUtils.getRootPath() + "/test.mp3";

    /**
     * 音频广播
     */
    private AudioBroadcastReceiver mAudioBroadcastReceiver;
    /**
     * 音频广播监听
     */
    private AudioBroadcastReceiver.AudioReceiverListener mAudioReceiverListener = new AudioBroadcastReceiver.AudioReceiverListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            doAudioReceive(context, intent);
        }
    };

    private AudioPlayerManager audioPlayerManager;

    /**
     * 当前播放歌曲的索引
     */
    private String mCurPlayIndexHash = "";

    @BindView(R.id.bt_play)
    protected Button bt_play;
    @BindView(R.id.bt_pause)
    protected Button bt_pause;
    @BindView(R.id.bt_resume)
    protected Button bt_resume;
    @BindView(R.id.bt_next)
    protected Button bt_next;
    @BindView(R.id.bt_previous)
    protected Button bt_prevous;
    @BindView(R.id.seekBar)
    protected LrcSeekBar seekBar;

    private ArrayList<AudioInfo> mDatas = new ArrayList<>();
    private Intent playerServiceIntent;

    /**
     * 启动函数
     *
     * @param activity
     */
    public static final void launcher(Activity activity) {
        activity.startActivity(new Intent(activity, AudioActivity.class));
        activity.overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeadTitle("音频播放");

        initAudionManager();
        initService();
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_audio;
    }

    @Override
    protected void initInjector() {
        DaggerAudioActivityComponent
                .builder()
                .audioActivityMoudle(new AudioActivityMoudle(this))
                .build();
    }

    @Override
    protected void initViews() {
        seekBar.setOnChangeListener(new LrcSeekBar.OnChangeListener() {
            @Override
            public void onProgressChanged() {

            }

            @Override
            public String getTimeText() {
                int time = seekBar.getProgress();
                time /= 1000;
                int minute = time / 60;
                int second = time % 60;
                minute %= 60;
                return String.format("%02d:%02d", minute, second);
            }

            @Override
            public String getLrcText() {
                return null;
            }

            @Override
            public void dragFinish() {
                int playStatus = audioPlayerManager.getPlayStatus();
                if (playStatus == AudioPlayerManager.PLAYING) {
                    // 正在播放
                    if (audioPlayerManager.getCurAudioMessage() != null) {
                        AudioMessage audioMessage = audioPlayerManager.getCurAudioMessage();
                        if (audioMessage != null) {
                            audioMessage.setPlayProgress(seekBar.getProgress());
                            Intent resumeIntent = new Intent(AudioBroadcastReceiver.ACTION_SEEKTOMUSIC);
                            resumeIntent.putExtra(AudioMessage.KEY, audioMessage);
                            resumeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            sendBroadcast(resumeIntent);
                        }
                    }
                } else {
                    if (audioPlayerManager.getCurAudioMessage() != null) {
                        audioPlayerManager.getCurAudioMessage().setPlayProgress(seekBar.getProgress());
                    }
                }
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 初始化播放管理器
     */
    private void initAudionManager() {

        mDatas.add(new AudioInfo("1", "音频一", url));
        mDatas.add(new AudioInfo("2", "音频二", url));
        mDatas.add(new AudioInfo("3", "音频三", url));

        audioPlayerManager = AndroidApplication.getAudioPlayerManager();
        audioPlayerManager.setCurAudioInfos(mDatas);
    }

    /**
     * 初始化服务
     */
    private void initService() {
        playerServiceIntent = new Intent(this, AudioPlayerService.class);
        getApplication().startService(playerServiceIntent);


        //注册接收音频播放广播
        mAudioBroadcastReceiver = new AudioBroadcastReceiver(getApplicationContext());
        mAudioBroadcastReceiver.setAudioReceiverListener(mAudioReceiverListener);
        mAudioBroadcastReceiver.registerReceiver(getApplicationContext());
    }

    /**
     * 处理音频广播事件
     *
     * @param context
     * @param intent
     */
    private void doAudioReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case AudioBroadcastReceiver.ACTION_NULLMUSIC://空数据
                Logger.e("空数据");
                break;
            case AudioBroadcastReceiver.ACTION_INITMUSIC://初始化
                Logger.e("初始化");
                // 初始化
                AudioMessage audioMessage = audioPlayerManager.getCurAudioMessage();
                AudioInfo audioInfo = audioPlayerManager.getCurAudioInfo();
                mCurPlayIndexHash = audioInfo.getHash();

                seekBar.setEnabled(true);
                seekBar.setMax(100);
                seekBar.setSecondaryProgress(0);

                // 更新视图

                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_PLAYMUSIC: //播放
                Logger.e("播放");

                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_PAUSEMUSIC://暂停完成
                Logger.e("暂停完成");
                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_RESUMEMUSIC:// 唤醒完成
                Logger.e("唤醒完成");
                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_PLAYINGMUSIC:// 播放中
                AudioMessage audioMessage1 = audioPlayerManager.getCurAudioMessage();
                if (audioMessage1 != null) {
                    seekBar.setProgress((int) audioMessage1.getPlayProgress());
                    seekBar.setMax((int) audioMessage1.getPlayProgressTotal());
                }

                break;
        }
    }


    @OnClick({R.id.bt_play, R.id.bt_pause, R.id.bt_resume, R.id.bt_next, R.id.bt_previous})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_play:
                if (audioPlayerManager.getPlayStatus() == AudioPlayerManager.PAUSE) {
                    AudioInfo audioInfo = audioPlayerManager.getCurAudioInfo();
                    if (audioInfo != null) {
                        AudioMessage audioMessage = audioPlayerManager.getCurAudioMessage();
                        Intent reusmeIntent = new Intent(AudioBroadcastReceiver.ACTION_RESUMEMUSIC);
                        reusmeIntent.putExtra(AudioMessage.KEY, audioMessage);
                        reusmeIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        sendBroadcast(reusmeIntent);
                    }
                } else if (audioPlayerManager.getPlayStatus() == AudioPlayerManager.PLAYING) {
                    onClick(findViewById(R.id.bt_pause));
                } else {
                    AudioMessage audioMessage = new AudioMessage();
                    audioMessage.setAudioInfo(mDatas.get(0));

                    audioPlayerManager.setCurAudioInfos(mDatas);
                    audioPlayerManager.setCurAudioInfo(audioMessage.getAudioInfo());
                    audioPlayerManager.setCurAudioMessage(audioMessage);

                    Intent playIntent = new Intent(AudioBroadcastReceiver.ACTION_PLAYMUSIC);
                    playIntent.putExtra(AudioMessage.KEY, audioMessage);
                    playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(playIntent);
                }

                break;
            case R.id.bt_pause:
                Intent pauseIntent = new Intent(AudioBroadcastReceiver.ACTION_PAUSEMUSIC);
                pauseIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(pauseIntent);
                break;
            case R.id.bt_resume:
                onClick(findViewById(R.id.bt_play));
                break;
            case R.id.bt_next:
                Intent nextIntent = new Intent(AudioBroadcastReceiver.ACTION_NEXTMUSIC);
                nextIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(nextIntent);
                break;
            case R.id.bt_previous:
                Intent previousIntent = new Intent(AudioBroadcastReceiver.ACTION_PREMUSIC);
                previousIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(previousIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioPlayerManager.resetData();

        // 发送空数据
        Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULLMUSIC);
        nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(nullIntent);

        //注销广播
        mAudioBroadcastReceiver.unregisterReceiver(getApplicationContext());
        audioPlayerManager.resetData();
    }
}
