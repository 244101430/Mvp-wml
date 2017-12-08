package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerFragment;
import com.lantu.andorid.mvp_wml.injector.modules.InformationFragmentModule;
import com.lantu.andorid.mvp_wml.ui.home.index.IndexFragment;
import com.lantu.andorid.mvp_wml.ui.home.information.InformationFragment;

import dagger.Component;

/**
 * Created by wml8743 on 2017/12/7.
 */
@PerFragment
@Component(modules = InformationFragmentModule.class)
public interface InformationFragmentComponent {
    void inject(InformationFragment fragment);
}
