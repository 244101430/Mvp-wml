package com.lantu.andorid.mvp_wml.ui.test.download;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IRxBusPresenter;

/**
 * Created by wml8743 on 2017/12/21.
 */

public interface IDownloadActivityPresenter extends IRxBusPresenter{
    void download_start();
    void download_pause();
    void download_cancel();
}
