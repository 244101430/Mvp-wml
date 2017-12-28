package com.lantu.andorid.mvp_wml.ui.patternlock;


import com.andrognito.patternlockview.PatternLockView;
import com.lantu.andorid.mvp_wml.utils.StringUtils;

/**
 * 手势密码
 * Created by wml on 2017/10/16.
 */

public class PatternlockPresenter implements IPatternockPresenter{
    private IPatternlockView mView;


    public PatternlockPresenter(IPatternlockView mView) {
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }

    private String passwrod = "";

    @Override
    public void setPassword(String pattern) {

        if (StringUtils.isNotEmpty(pattern)){
            if (pattern.length()>=4){
                if (StringUtils.isEmpty(passwrod)){
                    passwrod = pattern;
                    mView.showToast("再试一次");
                    mView.cleanPatternLockView();
                }else {
                    if (pattern.equals(passwrod)){
                        mView.showToast("设置成功");
                        mView.cleanPatternLockView();
                        passwrod = "";
                        // 跳转MainActivity
                        mView.launcherMainActivity();
                    }else {
                       mView.setPatternLockViewMode(PatternLockView.PatternViewMode.WRONG);
                        mView.showToast("两次不一致");
                    }
                }
            }else {
                mView.showToast("至少经过4个点");
                mView.cleanPatternLockView();
            }
        }
    }
}
