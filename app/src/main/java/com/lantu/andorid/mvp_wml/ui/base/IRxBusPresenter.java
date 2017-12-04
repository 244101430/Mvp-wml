package com.lantu.andorid.mvp_wml.ui.base;

import rx.functions.Action1;

/**
 * Created by wml8743 on 2017/8/31.
 * RxBus Presenter
 */
public interface IRxBusPresenter extends IBasePresenter {

    /**
     * 注册
     * @param eventType
     * @param <T>
     */
    <T> void registerRxBus(Class<T> eventType, Action1<T> action);

    /**
     * 注销
     */
    void unregisterRxBus();
}

