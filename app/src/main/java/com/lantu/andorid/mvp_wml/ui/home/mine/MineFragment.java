package com.lantu.andorid.mvp_wml.ui.home.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.ui.base.BaseFragment;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;

import butterknife.BindView;

/**
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment<IBasePresenter> {
    @BindView(R.id.textView)
    TextView textView;


    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
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
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        if (textView != null){
            textView.setText("MineFragment");
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

}
