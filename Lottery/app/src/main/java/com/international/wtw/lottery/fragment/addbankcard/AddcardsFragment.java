package com.international.wtw.lottery.fragment.addbankcard;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.SelectBankDialog;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.newJason.BankcardModel;
import com.international.wtw.lottery.newJason.LoginModel;
import com.international.wtw.lottery.newJason.PersonalModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.BankNumEditText;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加银行卡的面页
 */
public class AddcardsFragment extends NewBaseFragment {

    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.ll_real_name)
    LinearLayout llRealName;
    @BindView(R.id.tv_select_bankcard)
    TextView tvSelectBankcard;
    @BindView(R.id.ll_select_bank)
    LinearLayout llSelectBank;
    @BindView(R.id.et_bankcard_number)
    BankNumEditText etBankcardNumber;
    @BindView(R.id.et_bankcard_number_again)
    BankNumEditText etBankcardNumberAgain;
    @BindView(R.id.et_open_address)
    EditText etOpenAddress;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    String token;
    private BaseNiceDialog mBankDialog;
    private String userid,bankid,username;
    private BankcardModel mdata;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_addcards2;
    }

    @Override
    protected void initData() {
        token = SharePreferencesUtil.getString(AddcardsFragment.this.getActivity(), LotteryId.TOKEN, null);
        HttpRequest.getInstance().getdictionaryAll(AddcardsFragment.this, token, "11", new HttpCallback<BankcardModel>() {
            @Override
            public void onSuccess(BankcardModel data) {
                mdata=data;
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(getActivity(),"请求参数失败",Toast.LENGTH_SHORT).show();
            }
        });


        HttpRequest.getInstance().getDetail(this, token, new HttpCallback<PersonalModel>() {
            @Override
            public void onSuccess(PersonalModel data) throws Exception {
                userid=data.getData().getId();
                username=data.getData().getTrueName();
                tvRealName.setText(data.getData().getTrueName());
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_SHORT).show();
            }
        });


    }


    @OnClick({R.id.ll_select_bank, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select_bank:
                showSelectBankDialog();
                break;
            case R.id.btn_commit:
                setBankcard();
                break;
        }
    }



    private void showSelectBankDialog() {
        if (mBankDialog == null) {
            mBankDialog = SelectBankDialog.newInstance()
                    .setListener(new SelectBankDialog.BankSelectListener() {
                        @Override
                        public void onBankSelect(String bank) {

                            tvSelectBankcard.setText(bank);
                            if (null!=mdata.getData()){
                                for (int i=0;i<mdata.getData().size();i++){
                                    if (mdata.getData().get(i).getKeyName().equals(bank)){
                                        bankid=mdata.getData().get(i).getId();
                                    }
                                }
                            }
                        }
                    })
                    .setAnimStyle(R.style.window_bottom_in_bottom_out)
                    .setShowBottom(true);
        }
        mBankDialog.showDialog(AddcardsFragment.this.getFragmentManager());
    }


    private void setBankcard() {
        String bankcardNum=etBankcardNumber.getText().toString().replace(" ","");
        if (TextUtils.isEmpty(bankcardNum)){
            Toast.makeText(getActivity(),"请完整填写银行卡账号",Toast.LENGTH_SHORT).show();
            return;
        }

        HttpRequest.getInstance().setUserbank(AddcardsFragment.this, token, userid, bankid, bankcardNum, username, etOpenAddress.getText().toString(), "", new HttpCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) throws Exception {
                Toast.makeText(getActivity(),"操作成功。",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
