package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.login.LoginActivity;

/**
 * 登录密码修改成功
 */
public class UpdateLoginPwdActivity extends AppCompatActivity {

    private int recLen = 3;
    private TextView tv_jump_time;

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {         // handle message
            switch (msg.what) {
                case 1:
                    recLen--;
                    tv_jump_time.setText(recLen + "秒后跳到登录面");

                    if (recLen > 0) {
                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);
                    } else {
                        startActivity(new Intent(UpdateLoginPwdActivity.this, LoginActivity.class));
                        finish();
                    }
            }
            super.handleMessage(msg);
        }
    };

    private ImageView imageView_toLeftArrow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateloginpwd);

        tv_jump_time = (TextView) findViewById(R.id.tv_jump_time);

        Message message = handler.obtainMessage(1);
        handler.sendMessageDelayed(message, 1000);

        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        imageView_toLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
