package com.lantu.andorid.mvp_wml.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lantu.andorid.mvp_wml.R;
import com.lantu.andorid.mvp_wml.api.bean.ScrollViewBean;

/**
 * Created by wml8743 on 2017/11/17.
 */

public class ScrollViewAdapter extends BaseQuickAdapter<ScrollViewBean, BaseViewHolder> {

    public ScrollViewAdapter() {
        super(R.layout.layout_scrollview_recycleview_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScrollViewBean item) {

    }
}
