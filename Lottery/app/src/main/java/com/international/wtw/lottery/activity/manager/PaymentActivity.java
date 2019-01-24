package com.international.wtw.lottery.activity.manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.WebViewActivity2;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.newJason.LoginModel;
import com.international.wtw.lottery.newJason.PaymentMethodModel;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

import static com.international.wtw.lottery.activity.mine.WebViewActivity.EXTRA_WEB_URL;

public class PaymentActivity extends NewBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.textView_lotteryTypeName)
    TextView textViewLotteryTypeName;
    @BindView(R.id.downArrow)
    LinearLayout downArrow;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.et_recharge_amount)
    EditText etRechargeAmount;
    @BindView(R.id.tv_selectpaystyle)
    TextView tvSelectpaystyle;
    @BindView(R.id.tv_money_limit)
    TextView tvMoneyLimit;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private PaymentMethodModel.DataBean dataBean;
    private OptionsPickerView pvOptions;
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<String> arrayList;
    private int size2, options11, options12;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        dataBean = (PaymentMethodModel.DataBean) getIntent().getSerializableExtra("paydata");

        int size = dataBean.getListPaymentModePaymentMethod().size();
        for (int i = 0; i < size; i++) {
            options1Items.add(dataBean.getListPaymentModePaymentMethod().get(i).getPaymentMethod().getName());
            arrayList = new ArrayList<>();
            size2 = dataBean.getListPaymentModePaymentMethod().get(i).getPaymentMethod().getListPaymentMethodBank().size();
            for (int m = 0; m < size2; m++) {
                arrayList.add(dataBean.getListPaymentModePaymentMethod().get(i).getPaymentMethod().getListPaymentMethodBank().get(m).getPaymentBank().getName());
                options2Items.add(arrayList);
            }
        }
        LogUtil.e("==========="+new Gson().toJson(dataBean));
        initOptionPicker();
        textViewLotteryTypeName.setText(dataBean.getName());
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }


    @OnClick({R.id.iv_back, R.id.tv_selectpaystyle, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_selectpaystyle:
                pvOptions.show();
                break;
            case R.id.btn_confirm:
                recharge();
                break;

        }
    }

    private void recharge() {

        String PaymentModeCode =dataBean.getCode();
        String PaymentMethodCode= dataBean.getListPaymentModePaymentMethod().get(options11).getPaymentMethod().getCode();
        String token = SharePreferencesUtil.getString(getApplicationContext(), LotteryId.TOKEN, "");
        String BankCode= dataBean.getListPaymentModePaymentMethod().get(options11).getPaymentMethod().getId()+"";
        HttpRequest.getInstance().recharge(this, token, etRechargeAmount.getText().toString(), PaymentModeCode, PaymentMethodCode,BankCode, new HttpCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                 if (dataBean.getListPaymentModePaymentMethod().get(options11).getPaymentMethod().isIsJump()){
                     jumpUrl(data);
                 }else{
                     Ercode(data);
                 }

            }

            private void Ercode(LoginModel data) {
                HttpRequest.getInstance().PhoneQrcode(this, token, data.getData(), new HttpCallback<LoginModel>() {
                    @Override
                    public void onSuccess(LoginModel data) {
                        String html=data.getData().replace(" &lt;","<").replace("&gt;",">");
                        Intent intent=new Intent(PaymentActivity.this,WebViewActivity2.class);
                        intent.putExtra(EXTRA_WEB_URL,html);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        Toast.makeText(PaymentActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void jumpUrl(LoginModel data) {
                HttpRequest.getInstance().phoneDeposit(this, token, data.getData(), new HttpCallback<LoginModel>() {
                    @Override
                    public void onSuccess(LoginModel data) {
                        String html=data.getData().replace(" &lt;","<").replace("&gt;",">");
                        Intent intent=new Intent(PaymentActivity.this,WebViewActivity2.class);
                        intent.putExtra(EXTRA_WEB_URL,html);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        Toast.makeText(PaymentActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(PaymentActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initOptionPicker() {//条件选择器初始化
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                options11 = options1;
                options12 = options2;
                tvMoneyLimit.setVisibility(View.VISIBLE);
                tvMoneyLimit.setText("充值范围:"+dataBean.getListPaymentModePaymentMethod().get(options1).getPaymentMethod().getMinAmount()+"~"+dataBean.getListPaymentModePaymentMethod().get(options1).getPaymentMethod().getMaxAmount());
                tvSelectpaystyle.setText(dataBean.getListPaymentModePaymentMethod().get(options11).getPaymentMethod().getName() + "--" );
//                        dataBean.getListPaymentModePaymentMethod().get(options11).getPaymentMethod().getListPaymentMethodBank().get(options12).getPaymentBank().getName());
            }
        })
                .setTitleText(dataBean.getName())
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.BLACK)
                .setCancelColor(Color.BLUE)
                .setSubmitColor(Color.BLUE)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 ;
                        LogUtil.e("=======str======="+str);
                    }
                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        pvOptions.setPicker(options1Items);//一级选择器*/
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }


}
