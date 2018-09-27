package com.international.wtw.lottery.activity.login;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.newJason.Login;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        ImageView view = (ImageView) findViewById(R.id.img_start);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(1000);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                getToken();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (null==SharePreferencesUtil.getString(SplashActivity.this, LotteryId.TOKEN,null)){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void getToken() {
        HttpRequest.getInstance().getToken(SplashActivity.this, new HttpCallback<Login>() {
            @Override
            public void onSuccess(Login data) {
                SharePreferencesUtil.addString(SplashActivity.this,"token",data.getData());
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

            }
        });


    }
}