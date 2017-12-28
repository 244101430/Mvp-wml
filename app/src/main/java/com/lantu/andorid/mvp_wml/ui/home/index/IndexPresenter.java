package com.lantu.andorid.mvp_wml.ui.home.index;

import com.lantu.andorid.mvp_wml.api.RetrofitService;
import com.lantu.andorid.mvp_wml.api.bean.NewsInfo;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by wml on 2017/10/10.
 */

public class IndexPresenter implements IBasePresenter {

    private IIndexView mView;
    private String mNewsId;
    private int mPage = 1;

    public IndexPresenter(IIndexView mView, String mNewsId) {
        this.mView = mView;
        this.mNewsId = mNewsId;
    }

    @Override
    public void getData(final boolean isRefresh) {
        RetrofitService.getNewsList(mNewsId, mPage)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .subscribe(new Subscriber<List<NewsInfo>>() {
                    @Override
                    public void onCompleted() {


                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPage = 0;

                        if (isRefresh) {
                            mView.finishRefresh();
                            // 可以提示对应的信息，但不更新界面
                            ToastUtils.showToast("刷新失败提示什么根据实际情况");
                        } else {
                            mView.showNetError();
                        }
                    }

                    @Override
                    public void onNext(List<NewsInfo> newsInfos) {
                        mView.loadData(newsInfos);
                        mPage++;
                    }
                })
        ;
    }

    @Override
    public void getMoreData() {

    }
}
