package com.lantu.andorid.mvp_wml.ui.home.information;

import com.lantu.andorid.mvp_wml.api.RetrofitService;
import com.lantu.andorid.mvp_wml.rxbus.RxBus;
import com.lantu.andorid.mvp_wml.rxbus.event.TestEvent;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;

import org.json.JSONException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by wml8743 on 2017/12/7.
 */

public class InformationFragmentPresenter implements IInformationFragmentPresenter{

    private IInformationFragmentView mView;
    private RxBus mRxBus;

    public InformationFragmentPresenter(IInformationFragmentView mView, RxBus rxBus) {
        this.mView = mView;
        this.mRxBus = rxBus;
    }

    @Override
    public void getData(boolean isRefresh) {
        // 获取数据
        try {
            RetrofitService.getNoahStr()
                    .subscribe(new Subscriber<String>(){
                        @Override
                        public void onCompleted() {
                            System.out.println();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        mView.loadPageData("测试数据");
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void refreshProductView() {

        // 更新
        mRxBus.post(new TestEvent(TestEvent.CHECK_PRODUCTFRAGMENT));
    }
}
