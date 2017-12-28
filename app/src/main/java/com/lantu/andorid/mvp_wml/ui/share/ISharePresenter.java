package com.lantu.andorid.mvp_wml.ui.share;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;

/**
 * Created by wml on 2017/10/19.
 */

public interface ISharePresenter<T> extends IBasePresenter {

    /**
     * 微信登陆
     */
    void loginWx();

    /**
     * 微博登陆
     */
    void loginWeiBo();

    /**
     * QQ登陆
     */
    void loginQQ();

    /**
     * 分享弹窗口
     */
    void showShareDialog();

}
