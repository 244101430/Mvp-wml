package com.lantu.andorid.mvp_wml.ui.home.information;

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
public class InformationFragment extends BaseFragment<IBasePresenter> {

    @BindView(R.id.textView)
    TextView textView;

    public static InformationFragment newInstance() {
        InformationFragment fragment = new InformationFragment();
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
        return R.layout.fragment_information;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        if (textView != null){
            textView.setText("重置默认图标");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IconUtils.switchIcon(getContext(), IconUtils.ICON_DEFAULT);
                }
            });
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

}
