package com.lantu.andorid.mvp_wml;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.dl7.tinkerlib.Log.MyLogImp;
import com.dl7.tinkerlib.util.TinkerManager;
import com.lantu.andorid.mvp_wml.api.RetrofitService;
import com.lantu.andorid.mvp_wml.injector.components.ApplicationComponent;
import com.lantu.andorid.mvp_wml.injector.components.DaggerApplicationComponent;
import com.lantu.andorid.mvp_wml.injector.modules.ApplicationModule;
import com.lantu.andorid.mvp_wml.utils.IconUtils;
import com.lantu.andorid.mvp_wml.utils.Logger;
import com.lantu.andorid.mvp_wml.utils.ToastUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by wml8743 on 2017/8/31.
 */

@DefaultLifeCycle(application = "com.lantu.android.mvp_wml.MvpApplication", flags = ShareConstants.TINKER_ENABLE_ALL, loadVerifyFlag = false)
public class AndroidApplication extends DefaultApplicationLike {

    private static Context sContext;
    private static ApplicationComponent sAppComponent;
    
    private int activityAount = 0; // 当前Acitity个数
    private boolean isForeground; // 是否在前台 true=处于前台，false=处于后台
    private static String IOCN_VERSION_CODE = ""; // 桌面图标版本


    public AndroidApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);

    }

    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _initInjector();
        _initConfig();
        _initX5Sdk();
        _registerActivityLifecycleCallbacks();
    }

    /**
     * 使用Tinker生成Application，这里改成静态调用
     *
     * @return
     */
    public static ApplicationComponent getAppComponent() {
        return sAppComponent;
    }

    public static Context getContext() {
        return sContext;
    }

    private void _initInjector() {
        // 这里不做注入操作，只提供一些全局单例数据
        sAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * 初始化配置
     */
    private void _initConfig() {

        RetrofitService.init();
        ToastUtils.init(getApplication());
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        sContext = getApplication();
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());
        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    /**
     * 初始化x5内核
     */
    private void _initX5Sdk() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Logger.d(" onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplication(), cb);
    }

    /**
     * activity 生命周期监听，用于监控APP前后台切换
     */
    private void _registerActivityLifecycleCallbacks() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    if (activityAount == 0) {
                        //app回到前台
                        isForeground = true;
                    }
                    activityAount++;
                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {
                    activityAount--;
                    if (activityAount == 0) {
                        isForeground = false;
                        IconUtils.switchIcon(getContext(), IOCN_VERSION_CODE);// 切换图标
                    }
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
        }
    }

    public static void setIcon(String code){
        IOCN_VERSION_CODE = code;
    }
}
