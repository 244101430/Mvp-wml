package com.lantu.andorid.mvp_wml.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lantu.andorid.mvp_wml.api.bean.ScrollViewBean;
import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.PerFragment;
import com.lantu.andorid.mvp_wml.ui.adapter.ScrollViewAdapter;
import com.lantu.andorid.mvp_wml.ui.adapter.ViewPagerAdapter;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.other.ListFragmentPresenter;
import com.lantu.andorid.mvp_wml.ui.other.ListFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml on 2017/12/4.
 */
@Module
public class ListFragmentModule {
    private final ListFragment mListFragment;

    public ListFragmentModule(ListFragment mListFragment) {
        this.mListFragment = mListFragment;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter(){
        return new ListFragmentPresenter(mListFragment);
    }


    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter(){
        return new ScrollViewAdapter();
    }
}
