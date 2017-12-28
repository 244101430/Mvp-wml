package com.lantu.andorid.mvp_wml.ui.audio;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

/**
 * Created by wml on 2017/12/11.
 */

public class AudioPresenter implements IBasePresenter{

    private final IBaseView mView;

    public AudioPresenter(IBaseView mView) {
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
