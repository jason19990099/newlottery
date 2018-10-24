package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.event.AddcardEvent;
import com.international.wtw.lottery.fragment.addbankcard.AddcardsFragment;
import com.international.wtw.lottery.fragment.addbankcard.CardListFragment;
import com.international.wtw.lottery.fragment.addbankcard.EmptyFragment;
import com.international.wtw.lottery.newJason.BankcardsModel;
import com.international.wtw.lottery.newJason.LoginModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 银行账号管理面页
 */
public class BankcardContralActivity extends NewBaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.topTitle)
    RelativeLayout topTitle;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bankcard;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getBankcard();
    }

    @Override
    protected boolean useEventBus() {
       return  true;
    }

    private void getBankcard() {
        String token = SharePreferencesUtil.getString(BankcardContralActivity.this, LotteryId.TOKEN, null);
        HttpRequest.getInstance().getUserBank(BankcardContralActivity.this, token, new HttpCallback<BankcardsModel>() {
            @Override
            public void onSuccess(BankcardsModel data) {
                FragmentManager fm = BankcardContralActivity.this.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (data.getData()==null){
                    ft.replace(R.id.fl_container, new EmptyFragment()).commit();
                }else{
                    ft.replace(R.id.fl_container, new CardListFragment()).commit();
                }
            }
            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(BankcardContralActivity.this,"请求失败，请重试",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(AddcardEvent messageEvent) {
        FragmentManager fm = BankcardContralActivity.this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_container, new AddcardsFragment()).commit();
    }



    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }


}
