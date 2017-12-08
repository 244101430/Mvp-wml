package com.lantu.andorid.mvp_wml.ui.home.product;

import com.lantu.andorid.mvp_wml.api.RetrofitService;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;

import rx.Subscriber;

/**
 * Created by wml8743 on 2017/12/7.
 */

public class ProductFragmentPresenter implements IBasePresenter{
    private IProductFragmentView mView;

    public ProductFragmentPresenter(IProductFragmentView mView) {
        this.mView = mView;
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
}
