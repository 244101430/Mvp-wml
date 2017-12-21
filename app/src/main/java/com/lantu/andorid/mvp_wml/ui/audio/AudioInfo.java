package com.lantu.andorid.mvp_wml.ui.audio;

import android.os.Parcel;
import android.os.Parcelable;

import com.dl7.downloaderlib.model.DownloadStatus;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wml8743 on 2017/12/14.
 */

public class AudioInfo {

    /**
     *
     */
    private String hash;
    /**
     * 歌曲名称
     */
    private String songName;
    private String url;

    public AudioInfo() {

    }

    public AudioInfo(String hash, String songName, String url) {
        this.url = url;
        this.hash = hash;
        this.songName = songName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
