package com.lantu.andorid.mvp_wml.ui.video;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

/**
 * Created by wml8743 on 2017/12/11.
 */

public class VideoActivityPresenter implements IBasePresenter{

    private IBaseView mView;

    public VideoActivityPresenter(IBaseView mView) {
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
