package com.lantu.andorid.mvp_wml.ui.audio;

import java.io.Serializable;

/**
 * Created by wml8743 on 2017/12/14.
 */

public class AudioMessage implements Serializable {
    public static final String KEY = "com.zlm.hp.am.key";

    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 播放进度
     */
    private long playProgress;
    /**
     *  总进度
     */
    private long playProgressTotal;

    /**
     * 音频信息
     */
    private AudioInfo audioInfo;
    /**
     *
     */
    private String hash;


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public long getPlayProgress() {
        return playProgress;
    }

    public void setPlayProgress(long playProgress) {
        this.playProgress = playProgress;
    }

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getPlayProgressTotal() {
        return playProgressTotal;
    }

    public void setPlayProgressTotal(long playProgressTotal) {
        this.playProgressTotal = playProgressTotal;
    }
}
