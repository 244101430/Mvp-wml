package com.lantu.andorid.mvp_wml.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.ViewHolder;

import butterknife.OnClick;
import me.shaohui.shareutil.ShareUtil;
import me.shaohui.shareutil.share.ShareListener;
import me.shaohui.shareutil.share.SharePlatform;

/**
 * 分享弹出框
 * Created by wml8743 on 2017/10/18.
 */
public class ShareBottomDialog extends BaseNiceDialog {

    private ShareListener mShareListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowBottom(true);
        mShareListener = new ShareListener() {
            @Override
            public void shareSuccess() {
                ToastUtils.showToast("分享成功");
            }

            @Override
            public void shareFailure(Exception e) {
                ToastUtils.showToast("分享失败");
            }

            @Override
            public void shareCancel() {
                ToastUtils.showToast("取消分享");

            }
        };
    }

    @Override
    public int intLayoutId() {
        return R.layout.layout_bottom_share;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        // 微信分享
        holder.setOnClickListener(R.id.share_wx, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareMedia(getContext(), SharePlatform.WX, "Title", "summary", "http://www.google.com", "http://shaohui.me/images/avatar.gif", mShareListener);
                dismiss();
            }
        });
        // 朋友圈
        holder.setOnClickListener(R.id.share_wx_timeline, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareText(getContext(), SharePlatform.WX_TIMELINE, "测试分享文字", mShareListener);
                dismiss();
            }
        });
        // 微博
        holder.setOnClickListener(R.id.share_weibo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareImage(getContext(), SharePlatform.WEIBO, "http://shaohui.me/images/avatar.gif", mShareListener);
                dismiss();
            }
        });
        // qq
        holder.setOnClickListener(R.id.share_qq, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareImage(getContext(), SharePlatform.QQ, "http://shaohui.me/images/avatar.gif", mShareListener);
                dismiss();
            }
        });
        // 空间
        holder.setOnClickListener(R.id.share_qzone, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareMedia(getContext(), SharePlatform.QZONE, "Title", "summary", "http://www.google.com", "http://shaohui.me/images/avatar.gif", mShareListener);
                dismiss();
            }
        });


    }
}
