package com.lantu.andorid.mvp_wml.ui.audio;

import android.content.Context;
import android.content.Intent;

import com.lantu.andorid.mvp_wml.receiver.AudioBroadcastReceiver;
import com.lantu.andorid.mvp_wml.utils.PreferencesUtil;

import java.util.List;

/**
 * 音频播放处理类
 * Created by wml on 2017/12/14.
 */

public class AudioPlayerManager {

    /**
     * 播放状态key
     */
    public static String playStatus_KEY = "playStatus_KEY";

    private int playStatus = -1;

    /**
     * 播放歌曲id key
     */
    public static String playIndexHashID_KEY = "playIndexHashID_KEY";

    private Context mContext;
    /**
     * 正在播放
     */
    public static final int PLAYING = 0;
    /**
     * 暂停
     */
    public static final int PAUSE = 1;
    /**
     * 停止
     */
    public static final int STOP = 2;
    /**
     * 播放在线音乐
     */
    public static final int PLAYNET = 3;

    /**
     * 当前播放列表
     */
    private List<AudioInfo> curAudioInfos;

    /**
     * 设置当前正在播放的歌曲
     */
    private AudioInfo curAudioInfo;

    /**
     * 当前歌曲
     */
    private AudioMessage curAudioMessage;

    /**
     * 播放歌曲id
     */
    private String playIndexHashID = "";


    private static AudioPlayerManager _AudioPlayerManager;

    public AudioPlayerManager(Context context) {
        this.mContext = context;
    }

    /**
     * @param context
     * @return
     */
    public static AudioPlayerManager getAudioPlayerManager(Context context) {

        if (_AudioPlayerManager == null) {
            _AudioPlayerManager = new AudioPlayerManager(context);
        }
        return _AudioPlayerManager;
    }

    /**
     * 初始
     */
    public void initSongInfoData() {
        if (curAudioInfos != null && curAudioInfos.size() > 0) {
            String playInfoHashID = getPlayIndexHashID();
            if (playInfoHashID == null || playInfoHashID.equals("")) {
                resetData();
                //发送空数据广播
                Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULLMUSIC);
                nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                mContext.sendBroadcast(nullIntent);
                return;
            }
            boolean flag = false;
            for (AudioInfo info : curAudioInfos) {
                if (info.getHash().equals(playInfoHashID)) {
                    flag = true;
                    //发送init的广播
                    AudioMessage curAudioMessage = new AudioMessage();
                    curAudioMessage.setAudioInfo(info);

                    setCurAudioMessage(curAudioMessage);
//                    setCurAudioInfo(info);

                    Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INITMUSIC);
                    initIntent.putExtra(AudioMessage.KEY, curAudioMessage);
                    initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    mContext.sendBroadcast(initIntent);
                }
            }
            if (!flag) {
                resetData();
            }

        } else {
            resetData();
            //发送空数据广播
            Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULLMUSIC);
            nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            mContext.sendBroadcast(nullIntent);
        }
    }

    /**
     * 上一首
     *
     * @param playModel
     */
    public AudioInfo preMusic(int playModel) {
        if (getCurAudioInfo() == null || getCurAudioMessage() == null || getCurAudioInfos() == null) {
            return null;
        }
        // 获取播放索引
        int playIndex = getCurPlayIndex();
        if (playIndex == -1) {
            return null;
        }

        // 顺序播放
        playIndex--;
        if (playIndex < 0) {
            return null;
        }

        if (getCurAudioInfos().size() > 0) {
            return getCurAudioInfos().get(playIndex);
        }
        return null;
    }

    /**
     * 下一首
     *
     * @param playModel 播放模式
     * @return
     */
    public AudioInfo nextMusic(int playModel) {
        if (getCurAudioInfo() == null || getCurAudioMessage() == null || getCurAudioInfos() == null) {
            return null;
        }
        // 获取播放索引
        int playIndex = getCurPlayIndex();
        if (playIndex == -1) {
            return null;
        }

        // 顺序播放
        playIndex++;
        if (playIndex >= getCurAudioInfos().size()) {
            return null;
        }

        if (getCurAudioInfos().size() > 0) {
            return getCurAudioInfos().get(playIndex);
        }
        return null;
    }

    /**
     *
     */
    public void resetData() {
        //清空之前的播放数据
        setPlayStatus(STOP);
        setPlayIndexHashID("-1");
        setCurAudioMessage(null);
        setCurAudioInfos(null);
        setCurAudioInfo(null);
    }

    /**
     * 获取当前的播放索引
     *
     * @return
     */
    private int getCurPlayIndex() {
        int index = 0;
        for (int i = 0; i < getCurAudioInfos().size(); i++) {
            AudioInfo audioInfo = getCurAudioInfos().get(i);
            if (audioInfo.getHash().equals(getPlayIndexHashID())) {
                return i;
            }
        }
        return index;
    }

    /**
     * 获取当前播放列表
     * @return
     */
    public List<AudioInfo> getCurAudioInfos() {
        return curAudioInfos;
    }

    /**
     * 设置播放列表
     * @param curAudioInfos
     */
    public void setCurAudioInfos(List<AudioInfo> curAudioInfos) {
        this.curAudioInfos = curAudioInfos;
    }

    /**
     * 获取当前播放状态
     * @return
     */
    public int getPlayStatus() {
        return playStatus;
    }

    /**
     * 设置当前播放状态
     * @param playStatus
     */
    public void setPlayStatus(int playStatus) {
        this.playStatus = playStatus;
    }

    /**
     * 获取当前播放ID
     * @return
     */
    public String getPlayIndexHashID() {
        return (String) PreferencesUtil.getValue(mContext, playIndexHashID_KEY, playIndexHashID);
    }

    /**
     * 设置当前播放ID
     * @param playIndexHashID
     */
    public void setPlayIndexHashID(String playIndexHashID) {
        PreferencesUtil.saveValue(mContext, playIndexHashID_KEY, playIndexHashID);
    }

    /**
     * 获取当前歌曲
     * @return
     */
    public AudioInfo getCurAudioInfo() {
        return curAudioInfo;
    }

    /**
     * 设置当前歌曲
     * @param curAudioInfo
     */
    public void setCurAudioInfo(AudioInfo curAudioInfo) {
        this.curAudioInfo = curAudioInfo;
    }

    /**
     * 获取音频信息
     * @return
     */
    public AudioMessage getCurAudioMessage() {
        return curAudioMessage;
    }

    /**
     * 设置音频信息
     * @param curAudioMessage
     */
    public void setCurAudioMessage(AudioMessage curAudioMessage) {
        this.curAudioMessage = curAudioMessage;
    }
}
