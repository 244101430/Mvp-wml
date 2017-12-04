package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerFragment;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.home.index.IndexFragment;
import com.lantu.andorid.mvp_wml.ui.home.index.IndexPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/10/10.
 */

@Module
public class IndexModule {
    private final IndexFragment mIndexView;
    private final String mNewId;

    public IndexModule(IndexFragment mIndexView, String mNewId) {
        this.mIndexView = mIndexView;
        this.mNewId = mNewId;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter(){
        return new IndexPresenter(mIndexView, mNewId);
    }
}
