package com.lantu.andorid.mvp_wml.ui.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerShareComponent;
import com.lantu.andorid.mvp_wml.injector.modules.ShareModule;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import com.lantu.andorid.mvp_wml.ui.dialog.ShareBottomDialog;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import butterknife.OnClick;
import me.shaohui.shareutil.login.LoginPlatform;
import me.shaohui.shareutil.login.LoginResult;
import me.shaohui.shareutil.login.result.QQToken;
import me.shaohui.shareutil.login.result.QQUser;
import me.shaohui.shareutil.login.result.WeiboToken;
import me.shaohui.shareutil.login.result.WeiboUser;
import me.shaohui.shareutil.login.result.WxToken;
import me.shaohui.shareutil.login.result.WxUser;

/**
 * 分享测试&第三方登录
 */
public class ShareActivity extends BaseSwipeBackActivity<ISharePresenter> implements IShareView, View.OnClickListener {


    public final static void launcher(Activity activity) {
        activity.startActivity(new Intent(activity, ShareActivity.class));
        activity.overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_share;
    }

    @Override
    protected void initInjector() {
        DaggerShareComponent.builder()
                .applicationComponent(getAppComponent())
                .shareModule(new ShareModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @OnClick({R.id.login_wx, R.id.login_weibo, R.id.login_qq, R.id.bt_share})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_wx:
                mPresenter.loginWx();
                break;
            case R.id.login_weibo:
                mPresenter.loginWeiBo();
                break;
            case R.id.login_qq:
                mPresenter.loginQQ();
                break;
            case R.id.bt_share:
                mPresenter.showShareDialog();
                break;
        }
    }

    @Override
    public void loginSuccess(LoginResult result) {
        showToast(result.getUserInfo() != null ? result.getUserInfo().getNickname() : "" + "登录成功");
        switch (result.getPlatform()) {
            case LoginPlatform.WX:
                WxUser wxUser = (WxUser) result.getUserInfo();
                WxToken wxToken = (WxToken) result.getToken();
                break;
            case LoginPlatform.WEIBO:
                WeiboUser weiboUser = (WeiboUser) result.getUserInfo();
                WeiboToken weiboToken = (WeiboToken) result.getToken();
                break;
            case LoginPlatform.QQ:
                QQUser qqUser = (QQUser) result.getUserInfo();
                QQToken qqToken = (QQToken) result.getToken();
                break;
        }
    }

    @Override
    public void loginFailure(Exception e) {
        showToast("登录失败");

    }

    @Override
    public void loginCancel() {
        showToast("登录取消");

    }

    @Override
    public void showShareDialog() {
        ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.show(getSupportFragmentManager());

        NiceDialog.init()
                .setLayoutId(R.layout.layout_bottom_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {

                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());

    }

}
