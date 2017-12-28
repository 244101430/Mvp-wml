package com.lantu.andorid.mvp_wml.ui.home.index;

import com.lantu.andorid.mvp_wml.api.bean.NewsInfo;
import com.lantu.andorid.mvp_wml.ui.base.ILoadDataView;

import java.util.List;

/**
 * 首页视图接口
 * Created by wml on 2017/10/10.
 */

interface IIndexView extends ILoadDataView<List<NewsInfo>>{

    /**
     * 加载页面数据
     * @param newBean
     */
    void loadPageData(NewsInfo newBean);

}
