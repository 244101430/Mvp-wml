package com.lantu.andorid.mvp_wml.rxbus.event;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wml on 2017/12/8.
 */

public class TestEvent {
    /**
     * 类型
     */
    public static final int CHECK_PRODUCTFRAGMENT = 300;
    public static final int CHECK_INDEXFRAGMENT = 301;

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({CHECK_PRODUCTFRAGMENT, CHECK_INDEXFRAGMENT})
    public @interface CheckStatus {
    }

    public int checkStatus = CHECK_PRODUCTFRAGMENT;

    public TestEvent() {
    }

    public TestEvent(@CheckStatus int checkStatus) {
        this.checkStatus = checkStatus;
    }
}
