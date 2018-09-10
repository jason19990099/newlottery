package com.international.wtw.lottery.activity.mine;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.AccountHistoryAdpater;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.json.BettingHistoryBean;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 账户历史
 */
public class AccountHistoryActivity extends BaseActivity {
    private ListView lv_account;
    private  List<BettingHistoryBean.TwoWeekHistoryBean> datas;
    private AccountHistoryAdpater adpater;
    private ImageView imageView2;
    private Gson gson=new Gson();
    private BettingHistoryBean bean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accounthistory;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        lv_account = (ListView) findViewById(R.id.lv_account);
        imageView2 = (ImageView) findViewById(R.id.iv_back);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initDatas() {
        String login_oid = SharePreferencesUtil.getString(AccountHistoryActivity.this, LotteryId.Login_oid, null);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put(LotteryId.OID, login_oid);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        new Thread() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(Constants.BASE_URL + Constants.GET_BETTING_HISTORY)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("==AccountHistoryActivity==result====" + result);
                    if (responseCode == 200) {
                        bean=gson.fromJson(result,BettingHistoryBean.class);
                        datas=bean.getTwo_week_history();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adpater = new AccountHistoryAdpater(AccountHistoryActivity.this, datas);
                                lv_account.setAdapter(adpater);
                                adpater.notifyDataSetChanged();
                            }
                        });


                    } else {
                        LogUtil.e("=========请求接口失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


}
