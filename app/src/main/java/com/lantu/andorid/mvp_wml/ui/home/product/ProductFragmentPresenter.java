package com.lantu.andorid.mvp_wml.ui.home.product;

import com.lantu.andorid.mvp_wml.api.RetrofitService;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;
import com.lantu.andorid.mvp_wml.ui.base.IRxBusPresenter;
import com.lantu.andorid.mvp_wml.utils.Logger;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by wml on 2017/12/7.
 */

public class ProductFragmentPresenter implements IRxBusPresenter{
    private final IProductFragmentView mView;
    private final RxBus mRxBus;

    public ProductFragmentPresenter(IProductFragmentView mView, RxBus mRxBus) {
        this.mView = mView;
        this.mRxBus = mRxBus;
    }


    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getLanTu()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        mView.loadPageData(s);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = mRxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.e(throwable.toString());
            }
        });
        mRxBus.addSubscription(this, subscription);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}
