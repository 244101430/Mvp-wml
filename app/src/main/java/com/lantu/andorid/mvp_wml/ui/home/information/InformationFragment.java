package com.lantu.andorid.mvp_wml.ui.home.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerInformationFragmentComponent;
import com.lantu.andorid.mvp_wml.injector.modules.InformationFragmentModule;
import com.lantu.andorid.mvp_wml.ui.base.BaseFragment;
import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;
import com.lantu.andorid.mvp_wml.utils.IconUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * create an instance of this fragment.
 */
public class InformationFragment extends BaseFragment<IBasePresenter> implements IInformationFragmentView{

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
        DaggerInformationFragmentComponent.builder()
                .informationFragmentModule(new InformationFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        if (textView != null){
            textView.setText("重置默认图标");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
        if (textView != null){
            try {
                JSONArray jsonArray = new JSONArray(str);
                textView.setText("Noah请求demo 当前时间：" + System.currentTimeMillis() + "    " + jsonArray.toString());
            } catch (JSONException e) {
                textView.setText("Noah请求demo 当前时间：" + System.currentTimeMillis() + "    " + str);
            }

        }
    }
}
