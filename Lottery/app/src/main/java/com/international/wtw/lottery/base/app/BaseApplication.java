package com.international.wtw.lottery.base.app;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.listener.RequestClient;
import com.international.wtw.lottery.utils.ActivityManager;
import com.international.wtw.lottery.utils.Apputil;
import com.international.wtw.lottery.utils.Config;
import com.international.wtw.lottery.utils.FunctionUtility;
import com.international.wtw.lottery.widget.FloatView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {

    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }

    private WindowManager wm = null;
    private WindowManager.LayoutParams wmParams = null;
    private FloatView myFV = null;
    private static BaseApplication sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        //去掉证书验证的问题/
        Apputil.giveipTrust();

        sApp = this;
        RequestClient.init(this);
        FunctionUtility.setContext(this);
        Config.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());//日志打印
        registerActivityLifecycleCallbacks();
        //设置友盟统计场景
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        //友盟日志加密6.0.0版本及以后
        MobclickAgent.enableEncrypt(true);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static BaseApplication getAppContext() {
        return sApp;
    }

    public void createView() {

        if (null == myFV) {
            myFV = new FloatView(getApplicationContext());
            myFV.setImageResource(R.mipmap.icon_customer_service);  //这里简单的用自带的Icom来做演示
            //获取WindowManager
            wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            //设置LayoutParams(全局变量）相关参数
            wmParams = (this).getWindowParams();

            /**
             *以下都是WindowManager.LayoutParams的相关属性
             * 具体用途可参考SDK文档
             */
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;   //设置window type
            wmParams.format = PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明
            //设置Window flag
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 下面的flags属性的效果形同“锁定”。
         * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
         wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                               | LayoutParams.FLAG_NOT_FOCUSABLE
                               | LayoutParams.FLAG_NOT_TOUCHABLE;
        */
            wmParams.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角，便于调整坐标
            //以屏幕左上角为原点，设置x、y初始值
            wmParams.x = wm.getDefaultDisplay().getWidth();
            wmParams.y = wm.getDefaultDisplay().getHeight() / 2;
            //设置悬浮窗口长宽数据
            wmParams.width = 100;
            wmParams.height = 100;

            //显示myFloatView图像
            wm.addView(myFV, wmParams);
        }


    }

    public void removeFM() {

        if (null != wm && null != myFV) {
            wm.removeView(myFV);
            myFV = null;
            wm = null;
        }
    }


    /**
     * 获取当前activity的接口
     */
    private void registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

}

