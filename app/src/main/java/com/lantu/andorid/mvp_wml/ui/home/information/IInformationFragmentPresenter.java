package com.lantu.andorid.mvp_wml.ui.home.information;

import com.lantu.andorid.mvp_wml.ui.base.IBasePresenter;

/**
 * Created by wml8743 on 2017/12/8.
 */

public interface IInformationFragmentPresenter<T> extends IBasePresenter{

    /**
     * 更新理财页数据
     */
    void refreshProductView();
}
