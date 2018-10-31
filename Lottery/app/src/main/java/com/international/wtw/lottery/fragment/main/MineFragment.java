package com.international.wtw.lottery.fragment.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.login.LoginActivity;
import com.international.wtw.lottery.activity.manager.BankcardActivity;
import com.international.wtw.lottery.activity.mine.BankcardContralActivity;
import com.international.wtw.lottery.activity.mine.BetrecordActivity;
import com.international.wtw.lottery.activity.mine.MyMessageActivity;
import com.international.wtw.lottery.activity.mine.MyPasswordLoginActivity;
import com.international.wtw.lottery.activity.mine.PersonalActiovity;
import com.international.wtw.lottery.activity.mine.SettledOrdersActivity;
import com.international.wtw.lottery.activity.mine.TakeoutMoneyPasswordActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.adapter.MineAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.fragment.BaseFragment;
import com.international.wtw.lottery.json.MineBean;
import com.international.wtw.lottery.newJason.LoginModel;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    Unbinder unbinder;
    private View view;
    private TextView mine_tv_name;
    private GridView mine_gv;
    private List<MineBean> list;
    private TextView mine_out_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        EventBus.getDefault().register(this);
        InitView();
        getData();
        MineAdapter adapter = new MineAdapter(getActivity(), list);
        mine_gv.setAdapter(adapter);

        mine_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null == getActivity())
                    return;
                boolean aBoolean = SharePreferencesUtil.getBoolean(getActivity(), LotteryId.IS_SHI_WAN, false);
                String type_name = list.get(position).getType_name();
                if (type_name.equals(getString(R.string.wd_grzx))) {
                    if (!aBoolean) {
                        startActivity(new Intent(getActivity(), PersonalActiovity.class));
                    } else {
                        ToastDialog.error(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                    }
                }
                if (type_name.equals(getString(R.string.wd_dlmm))) {
                    if (!aBoolean) {
                        startActivity(new Intent(getActivity(), MyPasswordLoginActivity.class));
                    } else {
                        ToastDialog.error(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                    }
                }
                if (type_name.equals(getString(R.string.wd_xxzx))) {
                    Intent intetn = new Intent(getActivity(), MyMessageActivity.class);
                    intetn.putExtra("title", "信息中心");
                    startActivity(intetn);
                }
                if (type_name.equals(getString(R.string.wd_lsjl))) {
                    if (!aBoolean) {
                        ((MainActivity) getActivity()).changeShowFragment(2);
                    } else {
                        ToastDialog.error(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                    }
                }
                if (type_name.equals(getString(R.string.wd_qkmm))) {
                    if (!aBoolean) {
                        Intent intent = new Intent(getActivity(), TakeoutMoneyPasswordActivity.class);
                        intent.putExtra(BankcardActivity.TITLE, getResources().getString(R.string.modify_bank_info));
                        startActivity(intent);
                    } else {
                        ToastDialog.error(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                    }
                }
                if (type_name.equals(getString(R.string.wd_yhzh))) {
                    Intent intent = new Intent(getActivity(), BankcardContralActivity.class);
                    intent.putExtra("is_shi_wan", aBoolean);
                    startActivity(intent);
                }
                if (type_name.equals(getString(R.string.wd_jryj))) {
                    startActivity(new Intent(getActivity(),SettledOrdersActivity.class));
                }
                if (type_name.equals(getString(R.string.wd_xzjl))) {
                    startActivity(new Intent(getActivity(),BetrecordActivity.class));
                }
                if (type_name.equals(getString(R.string.wd_kfjl))) {
                    String serviceUrl = SharePreferencesUtil.getString(getActivity(), LotteryId.SERVICE_URL, "");
                    if (!TextUtils.isEmpty(serviceUrl)) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, getString(R.string.service_online));
                        intent.putExtra(WebViewActivity.EXTRA_WEB_URL, serviceUrl);
                        intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                        getActivity().startActivity(intent);
                    }
                }
            }
        });

        unbinder = ButterKnife.bind(this, view);
        return view;
    }



    private void getUserInfoMoney() {
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().getBalance(getActivity(), token, new HttpCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                String money = data.getData();
                tvBalance.setText("余额:"+money+"元");
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getFragmentManager());
            }
        });

    }

    @Subscribe
    public void onEvent(MoneyInfoRefreshEvent event) {
        if (event.moneyInfo != null) {
            mine_tv_name.setText(String.format("hello" + "%s", event.moneyInfo.getUsername()));
        }
    }


    private void InitView() {
        mine_tv_name = (TextView) view.findViewById(R.id.mine_tv_name);
        mine_gv = (GridView) view.findViewById(R.id.mine_gv);
        mine_out_login = (TextView) view.findViewById(R.id.mine_out_login);
        mine_out_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_out_login:
                CreateDialog();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (null != getActivity()) {
            String token = SharePreferencesUtil.getString(getActivity(), LotteryId.TOKEN, "");
            if (token.equals("")) {
                mine_out_login.setVisibility(View.GONE);
            } else {
                mine_out_login.setVisibility(View.VISIBLE);
            }
        }

        getUserInfoMoney();
    }

    public void getData() {
        list = new ArrayList<>();

        MineBean bean1 = new MineBean();
        bean1.setType_name(getString(R.string.wd_grzx));

        MineBean bean2 = new MineBean();
        bean2.setType_name(getString(R.string.wd_dlmm));

        MineBean bean3 = new MineBean();
        bean3.setType_name(getString(R.string.wd_xxzx));

        MineBean bean4 = new MineBean();
        bean4.setType_name(getString(R.string.wd_lsjl));

        MineBean bean5 = new MineBean();
        bean5.setType_name(getString(R.string.wd_qkmm));

        MineBean bean6 = new MineBean();
        bean6.setType_name(getString(R.string.wd_yhzh));

        MineBean bean7 = new MineBean();
        bean7.setType_name(getString(R.string.wd_jryj));


        MineBean bean8 = new MineBean();
        bean8.setType_name(getString(R.string.wd_xzjl));

        MineBean bean9 = new MineBean();
        bean9.setType_name(getString(R.string.wd_kfjl));

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);
        list.add(bean9);
    }

    public void CreateDialog() {
        if (null == getActivity())
            return;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.out_login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);
        builder.create();
        final AlertDialog dialog = builder.show();

        Button btn_out_login_cancel = (Button) view.findViewById(R.id.btn_out_login_cancel);
        Button btn_out_login_ok = (Button) view.findViewById(R.id.btn_out_login_ok);

        btn_out_login_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_out_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginout();
            }
        });
    }

    private void loginout() {
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().Loginout(MineFragment.this, token, new HttpCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                 //重新获取token
                getToken();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
            }
        });

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 重新获取token 不然没有办法登陆
     */
    private void getToken() {
        HttpRequest.getInstance().getToken(MineFragment.this, new HttpCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                SharePreferencesUtil.addString(MineFragment.this.getActivity(),LotteryId.TOKEN,data.getData());
                MoneyInfoManager.get().setMoneyInfo(null);
                SharePreferencesUtil.addString(getActivity(), LotteryId.Login_username, "");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                dialog.dismiss();
            }
            @Override
            public void onFailure(String msgCode, String errorMsg) {
            }
        });
    }
}
