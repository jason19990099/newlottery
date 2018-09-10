package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.international.wtw.lottery.R;

/**
 * 我的密码
 */
public class MyPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_paly_password, rl_login_pass;
    private ImageView imageView_toLeftArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypassword);

        rl_paly_password = (RelativeLayout) findViewById(R.id.rl_paly_password);
        rl_login_pass = (RelativeLayout) findViewById(R.id.rl_login_pass);
        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);

        rl_paly_password.setOnClickListener(this);
        rl_login_pass.setOnClickListener(this);
        imageView_toLeftArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        String Login_phone = SharePreferencesUtil.getString(getApplicationContext(), LotteryId.Login_phone, null);
        switch (v.getId()) {
            case R.id.rl_paly_password:
//                if (TextUtils.isEmpty(Login_phone)) {
                    startActivity(new Intent(MyPasswordActivity.this, MyPasswordOldPlayActivity.class));
//                } else {
//                    Intent intent1 = new Intent(MyPasswordActivity.this, MyPwdPhoneActivity.class);
//                    intent1.putExtra("IsLoginPaly", 1);
//                    startActivity(intent1);
//                }
                break;
            case R.id.rl_login_pass:
//                if (TextUtils.isEmpty(Login_phone)) {
                    startActivity(new Intent(MyPasswordActivity.this, MyPasswordLoginActivity.class));
//                } else {
//                    Intent intent2 = new Intent(MyPasswordActivity.this, MyPwdPhoneActivity.class);
//                    intent2.putExtra("IsLoginPaly", 0);
//                    startActivity(intent2);
//                }
                break;
            case R.id.imageView_toLeftArrow:
                finish();
                break;
        }
    }

}
