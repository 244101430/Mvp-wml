package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerFragment;
import com.lantu.andorid.mvp_wml.injector.modules.IndexModule;
import com.lantu.andorid.mvp_wml.ui.home.index.IndexFragment;

import dagger.Component;

/**
 * Created by wml8743 on 2017/10/10.
 */

@PerFragment
@Component(modules = IndexModule.class)
public interface IndexComponent {
    void inject(IndexFragment fragment);
}
