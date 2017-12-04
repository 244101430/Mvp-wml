package com.lantu.andorid.mvp_wml.ui.share;

import android.content.Context;

import me.shaohui.shareutil.LoginUtil;
import me.shaohui.shareutil.ShareConfig;
import me.shaohui.shareutil.ShareManager;
import me.shaohui.shareutil.login.LoginListener;
import me.shaohui.shareutil.login.LoginPlatform;
import me.shaohui.shareutil.login.LoginResult;

/**
 * Created by wml8743 on 2017/10/19.
 */

public class SharaPresenter implements ISharePresenter{

    String QQ_ID = "XXXXXX";
    String WEIBO_ID = "XXXXXXX";
    String WX_ID = "XXXXXXX";
    String WEIBO_REDIRECTURL = "XXXXXXX";
    String WX_SECRET = "XXXXXXX";

    private IShareView mView;
    private Context mContext;

    /**
     * 第三方登录成功处理
     */
    private LoginListener mLoginListener = new LoginListener() {
        @Override
        public void loginSuccess(LoginResult result) {
            mView.loginSuccess(result);
        }

        @Override
        public void loginFailure(Exception e) {
            mView.loginFailure(e);
        }

        @Override
        public void loginCancel() {
            mView.loginCancel();
        }
    };

    public SharaPresenter(IShareView mView, Context context) {
        this.mView = mView;
        this.mContext = context;

        ShareConfig config = ShareConfig.instance()
                .qqId(QQ_ID)
                .weiboId(WEIBO_ID)
                .wxId(WX_ID)
                .weiboRedirectUrl(WEIBO_REDIRECTURL)
                .wxSecret(WX_SECRET);
        ShareManager.init(config);
    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void loginWx() {
        LoginUtil.login(mContext, LoginPlatform.WX, mLoginListener);
    }

    @Override
    public void loginWeiBo() {
        LoginUtil.login(mContext, LoginPlatform.WEIBO, mLoginListener);
    }

    @Override
    public void loginQQ() {
        LoginUtil.login(mContext, LoginPlatform.QQ, mLoginListener);
    }

    @Override
    public void showShareDialog() {
        mView.showShareDialog();
    }
}
