package com.lantu.andorid.mvp_wml.utils;

import android.support.annotation.NonNull;

import com.lantu.andorid.mvp_wml.utils.baselog.KLog;


/**
 * ================================================================
 * log管理
 * ================================================================
 */
public final class Logger {

    private static boolean isInit = true;//是否初始化
    private static boolean isDebug = true;//是否输出日志

    /**
     * 私有化构造参数
     */
    private Logger() {
        throw new RuntimeException("当前实例不需要创建");
    }

    /**
     * 初始化日志打印
     *
     * @param TAG     日志输出标签
     * @param isDebug 是否输出日志
     */
    public static void init(@NonNull String TAG, @NonNull boolean isDebug) {
        KLog.init(isDebug, TAG);
        Logger.isDebug = isDebug;
        isInit = true;
    }

    public static void d(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) KLog.d(objectMsg);
    }

    public static void e(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) KLog.e(objectMsg);
    }

    public static void w(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) KLog.w(objectMsg);
    }

    public static void i(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) KLog.i(objectMsg);
    }

    private static void throwException() {
        throw new NullPointerException("日志未初始化,请调用init()方法初始化后再试!");
    }
}
