package com.lantu.andorid.mvp_wml.ui.screenshot;

import android.graphics.Bitmap;

import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

/**
 * Created by wml8743 on 2017/10/18.
 */

public interface IScreenshotView extends IBaseView {

    /**
     * 显示图片
     * @param bitmap
     */
    void showImageView(Bitmap bitmap);
}
