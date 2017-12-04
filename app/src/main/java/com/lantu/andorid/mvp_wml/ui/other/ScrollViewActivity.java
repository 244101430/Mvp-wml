package com.lantu.andorid.mvp_wml.ui.other;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerScrollViewComponent;
import com.lantu.andorid.mvp_wml.injector.modules.ScrollViewModule;
import com.lantu.andorid.mvp_wml.ui.adapter.ViewPagerAdapter;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import javax.inject.Inject;
import butterknife.BindView;

/**
 * 上滑悬浮
 */
public class ScrollViewActivity extends BaseSwipeBackActivity<IScrollViewPresenter> implements IScrollView{

    @BindView(R.id.viewpager)
    protected ViewPager mViewPager;
    @BindView(R.id.tabLayout_main)
    protected TabLayout mTabLayout;
    @Inject
    protected ViewPagerAdapter adapter;

    /**
     * 启动函数
     * @param activity
     */
    public static final void launcher(Activity activity){
        BaseSwipeBackActivity.laucher(activity, ScrollViewActivity.class);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_scroll_view;
    }

    @Override
    protected void initInjector() {
        DaggerScrollViewComponent
                .builder()
                .scrollViewModule(new ScrollViewModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        adapter.addFragment(new ListFragment(), "1");
        adapter.addFragment(new ListFragment(), "2");
        adapter.addFragment(new ListFragment(), "3");

        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

        if (!isRefresh) return;
        finishRefresh();
    }
}
