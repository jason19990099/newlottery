package com.international.wtw.lottery.activity.manager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.OfflinePayAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.dialog.DatePickDialog;
import com.international.wtw.lottery.dialog.SelectBankDialog;
import com.international.wtw.lottery.dialog.TimePickerDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.event.PaymentCompletedEvent;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.OfflinePayModel;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.RegexUtil;
import com.international.wtw.lottery.utils.SizeUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 线下支付 替换原ManualInputActivity
 */
public class OfflinePaymentActivity extends NewBaseActivity {

    public static String SELECTED_ITEM = "selected_type";
    public static String OFFLINE_PAYEE_INFO = "offline_payee_info";

    @BindView(R.id.textView_titleName)
    TextView mTvTitle;
    @BindView(R.id.tv_select_bankcard)
    TextView mTvSelectBankcard;
    /*@BindView(R.id.ll_select_bank)
    LinearLayout mLlSelectBank;*/
    @BindView(R.id.tv_open_bank)
    TextView mTvOpenBank;
    @BindView(R.id.tv_bank_address)
    TextView mTvBankAddress;
    @BindView(R.id.tv_bankcard_id)
    TextView mTvBankcardId;
    /*@BindView(R.id.tv_copy_bankcard)
    TextView mTvCopyBankcard;*/
    @BindView(R.id.tv_pay_method)
    TextView mTvPayMethod;
    @BindView(R.id.tv_payee_name)
    TextView mTvPayeeName;
    @BindView(R.id.tv_copy_payee_name)
    TextView mTvCopyPayeeName;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.ll_payee_info)
    LinearLayout mLlPayeeInfo;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_deposit_amount)
    EditText mEtDepositAmount;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.et_order_number)
    EditText mEtOrderNumber;
    @BindView(R.id.et_remarks)
    EditText mEtRemarks;
    @BindView(R.id.ll_bank_name)
    LinearLayout mLlBankName;
    @BindView(R.id.ll_bank_address)
    LinearLayout mLlBankAddress;
    @BindView(R.id.ll_bankcard_id)
    LinearLayout mLlBankcardId;
    @BindView(R.id.ll_order_num)
    LinearLayout llOrderNum;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private boolean isShowBank;//是否显示选择银行item

    private String mBankName;
    private String mDateTime;
    private String mDateStr;
    private String mTimeStr;
    private String imgUrl;
    private String typeName;//支付类型(银行卡支付取银行名称, 其他支付取支付方式)
    private BaseNiceDialog mBankDialog;
    private List<OfflinePayModel> mPayeeInfo;
    private OfflinePayModel selectedItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_offline_payment;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        //获取上一个页面传过来的数据
        //noinspection unchecked
        mPayeeInfo = (List<OfflinePayModel>) getIntent().getSerializableExtra(OFFLINE_PAYEE_INFO);
        selectedItem = (OfflinePayModel) getIntent().getSerializableExtra(SELECTED_ITEM);
        initRecycler();
        //根据传过来的数据初始化试图
        initCurrentDateTime();
        setInputListener();
        setPayeeInfo(selectedItem);
    }

    private void initRecycler() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        OfflinePayAdapter payAdapter = new OfflinePayAdapter(selectedItem.type, mPayeeInfo);
        mRecyclerView.setAdapter(payAdapter);
        payAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectedItem = payAdapter.getData().get(position);
                payAdapter.setSelectedType(selectedItem.type);
                setPayeeInfo(selectedItem);
                KeyBoardUtils.hideInputForce(OfflinePaymentActivity.this);
            }
        });
    }

    private void initCurrentDateTime() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        mDateTime = format.format(new Date(System.currentTimeMillis()));
        mDateStr = mDateTime.split(" ")[0];
        mTvDate.setText(mDateStr);
        mTimeStr = mDateTime.split(" ")[1];
        mTvTime.setText(mTimeStr);
    }

    private void setPayeeInfo(OfflinePayModel payeeInfo) {
        typeName = selectedItem.getPayName();
        mTvTitle.setText(typeName);
        mTvPayMethod.setText(payeeInfo.getPayName().replace("转账", "收款信息"));
        setIsShowBank(payeeInfo.type == 0, payeeInfo);
    }

    private void setIsShowBank(boolean isShow, OfflinePayModel payeeInfo) {
        isShowBank = isShow;
        //mLlSelectBank.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTvPayeeName.setText(payeeInfo.bank_user);
        mTvOpenBank.setText(payeeInfo.bank_name);
        mTvBankAddress.setText(payeeInfo.bank_addres);
        mTvBankcardId.setText(payeeInfo.bank_account);


        if (selectedItem.type == 1) {
            mTvCopyPayeeName.setVisibility(View.VISIBLE);
        } else {
            mTvCopyPayeeName.setVisibility(View.GONE);
        }
        if (selectedItem.type == 2) {
            mTvUserName.setText("微信昵称：");
            mEtRealName.setHint(getString(R.string.input_wechat_name));
        } else {
            mTvUserName.setText(getString(R.string.user_account_name));
            mEtRealName.setHint(getString(R.string.input_real_name));
        }
        if (selectedItem.type == 1 || selectedItem.type == 2) {
            mTvOrderNumber.setText("商户单号：");
            mEtOrderNumber.setHint("商户单号后四位");
        } else {
            mTvOrderNumber.setText("交易单号：");
            mEtOrderNumber.setHint("交易单号后四位");
        }

        String imageUrl1 = payeeInfo.bank_image_url;
        String imageUrl2 = payeeInfo.bank_image_url2;
        imgUrl = !TextUtils.isEmpty(imageUrl1) ? imageUrl1 : imageUrl2;
        if (!TextUtils.isEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http")) {
                imgUrl = Constants.BASE_URL + imgUrl;
            }
            mIvQrCode.setVisibility(View.VISIBLE);
            /*if (selectedItem.type != 1) {
                mLlBankName.setVisibility(View.GONE);
            } else {
                mLlBankName.setVisibility(View.VISIBLE);
            }*/
            mLlBankAddress.setVisibility(View.GONE);
            llOrderNum.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(imgUrl)
                    .config(Bitmap.Config.RGB_565)
                    .resize(SizeUtils.dp2px(160), SizeUtils.dp2px(160))
                    .into(mIvQrCode);
        } else {
            mIvQrCode.setVisibility(View.GONE);
            //mLlBankName.setVisibility(View.VISIBLE);
            mLlBankAddress.setVisibility(View.VISIBLE);
            llOrderNum.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_back, R.id.ll_select_bank, R.id.tv_date, R.id.tv_time,
            R.id.btn_confirm, R.id.tv_copy_bankcard, R.id.tv_copy_payee_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.ll_select_bank:
                showSelectBankDialog();
                break;
            case R.id.tv_date:
                pickDate();
                break;
            case R.id.tv_time:
                pickTime();
                break;
            case R.id.btn_confirm:
                commitOfflinePay();
                break;
            case R.id.tv_copy_bankcard:
                String content = mTvBankcardId.getText().toString();
                copyText(content);
                break;
            case R.id.tv_copy_payee_name:
                copyText(mTvPayeeName.getText().toString());
                break;
        }
    }

    private void copyText(String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb != null) {
            ClipData clipData = ClipData.newPlainText("Label", content);
            cmb.setPrimaryClip(clipData);
            ToastDialog.success("复制成功!").show(getSupportFragmentManager());
        } else {
            ToastDialog.error("复制失败!").show(getSupportFragmentManager());
        }
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new PaymentCompletedEvent());
        super.onBackPressed();
    }

    private void showSelectBankDialog() {
        if (mBankDialog == null) {
            mBankDialog = SelectBankDialog.newInstance()
                    .setListener(new SelectBankDialog.BankSelectListener() {
                        @Override
                        public void onBankSelect(String bank) {
                            typeName = mBankName = bank;
                            mTvSelectBankcard.setText(bank);
                        }
                    })
                    .setAnimStyle(R.style.window_bottom_in_bottom_out)
                    .setShowBottom(true);
        }
        mBankDialog.showDialog(getSupportFragmentManager());
    }

    private void pickDate() {
        new DatePickDialog(this, mDateStr, new DatePickDialog.DateChangeListener() {
            @Override
            public void refreshDate(String dateString) {
                mDateStr = dateString;
                mTvDate.setText(dateString);
            }
        }).show();
    }

    private void pickTime() {
        TimePickerDialog.newInstance()
                .setTimeChangeListener(new TimePickerDialog.TimeChangeListener() {
                    @Override
                    public void onTimeChanged(String timeString) {
                        mTimeStr = timeString;
                        mTvTime.setText(timeString);
                    }
                })
                .setMargin(70)
                .showDialog(getSupportFragmentManager());
    }

    private void commitOfflinePay() {
        String realName = mEtRealName.getText().toString().trim();
        String amount = mEtDepositAmount.getText().toString().trim();
        String remarks = mEtRemarks.getText().toString().trim();
        String orderNo = mEtOrderNumber.getText().toString().trim();
        /*if (isShowBank && TextUtils.isEmpty(mBankName)) {
            ToastDialog.error(getString(R.string.no_select_bank)).show(getSupportFragmentManager());
        } else */
        if (selectedItem.type != 2 && !RegexUtil.isChinese(realName)) {
            ToastDialog.error(getString(R.string.input_real_name)).show(getSupportFragmentManager());
        } else if (TextUtils.isEmpty(amount)) {
            ToastDialog.error(getString(R.string.input_amount)).show(getSupportFragmentManager());
        } else if (Float.valueOf(amount) < selectedItem.minMoney) {
            ToastDialog.error(String.format(getString(R.string.less_manual_amount2), selectedItem.minMoney)).show(getSupportFragmentManager());
        } else {
            if (TextUtils.isEmpty(orderNo) || orderNo.length() < 4) {
                if (selectedItem.type != 0 && !TextUtils.isEmpty(imgUrl)) {
                    //支付方式非银行卡支付, 并且二维码非空
                    ToastDialog.error(getString(R.string.input_order_num)).show(getSupportFragmentManager());
                    return;
                } else {
                    orderNo = "0000";
                }
            }
            mDateTime = mDateStr + " " + mTimeStr;
            requestOfflinePay(amount, orderNo, realName, remarks);
        }
    }

    private void requestOfflinePay(String amount, String orderNo, String realName, String remarks) {
        showProgressDialog(getString(R.string.loading));
        HttpRequest.getInstance().requestOfflinePay(this, amount, orderNo, realName, typeName,
                mDateTime, selectedItem.getPayName(), selectedItem.id, remarks, selectedItem.gameName,
                new HttpCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        dismissProgressDialog();
                        ToastDialog.success(getString(R.string.operation_success))
                                .setDismissListener(new ToastDialog.OnDismissListener() {
                                    @Override
                                    public void onDismiss(ToastDialog dialog) {
                                        EventBus.getDefault().post(new PaymentCompletedEvent());
                                        finish();
                                    }
                                }).show(getSupportFragmentManager());
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        dismissProgressDialog();
                        ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                    }
                });
    }

    private void setInputListener() {
        mEtDepositAmount.setHint(String.format(getString(R.string.amount_line_down_min2), selectedItem.minMoney));
        mEtDepositAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!text.equals("")) {
                    if (text.length() == 1 && (text.equals("0") || text.equals("."))) {
                        s.clear();
                        return;
                    }
                    float aFloat = Float.parseFloat(text);
                    if (aFloat > 100000.00) {
                        mEtDepositAmount.setText("100000.00");
                        mEtDepositAmount.setSelection(mEtDepositAmount.getText().toString().length());
                    }
                    //限制小数点后只能两位
                    int posDot = text.indexOf(".");
                    if (posDot <= 0)
                        return;
                    if (text.length() - posDot - 1 > 2) {
                        s.delete(posDot + 3, posDot + 4);
                    }
                }
            }
        });
    }
}
