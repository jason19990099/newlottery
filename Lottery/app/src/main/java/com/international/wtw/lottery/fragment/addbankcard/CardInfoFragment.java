package com.international.wtw.lottery.fragment.addbankcard;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.AddcardEvent;
import com.international.wtw.lottery.newJason.BankcardsModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class CardInfoFragment extends NewBaseFragment {
    @BindView(R.id.iv_banklogo)
    ImageView ivBanklogo;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_user)
    TextView tvBankUser;
    @BindView(R.id.tv_bank_address)
    TextView tvBankAddress;
    @BindView(R.id.tv_banklastNum)
    TextView tvBanklastNum;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cardlist;
    }

    @Override
    protected void initData() {
        String token = SharePreferencesUtil.getString(getActivity(), LotteryId.TOKEN, null);
        HttpRequest.getInstance().getUserBank(getActivity(), token, new HttpCallback<BankcardsModel>() {
            @Override
            public void onSuccess(BankcardsModel data) {
                tvBankUser.setText(data.getData().getBankAccountName());
                tvBankName.setText(data.getData().getBankName());
                tvBankAddress.setText(data.getData().getBankAddress());
                int length=data.getData().getBankAccount().length();
                if (length>3){
                    String str=data.getData().getBankAccount().substring(length-3,length);
                    tvBanklastNum.setText(str);
                }else{
                    tvBanklastNum.setText("✱✱✱");
                }

                ivBanklogo.setImageResource(getBankLogo(data.getData().getBankName()));

            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(getActivity(), "请求失败，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.iv_edit)
    public void onViewClicked() {
        AddcardEvent messageEvent = new AddcardEvent();
        EventBus.getDefault().post(messageEvent);
    }


    public int getBankLogo(String name) {
        switch (name) {
            case "中国银行":
                return R.mipmap.china;
            case "工商银行":
                return R.mipmap.gongshang;
            case "建设银行":
                return R.mipmap.construction;
            case "农业银行":
                return R.mipmap.agricultural;
            case "招商银行":
                return R.mipmap.merchants;
            case "交通银行":
                return R.mipmap.communications;
            case "民生银行":
                return R.mipmap.communications;
            case "平安银行":
                return R.mipmap.pingan;
            case "北京银行":
                return R.mipmap.beijing;
            case "兴业银行":
                return R.mipmap.industrial;
            case "中信银行":
                return R.mipmap.citic;
        }
        return R.mipmap.china;
    }


}
