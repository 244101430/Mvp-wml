package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerFragment;
import com.lantu.andorid.mvp_wml.injector.modules.ListFragmentModule;
import com.lantu.andorid.mvp_wml.ui.other.ListFragment;

import dagger.Component;

/**
 * Created by wml8743 on 2017/12/4.
 */
@PerFragment
@Component(modules = ListFragmentModule.class)
public interface ListFragmentComponent {
    void inject(ListFragment fragment);
}
