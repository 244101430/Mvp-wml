package com.lantu.andorid.mvp_wml.ui.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lantu.andorid.mvp_wml.AndroidApplication;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.ApplicationComponent;
import com.lantu.andorid.mvp_wml.injector.modules.ActivityModule;
import com.lantu.andorid.mvp_wml.receiver.NetBroadcastReceiver;
import com.lantu.andorid.mvp_wml.ui.home.MainActivity;
import com.lantu.andorid.mvp_wml.utils.NavUtils;
import com.lantu.andorid.mvp_wml.utils.NetUtil;
import com.lantu.andorid.mvp_wml.utils.StringUtils;
import com.lantu.andorid.mvp_wml.utils.SwipeRefreshHelper;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;
import com.lantu.andorid.mvp_wml.widget.EmptyLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wml8743 on 2017/8/31.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView, EmptyLayout.OnRetryListener, NetBroadcastReceiver.NetEvevt {

    /**
     * 把 Presenter 提取到基类需要配合基类的 initInjector() 进行注入，如果继承这个基类则必定要提供一个 Presenter 注入方法，
     * 该APP所有 Presenter 都是在 Module 提供注入实现，也可以选择提供另外不带 Presenter 的基类
     */
    @Inject
    protected T mPresenter;

    protected Context mContext;

    public static NetBroadcastReceiver.NetEvevt evevt;

    /**
     * 把 EmptyLayout 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;

    @Nullable
    @BindView(R.id.common_head_toolbar)
    protected Toolbar mToolBar;

    /**
     * 刷新控件，注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;
    @Nullable
    @BindView(R.id.common_head_net_status)
    protected TextView mNetView;    // 无网络状态提醒
    @Nullable
    @BindView(R.id.common_head_back)
    protected ImageView mIvCommonBack;    // 返回按钮
    @Nullable
    @BindView(R.id.common_head_finish_ll)
    protected LinearLayout mLlCommonColse;    // 关闭按钮布局
    @Nullable
    @BindView(R.id.common_head_finish)
    protected ImageView mIvCommonColse;    // 关闭按钮
    @Nullable
    @BindView(R.id.common_head_right1)
    protected ImageView mIvCommonRight1;    // 右边第一个按钮
    @Nullable
    @BindView(R.id.common_head_right2_ll)
    protected LinearLayout mLlCommonRight2;    // 右边第二个按钮布局
    @Nullable
    @BindView(R.id.common_head_right2)
    protected ImageView mIvCommonRight2;    // 右边第二个按钮


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        mContext = this;
        evevt = this;
        ButterKnife.bind(this);
        initToolBar();
        initInjector();
        initView();
        updateViews(false);
        onNetChange(NetUtil.isNetworkAvailable(this));
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
//        mNetView = View.inflate(this, R.layout.base_activity_netstates, null);
        LinearLayout rl = new LinearLayout(this);
//        rl.addView(mNetView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rl.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.setOrientation(LinearLayout.VERTICAL);
        super.setContentView(rl, params);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        initSwipeRefresh();
        // 按钮点击事件
        onClickByView(mIvCommonBack);
        onClickByView(mLlCommonColse);
        onClickByView(mIvCommonColse);
        onClickByView(mIvCommonRight1);
        onClickByView(mIvCommonRight2);
        onClickByView(mLlCommonRight2);
        initViews();
    }

    /**
     * 初始化状态栏
     */
    private void initToolBar() {
        // 判断当前系统版本
        // 分为俩个区间 1. 大于等于4.4 小于5.0 2.大于等于5.0
        // 原因 4.4没有提供api 需要单独设置 而 5.0提供相关api
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏为透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置底部虚拟导航栏为透明
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (mToolBar != null) {
            setOrChangeTranslucentColor(mToolBar, getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * 主要针对ToolBar进行设置
     *
     * @param toolbar
     * @param translucentPrimaryColor
     */
    public void setOrChangeTranslucentColor(Toolbar toolbar, int translucentPrimaryColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 大于等于4.4
            if (toolbar != null) {
                // 动态获取当前系统状态栏高度 累加
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
                int statusBarHeight = NavUtils.getStatusBarHeight(this);
                params.height += statusBarHeight;
                toolbar.setLayoutParams(params);
                // 设置padding 预防状态栏遮挡ToolBar
                toolbar.setPadding(toolbar.getPaddingLeft(),
                        toolbar.getTop() + statusBarHeight,
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
                // 设置顶部颜色
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 通过api设置状态栏颜色
                getWindow().setStatusBarColor(translucentPrimaryColor);
                // 设置底部虚拟导航栏颜色
//            getWindow().setNavigationBarColor(translucentPrimaryColor);
            } else {
                // 设置顶部颜色
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }

        } else {
            // 小于4.4 不做处理
        }
    }


    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
        }
    }

    @Override
    public void showNetError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(this);
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void onRetry() {
        updateViews(false);
    }


    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews(true);
                }
            });
        }
    }

    /**
     * 获取 ApplicationComponent
     *
     * @return ApplicationComponent
     */
    protected ApplicationComponent getAppComponent() {
        return AndroidApplication.getAppComponent();
//        return ((AndroidApplication) getApplication()).getAppComponent();
    }

    /**
     * 获取 ActivityModule
     *
     * @return ActivityModule
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            if (this instanceof MainActivity) {//防止首页切换fragment被移除
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(containerViewId, getSupportFragmentManager().findFragmentByTag(tag));
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            } else {
                // 存在则弹出在它上面的所有fragment，并显示对应fragment
                getSupportFragmentManager().popBackStack(tag, 0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNetChange(boolean isNet) {
        if (isNet) {
            if (mNetView != null)
                mNetView.setVisibility(View.GONE);
        } else {
            if (mNetView != null)
                mNetView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast(String message) {
        if (StringUtils.isNotEmpty(message)) {
            ToastUtils.showToast(message);
        }
    }

    @Override
    public void showToast(int resId) {
        showToast(getResources().getString(resId));
    }


    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * Dagger 注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);

    /**
     * 顶部按钮点击事件
     *
     * @param view
     */
    public void onClickByView(View view) {
        if (view == null) return;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.common_head_back:
                    case R.id.common_head_finish_ll:
                    case R.id.common_head_finish:
                        finish();
                        break;
                    case R.id.common_head_right1:
                    case R.id.common_head_right2_ll:
                        showToast("右边第一个按钮");
                        break;
                    case R.id.common_head_right2:
                        showToast("右边第二个按钮");
                        break;
                }
            }
        });

    }

    /**
     * 显示关闭按钮
     */
    public void showFinishView() {
        if (mLlCommonColse != null) {
            mLlCommonColse.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示右边第一个按钮
     */
    public void showRight1View() {
        if (mIvCommonRight1 != null) {
            mIvCommonRight1.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示右边第二个按钮
     */
    public void showRight2hView() {
        if (mLlCommonRight2 != null) {
            mLlCommonRight2.setVisibility(View.VISIBLE);
        }
    }
}
