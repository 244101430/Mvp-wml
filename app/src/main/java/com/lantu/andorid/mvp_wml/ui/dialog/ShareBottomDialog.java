package com.lantu.andorid.mvp_wml.ui.dialog;

import android.view.View;
import android.widget.Toast;

import com.lantu.andorid.mvp_wml.R;

import butterknife.OnClick;
import me.shaohui.shareutil.ShareUtil;
import me.shaohui.shareutil.share.ShareListener;
import me.shaohui.shareutil.share.SharePlatform;

/**
 * Created by wml8743 on 2017/10/18.
 */

public class ShareBottomDialog extends BaseBottomDialog implements View.OnClickListener{

    private ShareListener mShareListener;

    @Override
    public int getLayoutRes() {
        return R.layout.layout_bottom_share;
    }

    @Override
    public void bindView(final View v) {


        mShareListener = new ShareListener() {
            @Override
            public void shareSuccess() {
                Toast.makeText(v.getContext(), "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareFailure(Exception e) {
                Toast.makeText(v.getContext(), "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareCancel() {
                Toast.makeText(v.getContext(), "取消分享", Toast.LENGTH_SHORT).show();

            }
        };
    }

    /**
     * qq分享、qq空间分享，微博分享，朋友圈、微信
     * @param view
     */
    @OnClick({R.id.share_qq, R.id.share_qzone, R.id.share_weibo, R.id.share_wx_timeline, R.id.share_wx})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_qq:
                ShareUtil.shareImage(getContext(), SharePlatform.QQ,
                        "http://shaohui.me/images/avatar.gif", mShareListener);
                break;
            case R.id.share_qzone:
                ShareUtil.shareMedia(getContext(), SharePlatform.QZONE, "Title", "summary",
                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
                        mShareListener);
                break;
            case R.id.share_weibo:
                ShareUtil.shareImage(getContext(), SharePlatform.WEIBO,
                        "http://shaohui.me/images/avatar.gif", mShareListener);
                break;
            case R.id.share_wx_timeline:
                ShareUtil.shareText(getContext(), SharePlatform.WX_TIMELINE, "测试分享文字",
                        mShareListener);
                break;
            case R.id.share_wx:
                ShareUtil.shareMedia(getContext(), SharePlatform.WX, "Title", "summary",
                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
                        mShareListener);
                break;
        }
        dismiss();
    }
}
