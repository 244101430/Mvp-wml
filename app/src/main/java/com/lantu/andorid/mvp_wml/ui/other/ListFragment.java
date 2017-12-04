package com.lantu.andorid.mvp_wml.ui.other;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.api.bean.ScrollViewBean;
import com.lantu.andorid.mvp_wml.injector.components.DaggerIndexComponent;
import com.lantu.andorid.mvp_wml.injector.components.DaggerListFragmentComponent;
import com.lantu.andorid.mvp_wml.injector.modules.IndexModule;
import com.lantu.andorid.mvp_wml.injector.modules.ListFragmentModule;
import com.lantu.andorid.mvp_wml.ui.adapter.ScrollViewAdapter;
import com.lantu.andorid.mvp_wml.ui.adapter.ViewPagerAdapter;
import com.lantu.andorid.mvp_wml.ui.base.BaseFragment;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;
import com.lantu.andorid.mvp_wml.ui.home.index.IndexFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment<IBasePresenter> implements IBaseView {

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    List<ScrollViewBean> mDatas = new ArrayList<>();

    @Inject
    protected BaseQuickAdapter adapter;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initInjector() {
        DaggerListFragmentComponent.builder()
                .listFragmentModule(new ListFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        for (int i = 0; i < 50; i++) {
            mDatas.add(new ScrollViewBean());
        }
        adapter.addData(mDatas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

}
