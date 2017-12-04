package com.lantu.andorid.mvp_wml.ui.home.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.ui.base.BaseFragment;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.utils.IconUtils;

import butterknife.BindView;

/**
 * create an instance of this fragment.
 */
public class ProductFragment extends BaseFragment<IBasePresenter> {
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

    }

    @Override
    protected void initViews() {
        if (textView != null){
            textView.setText("修改图标二");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IconUtils.switchIcon(getContext(), IconUtils.ICON_TAG_1212);
                }
            });
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

}
