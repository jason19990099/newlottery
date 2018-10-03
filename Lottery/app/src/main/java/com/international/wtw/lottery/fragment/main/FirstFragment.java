package com.international.wtw.lottery.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.first.InfoCenterActivity;
import com.international.wtw.lottery.activity.first.InfoDetailActivity;
import com.international.wtw.lottery.activity.first.PreferentialActivity;
import com.international.wtw.lottery.activity.login.RegisterActivity;
import com.international.wtw.lottery.activity.lottery.PK10Activity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.adapter.base.NewRecyclerViewBaseAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.view.CustomGridView;
import com.international.wtw.lottery.dialog.MenuPopupWindow;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.easypopup.EasyPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.NiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewConvertListener;
import com.international.wtw.lottery.dialog.nice.ViewHolder;
import com.international.wtw.lottery.event.LoginEvent;
import com.international.wtw.lottery.fragment.BaseFragment;
import com.international.wtw.lottery.json.LunbotuBean;
import com.international.wtw.lottery.json.Notice;
import com.international.wtw.lottery.json.PreferentialBean;
import com.international.wtw.lottery.json.PreferentialProBean;
import com.international.wtw.lottery.json.UserModel;
import com.international.wtw.lottery.newJason.Allgame;
import com.international.wtw.lottery.newJason.Lotteryinfo;
import com.international.wtw.lottery.utils.NetWorkUtils;
import com.international.wtw.lottery.utils.RoundedCornersTransformation;
import com.international.wtw.lottery.utils.ScreenUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.SizeUtils;
import com.international.wtw.lottery.widget.MarqueeView;
import com.international.wtw.lottery.widget.banner.BannerBaseView;
import com.international.wtw.lottery.widget.banner.MainBannerView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 *  首页
 */

public class FirstFragment extends BaseFragment implements View.OnClickListener {
    private CustomGridView recycle_menu;
    private RelativeLayout rl_home_logo;
    private View view;
    private ImageView img_default;
    private TextView tv_user_name;
    private NewRecyclerViewBaseAdapter menuAdapter;
    private MarqueeView noteTextView;
    private ImageView img_menu;
    private List<Lotteryinfo> list2 = new ArrayList<>();
    private RecyclerView bottom_recycler;
    private LunbotuBean lunbotus;
    private ImageView img_user_default;
    private Button btn_shiwan_reg;
    private LinearLayout ll_user;
    private ImageView img_more;
    private EasyPopup mMenuPopup;
    private RelativeLayout topTitle;
    private List<PreferentialProBean> promotions;
    private BaseQuickAdapter<PreferentialProBean, BaseViewHolder> mBottomAdapter;
    private String serviceUrl;
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firstview_un, null);
        view.findViewById(R.id.img_shiwan).setOnClickListener(this);
        view.findViewById(R.id.img_login_reg).setOnClickListener(this);
        topTitle = (RelativeLayout) view.findViewById(R.id.topTitle);
        noteTextView = (MarqueeView) view.findViewById(R.id.marquee);
        view.findViewById(R.id.marquee).setOnClickListener(this);
        bottom_recycler = (RecyclerView) view.findViewById(R.id.bottom_recycler);
        img_more = (ImageView) view.findViewById(R.id.img_more);
        img_more.setOnClickListener(this);

        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        img_menu = (ImageView) view.findViewById(R.id.img_menu);
        img_user_default = (ImageView) view.findViewById(R.id.img_user_default);
        btn_shiwan_reg = (Button) view.findViewById(R.id.btn_shiwan_reg);
        ll_user = (LinearLayout) view.findViewById(R.id.ll_user);
        ll_user.setOnClickListener(this);
        btn_shiwan_reg.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        recycle_menu = (CustomGridView) view.findViewById(R.id.recycle_menu);
        view.findViewById(R.id.iv_laba).setOnClickListener(this);
        view.findViewById(R.id.img_jiantou).setOnClickListener(this);
        //轮播图
        setDefaultImag();

        if (null != getActivity()) {
            if (NetWorkUtils.isNetworkAvailable(getActivity())) {
                getBannerData(getActivity());
            }
        }

        //获取彩票信息
        getLotteryInfo();

        setBottomActivities(3);

        initBottomRecycler();

        noteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        noteTextView.pause();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                        noteTextView.start();
                        break;
                }
                return true;
            }
        });
        return view;
    }


    /**
     * 获取彩票彩种信息
     */
    private void getLotteryInfo() {
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().getAllgames(FirstFragment.this, token, new HttpCallback<Allgame>() {
            @Override
            public void onSuccess(Allgame data) {
                int  size=data.getData().size();
                for (int i=0;i<size;i++){
                    list2.add(new Lotteryinfo(data.getData().get(i).getName(),
                            data.getData().get(i).getGameTypeCode(), data.getData().get(i).getCode(),
                            R.mipmap.ic_launcher));
                }

                initLotteryData(list2);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

            }
        });
    }


    private void initBottomRecycler() {
        mBottomAdapter = new BaseQuickAdapter<PreferentialProBean, BaseViewHolder>(R.layout.item_home_bottom) {
            @Override
            protected void convert(BaseViewHolder helper, PreferentialProBean item) {
                ImageView view = helper.getView(R.id.iv_promotion_img);
                Picasso.with(getContext())
                        .load(Constants.BASE_URL + item.getImage())
                        .transform(new RoundedCornersTransformation(SizeUtils.dp2px(5), 0, RoundedCornersTransformation.CornerType.TOP))
                        .into(view);
                helper.setText(R.id.tv_promotion_desc, item.getText());
            }
        };
        bottom_recycler.setHasFixedSize(true);
        bottom_recycler.setNestedScrollingEnabled(true);
        bottom_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        bottom_recycler.setAdapter(mBottomAdapter);
        mBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, promotions.get(position).getText());
                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, promotions.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    /**
     * 彩票信息
     */
    private void initLotteryData(List<Lotteryinfo> msgBeen) {
        if (null == getActivity())
            return;
        recycle_menu.setFocusable(false);
        menuAdapter = new NewRecyclerViewBaseAdapter(getActivity(), msgBeen);
        recycle_menu.setAdapter(menuAdapter);
        recycle_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lotteryinfo lotteryinfo = list2.get(position);
                navToTargetActivity(lotteryinfo.getCode());
            }
        });
    }

    private void setBottomActivities(int num) {
        HttpRequest.getInstance().getPromotions(getActivity(), num, new HttpCallback<PreferentialBean>() {
            @Override
            public void onSuccess(PreferentialBean data) {
                promotions = data.getPromotions();
                mBottomAdapter.setNewData(promotions);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getFragmentManager());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        rl_home_logo = (RelativeLayout) view.findViewById(R.id.rl_home_logo);
        String token = SharePreferencesUtil.getString(getActivity(), LotteryId.TOKEN, "");
        if (null != getActivity()) {
            if (SharePreferencesUtil.getBoolean(getActivity(), LotteryId.IS_SHI_WAN, false)) {
                if (token.equals("")) {
                    rl_home_logo.setVisibility(View.GONE);
                    img_user_default.setVisibility(View.VISIBLE);
                    tv_user_name.setVisibility(View.VISIBLE);
                    ll_user.setVisibility(View.VISIBLE);
                    btn_shiwan_reg.setVisibility(View.VISIBLE);
                    img_menu.setVisibility(View.VISIBLE);
                } else {
                    img_user_default.setVisibility(View.GONE);
                    tv_user_name.setVisibility(View.GONE);
                    btn_shiwan_reg.setVisibility(View.GONE);
                    img_menu.setVisibility(View.GONE);
                    ll_user.setVisibility(View.VISIBLE);
                    rl_home_logo.setVisibility(View.VISIBLE);
                }
                String userName = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_username, null);
                if (!TextUtils.isEmpty(userName)) {
                    tv_user_name.setText(userName);
                }
            } else {
                if (token.equals("")) {
                    img_user_default.setVisibility(View.VISIBLE);
                    tv_user_name.setVisibility(View.VISIBLE);
                    rl_home_logo.setVisibility(View.GONE);
                    btn_shiwan_reg.setVisibility(View.GONE);
                    ll_user.setVisibility(View.VISIBLE);
                    img_menu.setVisibility(View.VISIBLE);
                } else {
                    img_user_default.setVisibility(View.GONE);
                    tv_user_name.setVisibility(View.GONE);
                    rl_home_logo.setVisibility(View.VISIBLE);
                    btn_shiwan_reg.setVisibility(View.GONE);
                    ll_user.setVisibility(View.VISIBLE);
                    img_menu.setVisibility(View.GONE);
                }
                String userName = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_username, null);
                if (!TextUtils.isEmpty(userName)) {
                    tv_user_name.setText(userName);
                }
            }
        }

//        if (!TextUtils.isEmpty(Login_oid)) {
//            MoneyInfoManager.get().requestMoneyInfo();
//        }
    }


    /**
     * 跳转到对应的Activity
     *
     * @param lotteryType
     */
    private void navToTargetActivity(String lotteryType) {
        if (null == getActivity())
            return;
        switch (lotteryType) {
            case "msssc":   //秒速时时彩
//                startActivity(new Intent(getActivity(), Quick3Activity.class));
                break;
            case "msft":  //秒速飞艇
//                getActivity().startActivity(new Intent(getActivity(), PK10Activity.class));
                break;
            case "bjscpk10":  //北京赛车PK10
                getActivity().startActivity(new Intent(getActivity(), PK10Activity.class));
                break;
            case "mssc":  //秒速赛车
//                getActivity().startActivity(new Intent(getActivity(), PK10Activity.class));
                break;

        }
    }

    @Override
    public void onClick(View v) {
        if (null == getActivity())
            return;
        switch (v.getId()) {
            case R.id.ll_user:
                ((MainActivity) getActivity()).changeShowFragment(3);
                break;
            case R.id.btn_shiwan_reg:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.img_shiwan:
//                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.img_login_reg:
                HttpRequest.getInstance().loginDemo(getActivity(), new HttpCallback<UserModel>() {
                    @Override
                    public void onSuccess(UserModel data) {
                        SharePreferencesUtil.addString(getContext(), LotteryId.TOKEN, data.getOid());
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        EventBus.getDefault().postSticky(new LoginEvent());
                        ToastDialog.success(getString(R.string.login_success)).show(getFragmentManager());
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        ToastDialog.error(errorMsg).show(getFragmentManager());
                    }
                });
                break;
            case R.id.img_menu:
                SetMenu();
                break;
            case R.id.iv_laba:
            case R.id.img_jiantou:
                if (null != lunbotus) {
                    Intent intetn = new Intent(getActivity(), InfoCenterActivity.class);
                    intetn.putExtra(LotteryId.LUN_BO_TU, lunbotus);
                    intetn.putExtra("title", getString(R.string.info_center));
                    startActivity(intetn);
                }
                break;
            case R.id.img_more:
                startActivity(new Intent(getActivity(), PreferentialActivity.class));
                break;
        }
    }

    /**
     * 获取轮播图和消息
     *
     * @param context
     */
    public void getBannerData(Context context) {
        HttpRequest.getInstance().getBanner(context, new HttpCallback<LunbotuBean>() {
            @Override
            public void onSuccess(LunbotuBean lunbotu) {
                lunbotus = lunbotu;
                List<String> list = new ArrayList<>();
                if (null != lunbotu && lunbotu.getCode() == 1) {
                    if (null != lunbotu.getRotations() && lunbotu.getRotations().size() > 0
                            && null != lunbotu.getAnnouncements() && lunbotu.getAnnouncements().size() > 0) {
                        int size = lunbotu.getRotations().size();
                        for (int i = 0; i < size; i++) {
                            list.add(lunbotu.getRotations().get(i));
                        }
                        setLunBo(list, lunbotu.getAnnouncements());
                    }
                    if (null != lunbotu.getCustomerSer().getKefu()) {
                        serviceUrl = lunbotu.getCustomerSer().getKefu();
                        if (!TextUtils.isEmpty(serviceUrl)) {
                            SharePreferencesUtil.addString(getContext(), LotteryId.SERVICE_URL, serviceUrl);
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                noteTextView.setText("欢迎加入爱购彩，祝您游戏开心。。、");
                noteTextView.start();
            }
        });

    }

    public void SetMenu() {
        if (mMenuPopup == null) {
            mMenuPopup = new MenuPopupWindow(getActivity())
                    .createPopup();
        }
        mMenuPopup.showAtAnchorView(topTitle, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT);
    }

    /**
     * 设置默认的图片
     */
    private void setDefaultImag() {
        img_default = (ImageView) view.findViewById(R.id.img_default);
        int bannerHeight = (int) ((ScreenUtils.getScreenWidth(getActivity()) * 0.4f));
        ViewGroup.LayoutParams params = img_default.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = bannerHeight;
        img_default.setLayoutParams(params);
        img_default.setVisibility(View.VISIBLE);
    }

    /**
     * 设置轮播图和跑马灯
     *
     * @param list
     * @param announcements
     */
    private void setLunBo(List<String> list, List<LunbotuBean.AnnouncementsBean> announcements) {
        if (null != announcements) {
            StringBuilder buffer = new StringBuilder();
            int size = announcements.size();
            for (int i = 0; i < size; i++) {
                buffer.append(announcements.get(i).getContent()).append("    ");
            }
            noteTextView.setText(buffer.toString());
            noteTextView.start();

        }

        if (null != getActivity() && null != list) {
            RelativeLayout bannerContent = (RelativeLayout) view.findViewById(R.id.banner_cont);
            BannerBaseView banner = new MainBannerView(getActivity());
            bannerContent.addView(banner);
            banner.update(list);
            img_default.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent event) {
        Logger.d("LoginEvent");
        LoginEvent loginEvent = EventBus.getDefault().getStickyEvent(LoginEvent.class);
        EventBus.getDefault().removeStickyEvent(loginEvent);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLoginNotice();
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void getLoginNotice() {
        HttpRequest.getInstance().getLoginNotice(this, new HttpCallback<Notice>() {
            @Override
            public void onSuccess(Notice data) {
                Logger.d("isAlert = " + data.isAlert + ", msg = " + data.getMsg());
                if (data.isAlert == 1 && !TextUtils.isEmpty(data.getMsg())) {
                    showNotice(data.getMsg());
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Logger.d(errorMsg);
            }
        });
    }

    private void showNotice(String msg) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_pop_msg)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.setText(R.id.tv_msg, msg);
                        holder.setOnClickListener(R.id.iv_close, v -> dialog.dismiss());
                        holder.setOnClickListener(R.id.btn_detail, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                                intent.putExtra("time", String.valueOf(System.currentTimeMillis() / 1000));
                                intent.putExtra("text", msg);
                                getActivity().startActivity(intent);
                                dialog.dismissAllowingStateLoss();
                            }
                        });
                    }
                }).setMargin(35)
                .setOutCancel(true)
                .showDialog(getFragmentManager());
    }
}
