package com.lantu.andorid.mvp_wml.ui.base;

/**
 * Created by wml8743 on 2017/8/31.
 * 加载数据的界面接口
 */
public interface ILoadDataView<T> extends IBaseView {

    /**
     * 加载数据
     * @param data 数据
     */
    void loadData(T data);

    /**
     * 加载更多
     * @param data 数据
     */
    void loadMoreData(T data);

    /**
     * 没有数据
     */
    void loadNoData();
}

