package com.lantu.andorid.mvp_wml.injector.modules;

import android.content.Context;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.ui.share.ISharePresenter;
import com.lantu.andorid.mvp_wml.ui.share.IShareView;
import com.lantu.andorid.mvp_wml.ui.share.SharaPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/10/19.
 */

@Module
public class ShareModule {
    private final IShareView mView;

    public ShareModule(IShareView mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public ISharePresenter providePersenter(Context context){
        return new SharaPresenter(mView, context);
    }
}
