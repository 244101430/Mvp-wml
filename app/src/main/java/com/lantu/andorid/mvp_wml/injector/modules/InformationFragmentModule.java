package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerFragment;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.home.information.IInformationFragmentPresenter;
import com.lantu.andorid.mvp_wml.ui.home.information.InformationFragment;
import com.lantu.andorid.mvp_wml.ui.home.information.InformationFragmentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/12/7.
 */
@Module
public class InformationFragmentModule {
    private final InformationFragment mInfromationFragmentView;

    public InformationFragmentModule(InformationFragment mInfromationFragmentView) {
        this.mInfromationFragmentView = mInfromationFragmentView;
    }

    @PerFragment
    @Provides
    public IInformationFragmentPresenter providePresenter(RxBus rxBus){
        return new InformationFragmentPresenter(mInfromationFragmentView, rxBus);
    }
}
