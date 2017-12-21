package com.lantu.andorid.mvp_wml.ui.home.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.api.bean.NewsInfo;
import com.lantu.andorid.mvp_wml.injector.components.DaggerIndexComponent;
import com.lantu.andorid.mvp_wml.injector.modules.IndexModule;
import com.lantu.andorid.mvp_wml.ui.audio.AudioActivity;
import com.lantu.andorid.mvp_wml.ui.base.BaseFragment;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.test.download.DownloadActivity;


import java.util.List;

import butterknife.BindView;

/**
 * create an instance of this fragment.
 */
public class IndexFragment extends BaseFragment<IBasePresenter> implements IIndexView {

    @BindView(R.id.textView)
    TextView textView;

    private String mNewsId;

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsId = "T1348647909107";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initInjector() {
        DaggerIndexComponent.builder()
                .indexModule(new IndexModule(this, mNewsId))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mPresenter.getData(true);
//                ShareActivity.launcher(getActivity());
//                CommonWebActivity.launcer(getActivity(), "http://zhangwenyue.kingifa.com/src/static/VenuePreheating/Meeting.html");

//                IconSwitchActivity.launcher(getActivity());

//                ScrollViewActivity.launcher(getActivity());

//                AudioActivity.launcher(getActivity());

                DownloadActivity.launcher(getActivity());

            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<NewsInfo> data) {
        textView.setText(new Gson().toJson(data));
    }

    @Override
    public void loadMoreData(List<NewsInfo> data) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    public void loadPageData(NewsInfo newBean) {

    }
}
