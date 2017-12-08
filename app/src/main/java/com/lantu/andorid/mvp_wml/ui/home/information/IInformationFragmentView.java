package com.lantu.andorid.mvp_wml.ui.home.information;

import com.lantu.andorid.mvp_wml.api.bean.NewsInfo;
import com.lantu.andorid.mvp_wml.ui.base.IBaseView;

/**
 * Created by wml8743 on 2017/12/7.
 */

public interface IInformationFragmentView extends IBaseView{

    /**
     * 加载页面数据
     * @param str
     */
    void loadPageData(String str);

}
