package com.lantu.andorid.mvp_wml.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.lantu.andorid.mvp_wml.ui.base.BaseActivity;
import com.lantu.andorid.mvp_wml.utils.NetUtil;

/**
 * Created by wml on 2017/10/13.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetEvevt evevt = BaseActivity.evevt;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            boolean netWorkState = NetUtil.isNetworkAvailable(context);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState);
        }
    }

    // 自定义接口
    public interface NetEvevt {
        public void onNetChange(boolean isNet);
    }
}
