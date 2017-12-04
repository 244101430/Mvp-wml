package com.lantu.andorid.mvp_wml.ui.screenshot;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.widget.CertificateView;

/**
 * Created by wml8743 on 2017/10/18.
 */

public interface IScreenshotPresenter<T> extends IBasePresenter {
    void shotScrollView(CertificateView view);
}
