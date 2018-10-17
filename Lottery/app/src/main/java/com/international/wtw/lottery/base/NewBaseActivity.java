package com.international.wtw.lottery.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.dialog.SimpleProgressDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * 描述：Activity基类
 */

public abstract class NewBaseActivity extends AppCompatActivity {
    private SimpleProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        initStatusBar();
        ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    protected void initStatusBar() {
        //子类按需实现 如需要全屏显示的页面
    }

    @Override
    protected void onStart() {
        super.onStart();
        //按需注册EventBus
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        //按需注销EventBus
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    /**
     * @return 是否使用EventBus接收消息, 默认false, 如需使用重新该方法返回true
     */


    public void showProgressDialog(@NonNull String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null) {
                    mProgressDialog = new SimpleProgressDialog(NewBaseActivity.this, message);
                }
                mProgressDialog.setText(message);
                if (!mProgressDialog.isShowing() && !NewBaseActivity.this.isFinishing()) {
                    mProgressDialog.show();
                }
            }
        });
    }

    public void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    public void openActivity(@NonNull Class<? extends Activity> clazz) {
        openActivity(clazz, null, false);
    }

    public void openActivity(@NonNull Class<? extends Activity> clazz, boolean finish) {
        openActivity(clazz, null, finish);
    }

    public void openActivity(@NonNull Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        HttpRequest.getInstance().cancel(this);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected abstract boolean useEventBus() ;
}
