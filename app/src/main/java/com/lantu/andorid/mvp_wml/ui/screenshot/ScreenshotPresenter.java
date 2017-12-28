package com.lantu.andorid.mvp_wml.ui.screenshot;

import com.lantu.andorid.mvp_wml.widget.CertificateView;

/**
 * Created by wml on 2017/10/18.
 */

public class ScreenshotPresenter implements IScreenshotPresenter{

    private IScreenshotView mView;

    public ScreenshotPresenter(IScreenshotView mView) {
        this.mView = mView;
    }


    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void shotScrollView(CertificateView view) {

        mView.showImageView(view.shotScrollView());
    }

}
