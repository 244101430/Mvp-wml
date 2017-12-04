package com.lantu.andorid.mvp_wml.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import com.lantu.andorid.mvp_wml.ui.home.MainActivity;

/**
 * 切换桌面图标
 * Created by wml8743 on 2017/11/16.
 */

public class IconUtils {

    /**
     * 默认图标
     */
    public static final String ICON_DEFAULT = "iocn_defualt";
    //要跟manifest的activity-alias 的name保持一致
    /**
     * 图标一
     */
    public static final String ICON_TAG = "com.lantu.andorid.mvp_wml.icon_tag";
    /**
     * 图标二
     */
    public static final String ICON_TAG_1212 = "com.lantu.andorid.mvp_wml.icon_tag_1212";

    /**
     * @param useCode
     */
    public static void switchIcon(Context context, String useCode) {
        try {

            if (StringUtils.isEmpty(useCode)) {
                return;
            }
            PackageManager pm = context.getPackageManager();
            ComponentName componentName = new ComponentName(context, MainActivity.class);
            int res = pm.getComponentEnabledSetting(componentName);
            if (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                if (!ICON_DEFAULT.equals(useCode)) {
                    // 隐藏应用图标
                    pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                }

            } else {
                // 显示应用图标
                if (ICON_DEFAULT.equals(useCode)) {
                    pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
                }
            }


            /**
             * 图标一
             */
            ComponentName normalComponentName = new ComponentName(context, ICON_TAG);
            //正常图标新状态
            int normalNewState = ICON_TAG.equals(useCode) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
            if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {//新状态跟当前状态不一样才执行
                pm.setComponentEnabledSetting(normalComponentName, normalNewState, PackageManager.DONT_KILL_APP);
            }


            /**
             * 图标二
             */
            ComponentName actComponentName = new ComponentName(context, ICON_TAG_1212);
            //正常图标新状态
            int actNewState = ICON_TAG_1212.equals(useCode) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
            if (pm.getComponentEnabledSetting(actComponentName) != actNewState) {//新状态跟当前状态不一样才执行
                pm.setComponentEnabledSetting(actComponentName, actNewState, PackageManager.DONT_KILL_APP);
            }
        } catch (Exception e) {
        }
    }

}
