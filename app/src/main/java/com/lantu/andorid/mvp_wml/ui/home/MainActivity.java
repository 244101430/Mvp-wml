package com.lantu.andorid.mvp_wml.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.ui.base.BaseActivity;
import com.lantu.andorid.mvp_wml.ui.home.index.IndexFragment;
import com.lantu.andorid.mvp_wml.ui.home.information.InformationFragment;
import com.lantu.andorid.mvp_wml.ui.home.mine.MineFragment;
import com.lantu.andorid.mvp_wml.ui.home.product.ProductFragment;
import com.lantu.andorid.mvp_wml.utils.Logger;
import com.lantu.andorid.mvp_wml.widget.TabEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 程序入口
 */
public class MainActivity extends BaseActivity {

    private long mExitTime = 0;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @BindView(R.id.commontablayout)
    CommonTabLayout tabLayout;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    replaceFragment(R.id.fl_container, IndexFragment.newInstance(), IndexFragment.class.getSimpleName());
                    break;
                case 1:
                    replaceFragment(R.id.fl_container, ProductFragment.newInstance(), ProductFragment.class.getSimpleName());
                    break;
                case 2:
                    replaceFragment(R.id.fl_container, InformationFragment.newInstance(), InformationFragment.class.getSimpleName());
                    break;
                case 3:
                    replaceFragment(R.id.fl_container, MineFragment.newInstance(), MineFragment.class.getSimpleName());
                    break;
            }
            return true;
        }
    });


    /**
     * 启动函数
     * @param activity
     */
    public static void laucher(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
//        initSystemBarColor(getResources().getColor(R.color.colorAccent));
        mTabEntities.add(new TabEntity("首页", R.drawable.ic_index_tab_1_, R.drawable.ic_index_tab_1));
        mTabEntities.add(new TabEntity("理财", R.drawable.ic_index_tab_2_, R.drawable.ic_index_tab_2));
        mTabEntities.add(new TabEntity("资讯", R.drawable.ic_index_tab_3_, R.drawable.ic_index_tab_3));
        mTabEntities.add(new TabEntity("我的", R.drawable.ic_index_tab_4_, R.drawable.ic_index_tab_4));
        tabLayout.setTabData(mTabEntities);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mHandler.sendEmptyMessage(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mHandler.sendEmptyMessage(0);

        //显示未读红点
        tabLayout.showDot(2);

    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }


    @Override
    public void onBackPressed() {
        _exit();
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Logger.i("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
