package com.international.wtw.lottery.activity.manager;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.SelectBankDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.ValidatePop;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.MyTextWatcher;
import com.international.wtw.lottery.widget.BankNumEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class BankcardActivity extends BaseActivity {

    public static final String TITLE = "title";
    public static final String IS_MODIFY = "is_modify";
    public static final String BANK_INFO = "bank_info";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_select_bankcard)
    TextView mTvSelectBankcard;
    @BindView(R.id.et_bankcard_number)
    BankNumEditText mEtBankNo;
    @BindView(R.id.et_bankcard_number_again)
    BankNumEditText mEtBankNoAgain;
    @BindView(R.id.et_open_address)
    EditText mEtOpenAddress;
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    @BindView(R.id.et_odd_bankcard_number)
    BankNumEditText mEtOddBankNo;
    @BindView(R.id.ll_odd_bank_info)
    LinearLayout mLlOddBankInfo;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    private BaseNiceDialog mBankDialog;
    private String mOddBankcardNo;
    private boolean isModify;
    private ValidatePop mOddBankNoPopup;
    private ValidatePop mOddBankNoPopup2;
    private ValidatePop mBankNoPopup;
    private ValidatePop mBankNoPopup2;
    private ValidatePop mBankNoAgainPopup;
    private ValidatePop mBankNoAgainPopup2;
    private ValidatePop mOpenAddressPopup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_bankcard;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTvTitle.setText(getIntent().getStringExtra(TITLE));
        isModify = getIntent().getBooleanExtra(IS_MODIFY, false);
        String[] bankNames = getResources().getStringArray(R.array.bank_name_items);
        mTvSelectBankcard.setText(bankNames[0]);
        MoneyInfo moneyInfo = (MoneyInfo) getIntent().getSerializableExtra(BANK_INFO);
        if (moneyInfo == null && !isModify) {
            requestUserMoneyInfo();
        } else {
            setBankcardData(moneyInfo);
        }
        initPopup();
        initEditTextListener();
    }

    private void initPopup() {
        mOddBankNoPopup = new ValidatePop(this, mEtOddBankNo).createPopup();
        mOddBankNoPopup2 = new ValidatePop(this, mEtOddBankNo).createPopup();
        mBankNoPopup = new ValidatePop(this, mEtBankNo).createPopup();
        mBankNoPopup2 = new ValidatePop(this, mEtBankNo).createPopup();
        mBankNoAgainPopup = new ValidatePop(this, mEtBankNoAgain).createPopup();
        mBankNoAgainPopup2 = new ValidatePop(this, mEtBankNoAgain).createPopup();
        mOpenAddressPopup = new ValidatePop(this, mEtOpenAddress).createPopup();
    }

    private void setBankcardData(MoneyInfo data) {
        String bankName = data.getBank_name();
        mOddBankcardNo = data.getBank_code();
        if (TextUtils.isEmpty(bankName) || TextUtils.isEmpty(mOddBankcardNo)) {
            isModify = false;
            mLlOddBankInfo.setVisibility(View.GONE);
            mTvTips.setText(getString(R.string.bind_card_tip2));
        } else {
            isModify = true;
            mLlOddBankInfo.setVisibility(View.VISIBLE);
            mTvBankName.setText(bankName);
            mTvTips.setText(getString(R.string.bind_card_tip3));
        }
    }

    @OnClick({R.id.img_back, R.id.ll_select_bank, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_select_bank:
                showSelectBankDialog();
                break;
            case R.id.btn_commit:
                commitAddOrModify();
                break;
        }
    }

    private void showSelectBankDialog() {
        if (mBankDialog == null) {
            mBankDialog = SelectBankDialog.newInstance()
                    .setListener(new SelectBankDialog.BankSelectListener() {
                        @Override
                        public void onBankSelect(String bank) {
                            mTvSelectBankcard.setText(bank);
                        }
                    })
                    .setAnimStyle(R.style.window_bottom_in_bottom_out)
                    .setShowBottom(true);
        }
        mBankDialog.showDialog(getSupportFragmentManager());
    }

    /**
     * 获取用户账户信息
     */
    private void requestUserMoneyInfo() {
        HttpRequest.getInstance().requestAmountInfo(this, new HttpCallback<MoneyInfo>() {
            @Override
            public void onSuccess(MoneyInfo data) {
                MoneyInfoManager.get().setMoneyInfo(data);
                setBankcardData(data);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
            }
        });
    }

    private void commitAddOrModify() {
        if (isModify) {
            if (checkOddBankNo() & checkBankNo() & checkBankAgain() & checkAddress()) {
                String bankName = mTvSelectBankcard.getText().toString().trim();
                String bankNo = mEtBankNo.getBankNum();
                String bankAddress = mEtOpenAddress.getText().toString().trim();
                requestAddOrModifyBankcard(bankName, bankNo, bankAddress);
            }
        } else {
            if (checkBankNo() & checkBankAgain() & checkAddress()) {
                String bankName = mTvSelectBankcard.getText().toString().trim();
                String bankNo = mEtBankNo.getBankNum();
                String bankAddress = mEtOpenAddress.getText().toString().trim();
                requestAddOrModifyBankcard(bankName, bankNo, bankAddress);
            }
        }

        /*if (TextUtils.isEmpty(bankName)) {
            //检查是否选择了银行
            showCheckNotify(getString(R.string.no_select_bank), mTvSelectBankcard);
        } else if (TextUtils.isEmpty(bankNo) || bankNo.length() < 16 || bankNo.length() > 19) {
            //检查银行卡号格式是否正确
            showCheckNotify(getString(R.string.correct_card_num), mEtBankNo);
        } else if (bankNo.equals(mOddBankcardNo)) {
            //检查两次输入的银行卡号是否相同
            showCheckNotify(getString(R.string.new_number_should_different), mEtBankNo);
        } else if (TextUtils.isEmpty(bankNoAgain)) {
            showCheckNotify(getString(R.string.input_card_num_again), mEtBankNoAgain);
        } else if (!bankNo.equals(bankNoAgain)) {
            //检查两次输入的银行卡号是否相同
            showCheckNotify(getString(R.string.bankcard_number_different), mEtBankNoAgain);
        } else if (TextUtils.isEmpty(bankAddress)) {
            showCheckNotify(getString(R.string.input_bank_address), mEtOpenAddress);
        } else {
            requestAddOrModifyBankcard(bankName, bankNo, bankAddress);
        }*/
    }

    /**
     * 请求修改或添加银行卡接口
     */
    private void requestAddOrModifyBankcard(String bankName, String bankNo, String bankAddress) {
        HttpRequest.getInstance().addOrModifyBankcard(this, bankName, bankNo, bankAddress, new HttpCallback<BaseModel>() {
            @Override
            public void onSuccess(BaseModel data) {
                ToastDialog.success(getString(R.string.operation_success))
                        .setDismissListener(new ToastDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(ToastDialog dialog) {
                                MoneyInfoManager.get().requestMoneyInfo();
                                finish();
                            }
                        }).show(getSupportFragmentManager());
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
            }
        });
    }

    private void initEditTextListener() {
        mEtOddBankNo.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String bankNum = mEtOddBankNo.getBankNum();
                if (TextUtils.isEmpty(bankNum)) {
                    if (mOddBankNoPopup2.getPopupWindow().isShowing())
                        mOddBankNoPopup2.dismiss();
                    if (!mOddBankNoPopup.getPopupWindow().isShowing())
                        mOddBankNoPopup.showMsg(getString(R.string.bind_card_tip1));
                } else {
                    if (mOddBankNoPopup.getPopupWindow().isShowing())
                        mOddBankNoPopup.dismiss();
                    if (!bankNum.equals(mOddBankcardNo)) {
                        if (!mOddBankNoPopup2.getPopupWindow().isShowing())
                            mOddBankNoPopup2.showMsg(getString(R.string.bind_card_tip4));
                    } else {
                        if (mOddBankNoPopup2.getPopupWindow().isShowing())
                            mOddBankNoPopup2.dismiss();
                    }
                }
            }
        });
        mEtOddBankNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkOddBankNo();
            }
        });

        mEtBankNo.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String bankNum = mEtBankNo.getBankNum();
                if (mBankNoPopup2.getPopupWindow().isShowing())
                    mBankNoPopup2.dismiss();
                if (TextUtils.isEmpty(bankNum)) {
                    if (!mBankNoPopup.getPopupWindow().isShowing())
                        mBankNoPopup.showMsg(getString(R.string.correct_card_num));
                } else {
                    if (mBankNoPopup.getPopupWindow().isShowing())
                        mBankNoPopup.dismiss();
                }
            }
        });
        mEtBankNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkBankNo();
            }
        });

        mEtBankNoAgain.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String bankNum2 = mEtBankNoAgain.getBankNum();
                if (mBankNoAgainPopup2.getPopupWindow().isShowing())
                    mBankNoAgainPopup2.dismiss();
                if (TextUtils.isEmpty(bankNum2)) {
                    if (!mBankNoAgainPopup.getPopupWindow().isShowing())
                        mBankNoAgainPopup.showMsg(getString(R.string.input_card_num_again));
                } else {
                    if (mBankNoAgainPopup.getPopupWindow().isShowing())
                        mBankNoAgainPopup.dismiss();
                }
            }
        });
        mEtBankNoAgain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkBankAgain();
            }
        });

        mEtOpenAddress.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String address = mEtOpenAddress.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    if (!mOpenAddressPopup.getPopupWindow().isShowing())
                        mOpenAddressPopup.showMsg(getString(R.string.input_card_num_again));
                } else {
                    if (mOpenAddressPopup.getPopupWindow().isShowing())
                        mOpenAddressPopup.dismiss();
                }
            }
        });
        mEtOpenAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkAddress();
            }
        });
    }

    private boolean checkOddBankNo() {
        String bankNum = mEtOddBankNo.getBankNum();
        if (TextUtils.isEmpty(bankNum)) {
            if (!mOddBankNoPopup.getPopupWindow().isShowing())
                mOddBankNoPopup.showMsg(getString(R.string.bind_card_tip1));
            return false;
        } else {
            if (!bankNum.equals(mOddBankcardNo)) {
                if (!mOddBankNoPopup2.getPopupWindow().isShowing())
                    mOddBankNoPopup2.showMsg(getString(R.string.bind_card_tip4));
                return false;
            } else {
                if (mOddBankNoPopup2.getPopupWindow().isShowing())
                    mOddBankNoPopup2.dismiss();
                return true;
            }
        }
    }

    private boolean checkBankNo() {
        String bankNum = mEtBankNo.getBankNum();
        if (TextUtils.isEmpty(bankNum) || bankNum.length() < 16 || bankNum.length() > 19) {
            if (!mBankNoPopup.getPopupWindow().isShowing())
                mBankNoPopup.showMsg(getString(R.string.correct_card_num));
            return false;
        } else {
            if (mBankNoPopup.getPopupWindow().isShowing())
                mBankNoPopup.dismiss();
            if (bankNum.equals(mOddBankcardNo)) {
                if (!mBankNoPopup2.getPopupWindow().isShowing())
                    mBankNoPopup2.showMsg(getString(R.string.new_number_should_different));
                return false;
            } else {
                if (mBankNoPopup2.getPopupWindow().isShowing())
                    mBankNoPopup2.dismiss();
                return true;
            }
        }
    }

    private boolean checkBankAgain() {
        String bankNum1 = mEtBankNo.getBankNum();
        String bankNum2 = mEtBankNoAgain.getBankNum();
        if (TextUtils.isEmpty(bankNum2)) {
            if (!mBankNoAgainPopup.getPopupWindow().isShowing())
                mBankNoAgainPopup.showMsg(getString(R.string.input_card_num_again));
            return false;
        } else {
            if (mBankNoAgainPopup.getPopupWindow().isShowing())
                mBankNoAgainPopup.dismiss();
            if (!bankNum1.equals(bankNum2)) {
                if (!mBankNoAgainPopup2.getPopupWindow().isShowing())
                    mBankNoAgainPopup2.showMsg(getString(R.string.bankcard_number_different));
                return false;
            } else {
                if (mBankNoAgainPopup2.getPopupWindow().isShowing())
                    mBankNoAgainPopup2.dismiss();
            }
            return true;
        }
    }

    private boolean checkAddress() {
        String address = mEtOpenAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            if (!mOpenAddressPopup.getPopupWindow().isShowing())
                mOpenAddressPopup.showMsg(getString(R.string.input_bank_address));
            return false;
        } else {
            if (mOpenAddressPopup.getPopupWindow().isShowing())
                mOpenAddressPopup.dismiss();
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        dismissAllPopup();
        super.onDestroy();
    }

    private void dismissAllPopup() {
        if (mOddBankNoPopup.getPopupWindow().isShowing())
            mOddBankNoPopup.dismiss();
        if (mOddBankNoPopup2.getPopupWindow().isShowing())
            mOddBankNoPopup2.dismiss();
        if (mBankNoPopup.getPopupWindow().isShowing())
            mBankNoPopup.dismiss();
        if (mBankNoPopup2.getPopupWindow().isShowing())
            mBankNoPopup2.dismiss();
        if (mBankNoAgainPopup.getPopupWindow().isShowing())
            mBankNoAgainPopup.dismiss();
        if (mBankNoAgainPopup2.getPopupWindow().isShowing())
            mBankNoAgainPopup2.dismiss();
        if (mOpenAddressPopup.getPopupWindow().isShowing())
            mOpenAddressPopup.dismiss();
    }
}
