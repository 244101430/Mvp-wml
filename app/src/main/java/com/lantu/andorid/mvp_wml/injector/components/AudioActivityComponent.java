package com.lantu.andorid.mvp_wml.injector.components;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.injector.modules.AudioActivityMoudle;
import com.lantu.andorid.mvp_wml.ui.audio.AudioActivity;

import dagger.Component;

/**
 * Created by wml8743 on 2017/12/11.
 */
@PerActivity
@Component(modules = AudioActivityMoudle.class)
public interface AudioActivityComponent {
    void injector(AudioActivity activity);
}
