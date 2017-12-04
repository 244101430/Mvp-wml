package com.lantu.andorid.mvp_wml.ui.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.injector.components.DaggerScreenshotComponent;
import com.lantu.andorid.mvp_wml.injector.modules.ScreenshotModule;
import com.lantu.andorid.mvp_wml.ui.base.BaseSwipeBackActivity;
import com.lantu.andorid.mvp_wml.widget.CertificateView;

import butterknife.BindView;

/**
 * 截图
 */
public class ScreenshotActivity extends BaseSwipeBackActivity<IScreenshotPresenter> implements IScreenshotView {

    @BindView(R.id.scrollView)
    protected ScrollView mScrollView;
    @BindView(R.id.bt_screenshot)
    protected Button mBtScreenshot;
    @BindView(R.id.image_view)
    protected ImageView mImageView;
    @BindView(R.id.certificateview)
    protected CertificateView mCertificateView;

    public static final void launcher(Activity activity){
        activity.startActivity(new Intent(activity, ScreenshotActivity.class));
        activity.overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_screenshot;
    }

    @Override
    protected void initInjector() {
        DaggerScreenshotComponent.builder()
                .screenshotModule(new ScreenshotModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        mBtScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.shotScrollView(mCertificateView);
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 显示图片
     * @param bitmap
     */
    @Override
    public void showImageView(Bitmap bitmap) {
        if (bitmap != null){
            mImageView.setImageBitmap(bitmap);
        }
    }
}
