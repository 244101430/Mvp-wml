package com.lantu.andorid.mvp_wml.ui.test.download;

import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

/**
 * Created by wml on 2017/12/21.
 */

public interface IDownloadActivityView extends IBaseView{

    void download_start();
    void download_pause();
    void download_cancel();
    void download_seekbar(long progres);
}
