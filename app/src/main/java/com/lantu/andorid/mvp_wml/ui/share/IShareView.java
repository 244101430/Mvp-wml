package com.lantu.andorid.mvp_wml.ui.share;

import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

import me.shaohui.shareutil.login.LoginResult;

/**
 * Created by wml8743 on 2017/10/19.
 */

public interface IShareView extends IBaseView{

    /**
     * 登陆成功
     * @param result
     */
    void loginSuccess(LoginResult result);

    /**
     * 登陆失败
     * @param e
     */
    void loginFailure(Exception e);

    /**
     * 登陆取消
     */
    void loginCancel();

    /**
     * 显示分享弹框
     */
    void showShareDialog();

}
