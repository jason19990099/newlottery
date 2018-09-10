package com.international.wtw.lottery.activity.mine;

import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.MsgDetailBean;
import com.international.wtw.lottery.json.MsgDetailContentBean;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by 18Steven on 2017/9/23.
 */

public class MsgDetailActivity extends BaseActivity {
    private TextView tv_time, tv_title;
    private com.international.wtw.lottery.utils.MyTextView tv_context;
    private String id, time, text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msgdetail;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        id = getIntent().getStringExtra("id");
        time = getIntent().getStringExtra("time");
        text = getIntent().getStringExtra("text");
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_context = (com.international.wtw.lottery.utils.MyTextView) findViewById(R.id.tv_context);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.wlecome));

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SetData();
    }

    private void SetData() {
        String login_oid = SharePreferencesUtil.getString(getApplicationContext(), LotteryId.Login_oid, "");
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("oid", login_oid);
        jsonParams.put("msgId", id);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        MyMessageRequest(body);
    }

    private void MyMessageRequest(RequestBody body) {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + Constants.USER_MSGBYID)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("消息详情---" + responseCode + "-" + result);
                    JSONObject object = new JSONObject(result);
                    boolean oldboo = object.has("msg");
                    Gson gson = new Gson();
                    MsgDetailBean msgDetailBean = gson.fromJson(result, MsgDetailBean.class);
                    int msg1 = msgDetailBean.getMsg();
                    if (responseCode == 200) {
                        if (msg1 == 2006) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MsgDetailContentBean content = msgDetailBean.getContent();
                                    String addtime = content.getAddtime();
                                    String comment = content.getComment();
                                    tv_time.setText(addtime);
                                    tv_context.setText(comment);
                                }
                            });
                        } else {
                            ToastDialog.error(Constants.getErrorCodeInfo(msg1 + "")).show(getSupportFragmentManager());
                        }
                    } else {
                        LogUtil.e("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
