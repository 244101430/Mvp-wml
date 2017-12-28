package com.lantu.andorid.mvp_wml.ui.other;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

/**
 * Created by wml on 2017/12/4.
 */

public class ListFragmentPresenter implements IBasePresenter {

    private IBaseView mView;

    public ListFragmentPresenter(IBaseView mView) {
        this.mView = mView;
    }
    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
