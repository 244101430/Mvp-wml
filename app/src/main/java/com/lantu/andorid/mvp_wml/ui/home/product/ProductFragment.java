package com.lantu.andorid.mvp_wml.ui.home.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerProductFragmentComponment;
import com.lantu.andorid.mvp_wml.injector.modules.ProductFragmentModule;
import com.lantu.andorid.mvp_wml.rxbus.event.TestEvent;
import com.lantu.andorid.mvp_wml.ui.base.BaseFragment;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IRxBusPresenter;
import com.lantu.andorid.mvp_wml.ui.home.index.IndexFragment;
import com.lantu.andorid.mvp_wml.utils.IconUtils;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * create an instance of this fragment.
 */
public class ProductFragment extends BaseFragment<IRxBusPresenter> implements IProductFragmentView {
    @BindView(R.id.textView)
    TextView textView;

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initInjector() {
        DaggerProductFragmentComponment.builder()
                .applicationComponent(getAppComponent())
                .productFragmentModule(new ProductFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        mPresenter.registerRxBus(TestEvent.class, new Action1<TestEvent>() {
            @Override
            public void call(TestEvent testEvent) {
                if (testEvent.checkStatus == TestEvent.CHECK_PRODUCTFRAGMENT) {
                    if (textView != null) {
                        textView.setText("TestEvent.CHECK_PRODUCTFRAGMENT");
                    }
                }
            }
        });
        if (textView != null) {
            textView.setText("修改图标二");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.getData(false);
                }
            });
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadPageData(String str) {
        if (textView != null) {
            textView.setText(str);
        }
    }
}
