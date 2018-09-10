package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.PasswordInputView;

/**
 * 修改支付密码   新
 */
public class MyPasswordNewPlayActivity extends AppCompatActivity {

    private PasswordInputView edittext_withdrawl_newpwd;
    private ImageView imageView_toLeftArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypass_newplay);

        edittext_withdrawl_newpwd = (PasswordInputView) findViewById(R.id.edittext_withdrawl_newpwd);

        edittext_withdrawl_newpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //这里的count树枝上是和onTextChanged（）里的before一样的,after树枝上是和onTextChanged（）里的count一样的
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本每次改变就会跑这个方法
                if (s.length() == 4) {
                    SharePreferencesUtil.addString(MyPasswordNewPlayActivity.this, LotteryId.Pay_NewPwd, s.toString());
                    Intent intent = new Intent(MyPasswordNewPlayActivity.this, MyPasswordOnceNewPlayActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //这里显示出输入的字符串
            }
        });

        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        imageView_toLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
