package com.international.wtw.lottery.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.widget.FloatView;

/**
 * Created by XIAOYAN on 2017/6/18.
 */

public class CustomerserviceImg {

    public WindowManager wm = null;
    public WindowManager.LayoutParams wmParams = null;
    public FloatView myFV = null;

    public void createView(Context context) {
        myFV = new FloatView(context.getApplicationContext());
        myFV.setImageResource(R.mipmap.icon_customer_service);  //这里简单的用自带的Icom来做演示
        //获取WindowManager
        wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
//        wmParams = ((BaseApplication) getApplication()).getWindowParams();
        BaseApplication baseApplication = new BaseApplication();
        wmParams = baseApplication.getWindowParams();

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

    public void removeView() {
        wm.removeView(myFV);
    }

}
