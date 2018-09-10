package com.international.wtw.lottery.activity.mine;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;

/**
 * Created by XIAOYAN on 2017/9/9.
 */

public class AboutUsActivity extends AppCompatActivity {

    private ImageView imageView_toLeftArrow;
    private TextView tv_current_version, tv_latest_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        imageView_toLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_current_version = (TextView) findViewById(R.id.tv_current_version);
        tv_latest_version = (TextView) findViewById(R.id.tv_latest_version);

        tv_current_version.setText("当前版本 " + getVersion());
        tv_latest_version.setText("当前版本 " + getVersion());

    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
