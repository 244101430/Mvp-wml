package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerFragment;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IRxBusPresenter;
import com.lantu.andorid.mvp_wml.ui.home.information.InformationFragmentPresenter;
import com.lantu.andorid.mvp_wml.ui.home.product.ProductFragment;
import com.lantu.andorid.mvp_wml.ui.home.product.ProductFragmentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/12/7.
 */
@Module
public class ProductFragmentModule {
    private final ProductFragment mView;

    public ProductFragmentModule(ProductFragment mView) {
        this.mView = mView;
    }

    @PerFragment
    @Provides
    public IRxBusPresenter providePresenter(RxBus rxBus){
        return new ProductFragmentPresenter(mView, rxBus);
    }
}
