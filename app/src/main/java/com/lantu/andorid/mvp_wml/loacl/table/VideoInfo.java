package com.lantu.andorid.mvp_wml.loacl.table;

import android.os.Parcel;
import android.os.Parcelable;

import com.dl7.downloaderlib.model.DownloadStatus;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wml8743 on 2017/12/21.
 */
@Entity
public class VideoInfo implements Parcelable{
    @Id
    private String vid;
    private String title;
    private String videoUrl;

    private long totalSize;

    private long loadedSize;
    /**
     * 下载状态
     */
    private int downloadStatus = DownloadStatus.NORMAL;

    private long downloadSpeed;

    private boolean isCollect;

    public VideoInfo(){

    }
    protected VideoInfo(Parcel in) {
        vid = in.readString();
        title = in.readString();
        videoUrl = in.readString();
        totalSize = in.readLong();
        loadedSize = in.readLong();
        downloadStatus = in.readInt();
        downloadSpeed = in.readLong();
        isCollect = in.readByte() != 0;
    }
    @Generated(hash = 1167957326)
    public VideoInfo(String vid, String title, String videoUrl, long totalSize,
            long loadedSize, int downloadStatus, long downloadSpeed,
            boolean isCollect) {
        this.vid = vid;
        this.title = title;
        this.videoUrl = videoUrl;
        this.totalSize = totalSize;
        this.loadedSize = loadedSize;
        this.downloadStatus = downloadStatus;
        this.downloadSpeed = downloadSpeed;
        this.isCollect = isCollect;
    }

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        @Override
        public VideoInfo createFromParcel(Parcel in) {
            return new VideoInfo(in);
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getLoadedSize() {
        return loadedSize;
    }

    public void setLoadedSize(long loadedSize) {
        this.loadedSize = loadedSize;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public long getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(long downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "vid='" + vid + '\'' +
                ", title='" + title + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", totalSize=" + totalSize +
                ", loadedSize=" + loadedSize +
                ", downloadStatus=" + downloadStatus +
                ", downloadSpeed=" + downloadSpeed +
                ", isCollect=" + isCollect +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.vid);
        parcel.writeString(this.title);
        parcel.writeString(this.videoUrl);
        parcel.writeLong(this.totalSize);
        parcel.writeLong(this.loadedSize);
        parcel.writeInt(this.downloadStatus);
        parcel.writeLong(this.downloadSpeed);
        parcel.writeByte(this.isCollect ? (byte) 1 : (byte) 0);
    }
    public boolean getIsCollect() {
        return this.isCollect;
    }
    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }
}
