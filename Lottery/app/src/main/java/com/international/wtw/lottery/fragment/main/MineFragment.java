package com.international.wtw.lottery.fragment.main;

import android.app.AlertDialog;
import android.content.Context;
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
import com.international.wtw.lottery.activity.first.InfoCenterActivity;
import com.international.wtw.lottery.activity.login.LoginActivity;
import com.international.wtw.lottery.activity.manager.BankcardActivity;
import com.international.wtw.lottery.activity.mine.AboutUsActivity;
import com.international.wtw.lottery.activity.mine.BetOnRecordActivity;
import com.international.wtw.lottery.activity.mine.MyMessageActivity;
import com.international.wtw.lottery.activity.mine.MyPasswordActivity;
import com.international.wtw.lottery.activity.mine.PersonalCenterActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.adapter.MineAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.fragment.BaseFragment;
import com.international.wtw.lottery.json.LunbotuBean;
import com.international.wtw.lottery.json.MineBean;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private TextView mine_tv_name;
    private GridView mine_gv;
    private List<MineBean> list;
    private TextView mine_out_login;
    private LunbotuBean lunbotu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        EventBus.getDefault().register(this);
        InitView();

        getData();

        getBannerData(getActivity());

        getUserInfoMoney();

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
                        startActivity(new Intent(getActivity(), PersonalCenterActivity.class));
                    } else {
                        ToastDialog.error(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                    }
                }
                if (type_name.equals(getString(R.string.wd_dlmm))) {
                    if (!aBoolean) {
                        startActivity(new Intent(getActivity(), MyPasswordActivity.class));
                    } else {
                        ToastDialog.error(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                    }
                }
                if (type_name.equals(getString(R.string.wd_xxzx))) {
                    Intent intetn = new Intent(getActivity(), MyMessageActivity.class);
                    intetn.putExtra("title", "我的消息");
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
                        Intent intent = new Intent(getActivity(), BankcardActivity.class);
                        intent.putExtra(BankcardActivity.TITLE, getResources().getString(R.string.modify_bank_info));
                        startActivity(intent);
                    } else {
                        ToastDialog.error(getString(R.string.login_is_shiwan)).show(getFragmentManager());
                    }
                }
                if (type_name.equals(getString(R.string.wd_yhzh))) {
                    Intent intent = new Intent(getActivity(), BetOnRecordActivity.class);
                    intent.putExtra("is_shi_wan", aBoolean);
                    startActivity(intent);
                }
                if (type_name.equals(getString(R.string.wd_jryj))) {
                    if (null != lunbotu) {
                        Intent intetn = new Intent(new Intent(getActivity(), InfoCenterActivity.class));
                        intetn.putExtra(LotteryId.LUN_BO_TU, lunbotu);
                        intetn.putExtra("title", "新闻中心");
                        startActivity(intetn);
                    }
                }
                if (type_name.equals(getString(R.string.wd_xzjl))) {
                    startActivity(new Intent(getActivity(), AboutUsActivity.class));
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

        return view;
    }
    
    public void getBannerData(Context context) {
        String Login_oid = SharePreferencesUtil.getString(context, LotteryId.Login_oid, "");
        HttpRequest.getInstance().getNewsCenter(context, Login_oid, new HttpCallback<LunbotuBean>() {
            @Override
            public void onSuccess(LunbotuBean data) {
                lunbotu = data;
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getFragmentManager());
            }
        });
    }

    private void getUserInfoMoney() {
        MoneyInfo moneyInfo = MoneyInfoManager.get().getMoneyInfo();
        if (moneyInfo != null) {
            String hello = gettime();
            mine_tv_name.setText(String.format(hello + "%s", moneyInfo.getUsername()));
        }
    }

    @Subscribe
    public void onEvent(MoneyInfoRefreshEvent event) {
        if (event.moneyInfo != null) {
            String hello = gettime();
            mine_tv_name.setText(String.format(hello + "%s", event.moneyInfo.getUsername()));
        }
    }

    public String gettime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        int time = Integer.parseInt(str);
        LogUtil.e("time---" + time);

        String hello = null;
        if (time >= 0 && time <= 5) {
            hello = "晚上好！";
        }
        if (time > 5 && time <= 8) {
            hello = "早上好！";
        }
        if (time > 8 && time <= 10) {
            hello = "上午好!";
        }
        if (time > 10 && time <= 12) {
            hello = "中午好！";
        }
        if (time > 12 && time <= 16) {
            hello = "下午好！";
        }
        if (time > 16 && time <= 18) {
            hello = "傍晚好！";
        }
        if (time > 18) {
            hello = "晚上好！";
        }
        return hello;
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
            String Login_oid = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, null);
            if (Login_oid == null) {
                mine_out_login.setVisibility(View.GONE);
            } else {
                mine_out_login.setVisibility(View.VISIBLE);
            }
        }
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
                SharePreferencesUtil.addString(getActivity(), LotteryId.Login_oid, null);
                MoneyInfoManager.get().setMoneyInfo(null);
                SharePreferencesUtil.addString(getActivity(), LotteryId.Login_username, "");
                SharePreferencesUtil.addString(getActivity(), LotteryId.Login_phone, null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
