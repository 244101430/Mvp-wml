package com.lantu.andorid.mvp_wml.ui.web;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentwebX5.AgentWeb;
import com.just.agentwebX5.ChromeClientCallbackManager;
import com.just.agentwebX5.DownLoadResultListener;
import com.just.agentwebX5.LogUtils;
import com.just.agentwebX5.WebDefaultSettingsManager;
import com.just.agentwebX5.WebSettings;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.ui.base.BaseFragment;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;

import static com.lantu.andorid.mvp_wml.R.id.iv_back;

//import static com.lantu.andorid.mvp_wml.R.id.iv_back;


/**
 * Created by cenxiaozhong on 2017/5/15.
 */
public class AgentWebFragment extends BaseFragment implements FragmentKeyDown, View.OnClickListener {


    @BindView(R.id.iv_back)
    protected ImageView mBackImageView;
    @BindView(R.id.view_line)
    protected View mLineView;
    @BindView(R.id.iv_finish)
    protected ImageView mFinishImageView;
    @BindView(R.id.toolbar_title)
    protected TextView mTitleTextView;
    protected AgentWeb mAgentWebX5;
    public static final String URL_KEY = "url_key";

    public static AgentWebFragment getInstance(Bundle bundle) {

        AgentWebFragment mAgentWebFragment = new AgentWebFragment();
        if (bundle != null)
            mAgentWebFragment.setArguments(bundle);

        return mAgentWebFragment;

    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_agentweb;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

        pageNavigator(View.GONE);
        mBackImageView.setOnClickListener(this);
        mFinishImageView.setOnClickListener(this);

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAgentWebX5 = AgentWeb.with(this)//
                .setAgentWebParent((ViewGroup) view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//
                .setIndicatorColorWithHeight(-1, 2)//
                .setWebSettings(getSettings())//
                .setWebViewClient(mWebViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setReceivedTitleCallback(mCallback)
                .setSecurityType(AgentWeb.SecurityType.strict)
                .addDownLoadResultListener(mDownLoadResultListener)
                .createAgentWeb()//
                .ready()//
                .go(getUrl());
    }


    /**
     * 下载监听
     */
    protected DownLoadResultListener mDownLoadResultListener = new DownLoadResultListener() {
        @Override
        public void success(String path) {

            Log.i("Info", "path:" + path);
            /*File mFile=new File(path);
            mFile.delete();*/
        }

        @Override
        public void error(String path, String resUrl, String cause, Throwable e) {

            Log.i("Info", "path:" + path + "  url:" + resUrl + "  couse:" + cause + "  Throwable:" + e);
        }
    };

    /**
     * web设置
     * @return
     */
    public WebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    /**
     * 获取URL
     * @return
     */
    public String getUrl() {
        String target = "";

        if (TextUtils.isEmpty(target = this.getArguments().getString(URL_KEY))) {
            target = "http://www.jd.com";
        }
        return target;
    }

    /**
     * 设置title
     */
    protected ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mTitleTextView != null && !TextUtils.isEmpty(title))
                if (title.length() > 1)
                    title = title.substring(0, 10) + "...";
            mTitleTextView.setText(title);

        }
    };

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    };
    protected com.tencent.smtt.sdk.WebViewClient mWebViewClient = new com.tencent.smtt.sdk.WebViewClient() {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }


        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            LogUtils.i("Info", "mWebViewClient shouldOverrideUrlLoading:" + url);
            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?vid=XODEzMjU1MTI4&refer=&tuid=&ua=Mozilla%2F5.0%20(Linux%3B%20Android%207.0%3B%20SM-G9300%20Build%2FNRD90M%3B%20wv)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Version%2F4.0%20Chrome%2F58.0.3029.83%20Mobile%20Safari%2F537.36&source=exclusive-pageload&cookieid=14971464739049EJXvh|Z6i1re#Intent;scheme=youku;package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            if (url.startsWith("intent://"))
                return true;
            else if (url.startsWith("youku"))
                return true;
//            else if(isAlipay(view,url))  //不需要，defaultWebClient内部会自动处理
//                return true;


            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Log.i("Info", "url:" + url + " onPageStarted  target:" + getUrl());
            if (url.equals(getUrl())) {
                pageNavigator(View.GONE);
            } else {
                if (mAgentWebX5.back())
                pageNavigator(View.VISIBLE);
            }

        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("Info", "onActivityResult -- >callback:" + requestCode + "   0x254:" + 0x254);
//        Log.i("Info","onActivityResult result");
        mAgentWebX5.uploadFileResult(requestCode, resultCode, data);
    }


    /**
     * 设置返回按钮
     * @param tag
     */
    private void pageNavigator(int tag) {

        Log.i("Info", "TAG:" + tag);
        mBackImageView.setVisibility(tag);
        mLineView.setVisibility(tag);
    }

    @Override
    public void onResume() {
        mAgentWebX5.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWebX5.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        return mAgentWebX5.handleKeyEvent(keyCode, event);
    }

    @Override
    public void onDestroyView() {
        mAgentWebX5.getWebLifeCycle().onDestroy();
        super.onDestroyView();
        //  mAgentWebX5.destroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case iv_back:
                if (!mAgentWebX5.back())
                    AgentWebFragment.this.getActivity().finish();
                break;
            case R.id.iv_finish:
                AgentWebFragment.this.getActivity().finish();
                break;
        }
    }

    /**
     * 获取web控件
     * @return
     */
    public AgentWeb getWebView(){
       return mAgentWebX5;
    }

}
