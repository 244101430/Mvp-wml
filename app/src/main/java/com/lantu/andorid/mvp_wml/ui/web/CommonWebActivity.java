package com.lantu.andorid.mvp_wml.ui.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.just.agentwebX5.AgentWeb;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;

import butterknife.BindView;

/**
 * webX5浏览器
 */
public class CommonWebActivity extends BaseSwipeBackActivity<IBasePresenter> {

    @BindView(R.id.container_framelayout)
    protected FrameLayout mFrameLayout;
    private AgentWebFragment mAgentWebFragment;

    /**
     * 启动方法  CommonWebActivity.launcer(getActivity(), "http://www.vip.com");
     * @param activity
     * @param url
     */
    public final static void launcer(Activity activity, String url){
        activity.startActivity(new Intent(activity, CommonWebActivity.class).putExtra(AgentWebFragment.URL_KEY, url));
        activity.overridePendingTransition(R.anim.slide_right_entry,R.anim.hold);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Bundle mBundle = new Bundle();
        mBundle.putString(AgentWebFragment.URL_KEY, getIntent().getStringExtra(AgentWebFragment.URL_KEY));
        mAgentWebFragment = AgentWebFragment.getInstance(mBundle);
        addFragment(R.id.container_framelayout,mAgentWebFragment, AgentWebFragment.class.getName());
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void onBackPressed() {
        AgentWeb web = mAgentWebFragment.getWebView();
        if (web!=null){
            if (web.back()){
                return;
            }
        }
       finish();
    }
}
