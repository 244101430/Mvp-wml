package com.lantu.andorid.mvp_wml.ui.other;

/**
 * Created by wml8743 on 2017/11/17.
 */

public class ScrollViewPresenter implements IScrollViewPresenter {

    private IScrollView mView;

    public ScrollViewPresenter(IScrollView mView) {
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
