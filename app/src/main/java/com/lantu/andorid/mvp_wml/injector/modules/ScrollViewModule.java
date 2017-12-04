package com.lantu.andorid.mvp_wml.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.ui.adapter.ScrollViewAdapter;
import com.lantu.andorid.mvp_wml.ui.adapter.ViewPagerAdapter;
import com.lantu.andorid.mvp_wml.ui.other.IScrollViewPresenter;
import com.lantu.andorid.mvp_wml.ui.other.ScrollViewActivity;
import com.lantu.andorid.mvp_wml.ui.other.ScrollViewPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/10/18.
 */
@Module
public class ScrollViewModule {
    private final ScrollViewActivity mView;

    public ScrollViewModule(ScrollViewActivity mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IScrollViewPresenter providePresenter() {
        return new ScrollViewPresenter(mView);
    }

    @PerActivity
    @Provides
    public ViewPagerAdapter provideAdapter(){
        return new ViewPagerAdapter(mView.getSupportFragmentManager());
    }
}
