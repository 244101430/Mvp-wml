package com.lantu.andorid.mvp_wml.injector.modules;

import com.lantu.andorid.mvp_wml.injector.PerActivity;
import com.lantu.andorid.mvp_wml.ui.patternlock.IPatternlockView;
import com.lantu.andorid.mvp_wml.ui.patternlock.IPatternockPresenter;
import com.lantu.andorid.mvp_wml.ui.patternlock.PatternlockPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wml8743 on 2017/10/16.
 */
@Module
public class PatternockModule {
    private final IPatternlockView mView;

    public PatternockModule(IPatternlockView mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IPatternockPresenter providePresenter(){
        return new PatternlockPresenter(mView);
    }
}
