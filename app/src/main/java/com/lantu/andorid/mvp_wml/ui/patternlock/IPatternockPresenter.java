package com.lantu.andorid.mvp_wml.ui.patternlock;


import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;

/**
 * Created by wml8743 on 2017/10/16.
 */

public interface IPatternockPresenter<T> extends IBasePresenter {
    void setPassword(String pattern);
}
