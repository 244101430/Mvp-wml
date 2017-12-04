package com.lantu.andorid.mvp_wml.ui.patternlock;

import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

/**
 * Created by wml8743 on 2017/10/16.
 */

public interface IPatternlockView extends IBaseView {

    /**
     *  清除手势视图
     */
    void cleanPatternLockView();

    /**
     * 设置
     * @param mode
     */
    void setPatternLockViewMode(int mode);

    /**
     * 跳转MainActivity
     */
    void launcherMainActivity();


}
