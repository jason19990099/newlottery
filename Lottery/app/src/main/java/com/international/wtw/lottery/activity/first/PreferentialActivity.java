package com.international.wtw.lottery.activity.first;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.adapter.PreferentialAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.PreferentialBean;
import com.international.wtw.lottery.json.PreferentialProBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/10/4. 活动中心
 */

public class PreferentialActivity extends BaseActivity {

    private ImageView img_back;
    private GridView gv_preferential;
    private PreferentialAdapter adapter;
    private List<PreferentialProBean> promotions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preferential;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gv_preferential = (GridView) findViewById(R.id.gv_preferential);

        SetData(0);
    }

    private void SetData(int num) {
        HttpRequest.getInstance().getPromotions(PreferentialActivity.this, num, new HttpCallback<PreferentialBean>() {
            @Override
            public void onSuccess(PreferentialBean data) {
                promotions = data.getPromotions();
                adapter = new PreferentialAdapter(PreferentialActivity.this, promotions);
                gv_preferential.setAdapter(adapter);

                gv_preferential.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 = new Intent(PreferentialActivity.this, WebViewActivity.class);
                        intent1.putExtra(WebViewActivity.EXTRA_WEB_TITLE, promotions.get(position).getText());
                        intent1.putExtra(WebViewActivity.EXTRA_WEB_URL, promotions.get(position).getLink());
                        startActivity(intent1);
                    }
                });
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
            }
        });
    }

}
