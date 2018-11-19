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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.first.InfoDetailActivity;
import com.international.wtw.lottery.activity.first.PreferentialActivity;
import com.international.wtw.lottery.activity.login.RegisterActivity;
import com.international.wtw.lottery.activity.lottery.happytime.ChongqingFarmActivity;
import com.international.wtw.lottery.activity.lottery.happytime.Guangdonghappy10Activity;
import com.international.wtw.lottery.activity.lottery.saiche.Bjscpk10Activity;
import com.international.wtw.lottery.activity.lottery.ssc.ChongqingsscActivity;
import com.international.wtw.lottery.activity.lottery.saiche.MiaosufeitingActivity;
import com.international.wtw.lottery.activity.lottery.saiche.MiaosusaicheActivity;
import com.international.wtw.lottery.activity.lottery.ssc.MiaosusscActivity;
import com.international.wtw.lottery.activity.lottery.dandan.PCDDActivity;
import com.international.wtw.lottery.activity.lottery.ssc.TJSSCActivity;
import com.international.wtw.lottery.activity.lottery.ssc.XJSSCActivity;
import com.international.wtw.lottery.activity.lottery.dandan.XYDDActivity;
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
import com.international.wtw.lottery.newJason.AllgameModel;
import com.international.wtw.lottery.newJason.LotteryinfoModel;
import com.international.wtw.lottery.newJason.NoticeListModel;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 */

public class FirstFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.img_shiwan)
    Button imgShiwan;
    @BindView(R.id.img_login_reg)
    Button imgLoginReg;
    @BindView(R.id.rl_home_logo)
    RelativeLayout rlHomeLogo;
    @BindView(R.id.img_user_default)
    ImageView imgUserDefault;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_user)
    LinearLayout llUser;
    @BindView(R.id.btn_shiwan_reg)
    Button btnShiwanReg;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.topTitle)
    RelativeLayout topTitle;
    @BindView(R.id.img_default)
    ImageView imgDefault;
    @BindView(R.id.banner_cont)
    RelativeLayout bannerCont;
    @BindView(R.id.marquee)
    MarqueeView marquee;
    @BindView(R.id.img_jiantou)
    ImageView imgJiantou;
    @BindView(R.id.recycle_menu)
    CustomGridView recycleMenu;
    @BindView(R.id.img_more)
    ImageView imgMore;
    @BindView(R.id.bottom_recycler)
    RecyclerView bottomRecycler;
    Unbinder unbinder;
    private List<LotteryinfoModel> list2 = new ArrayList<>();
    private NewRecyclerViewBaseAdapter menuAdapter;
    private EasyPopup mMenuPopup;
    private List<PreferentialProBean> promotions;
    private BaseQuickAdapter<PreferentialProBean, BaseViewHolder> mBottomAdapter;
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_firstview_un, null);
        unbinder = ButterKnife.bind(this, view);
        view.findViewById(R.id.img_jiantou).setOnClickListener(this);
        //获取彩票信息
        getLotteryInfo();
        setBottomActivities(3);
        //轮播图
        setDefaultImag();
        initBottomRecycler();
        marquee.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        marquee.pause();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                        marquee.start();
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
        HttpRequest.getInstance().getAllgames(FirstFragment.this, token, new HttpCallback<AllgameModel>() {
            @Override
            public void onSuccess(AllgameModel data) {
                int size = data.getData().size();
                for (int i = 0; i < size; i++) {
                    list2.add(new LotteryinfoModel(data.getData().get(i).getName(),
                            data.getData().get(i).getGameTypeCode(), data.getData().get(i).getCode(),
                            R.mipmap.ic_launcher));
                }

                initLotteryData(list2);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
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
        bottomRecycler.setHasFixedSize(true);
        bottomRecycler.setNestedScrollingEnabled(true);
        bottomRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        bottomRecycler.setAdapter(mBottomAdapter);
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
    private void initLotteryData(List<LotteryinfoModel> msgBeen) {
        if (null == getActivity())
            return;
        recycleMenu.setFocusable(false);
        menuAdapter = new NewRecyclerViewBaseAdapter(getActivity(), msgBeen);
        recycleMenu.setAdapter(menuAdapter);
        recycleMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LotteryinfoModel lotteryinfo = list2.get(position);
                navToTargetActivity(lotteryinfo.getCode(), lotteryinfo.getName());
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
        String token = SharePreferencesUtil.getString(getActivity(), LotteryId.TOKEN, "");
        if (null != getActivity()) {
            if (SharePreferencesUtil.getBoolean(getActivity(), LotteryId.IS_SHI_WAN, false)) {
                if (token.equals("")) {
                    rlHomeLogo.setVisibility(View.GONE);
                    imgUserDefault.setVisibility(View.VISIBLE);
                    tvUserName.setVisibility(View.VISIBLE);
                    llUser.setVisibility(View.VISIBLE);
                    btnShiwanReg.setVisibility(View.VISIBLE);
                    imgMenu.setVisibility(View.VISIBLE);
                } else {
                    imgUserDefault.setVisibility(View.GONE);
                    tvUserName.setVisibility(View.GONE);
                    btnShiwanReg.setVisibility(View.GONE);
                    imgMenu.setVisibility(View.GONE);
                    llUser.setVisibility(View.VISIBLE);
                    rlHomeLogo.setVisibility(View.VISIBLE);
                }
                String userName = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_username, null);
                if (!TextUtils.isEmpty(userName)) {
                    tvUserName.setText(userName);
                }
            } else {
                if (token.equals("")) {
                    imgUserDefault.setVisibility(View.VISIBLE);
                    tvUserName.setVisibility(View.VISIBLE);
                    rlHomeLogo.setVisibility(View.GONE);
                    btnShiwanReg.setVisibility(View.GONE);
                    llUser.setVisibility(View.VISIBLE);
                    imgMenu.setVisibility(View.VISIBLE);
                } else {
                    imgUserDefault.setVisibility(View.GONE);
                    tvUserName.setVisibility(View.GONE);
                    rlHomeLogo.setVisibility(View.VISIBLE);
                    llUser.setVisibility(View.VISIBLE);
                    imgMenu.setVisibility(View.GONE);
                }
                String userName = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_username, null);
                if (!TextUtils.isEmpty(userName)) {
                    tvUserName.setText(userName);
                }
            }
        }

        if (null != getActivity()) {
            if (NetWorkUtils.isNetworkAvailable(getActivity())) {
                getBannerData(getActivity());
            }
        }

    }


    /**
     * 跳转到对应的Activity
     *
     * @param lotteryType
     */
    private void navToTargetActivity(String lotteryType, String lotteryname) {
        if (null == getActivity())
            return;
        Intent intent = null;
        switch (lotteryType) {
            case LotteryId.Miaosusscai:   //秒速时时彩
                intent = new Intent(getActivity(), MiaosusscActivity.class);
                break;
            case LotteryId.Miaosufeiting:  //秒速飞艇
                intent = new Intent(getActivity(), MiaosufeitingActivity.class);
                break;
            case LotteryId.BJSCPK10:  //北京赛车PK10
                intent = new Intent(getActivity(), Bjscpk10Activity.class);
                break;
            case LotteryId.MiaosuSaiche:  //秒速赛车
                intent = new Intent(getActivity(), MiaosusaicheActivity.class);
                break;
            case LotteryId.CQSSC:  //秒速赛车
                intent = new Intent(getActivity(), ChongqingsscActivity.class);
                break;
            case LotteryId.TJSSC:  //天津时时彩
                intent = new Intent(getActivity(), TJSSCActivity.class);
                break;
            case LotteryId.XJSSC:  //新疆时时彩
                intent = new Intent(getActivity(), XJSSCActivity.class);
                break;
            case LotteryId.PCDD:  //PC蛋蛋
                intent = new Intent(getActivity(), PCDDActivity.class);
                break;
            case LotteryId.XYDD:  //PC蛋蛋
                intent = new Intent(getActivity(), XYDDActivity.class);
                break;
            case LotteryId.GDKLSF:  //PC蛋蛋
                intent = new Intent(getActivity(), Guangdonghappy10Activity.class);
                break;
            case LotteryId.CQXYNC:  //PC蛋蛋
                intent = new Intent(getActivity(), ChongqingFarmActivity.class);
                break;
        }
        if (null==intent)
            return;
        intent.putExtra("lotteryname", lotteryname);
        getActivity().startActivity(intent);
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
                String token= SharePreferencesUtil.getString(getActivity(), LotteryId.TOKEN,"");
                HttpRequest.getInstance().loginDemo(getActivity(),token, new HttpCallback<UserModel>() {
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
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().getNoticeList(FirstFragment.this, token, new HttpCallback<NoticeListModel>() {
            @Override
            public void onSuccess(NoticeListModel data) {
                StringBuilder buffer = new StringBuilder();
                int size = data.getData().size();
                for (int i = 0; i < size; i++) {
                    buffer.append(data.getData().get(i).getContent()).append("    ");
                }
                marquee.setText(String.valueOf(buffer));
                marquee.start();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                marquee.setText("欢迎光临，祝您游戏愉快。");
                marquee.start();

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
        int bannerHeight = (int) ((ScreenUtils.getScreenWidth(getActivity()) * 0.4f));
        ViewGroup.LayoutParams params = imgDefault.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = bannerHeight;
        imgDefault.setLayoutParams(params);
        imgDefault.setVisibility(View.VISIBLE);
    }

    /**
     * 设置轮播图和跑马灯
     *
     * @param list
     * @param announcements
     */
    private void setLunBo(List<String> list, List<LunbotuBean.AnnouncementsBean> announcements) {


        if (null != getActivity() && null != list) {
            BannerBaseView banner = new MainBannerView(getActivity());
            bannerCont.addView(banner);
            banner.update(list);
            imgDefault.setVisibility(View.GONE);
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


    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_shiwan, R.id.img_login_reg, R.id.btn_shiwan_reg, R.id.img_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_shiwan:
                break;
            case R.id.img_login_reg:
                break;
            case R.id.btn_shiwan_reg:
                break;
            case R.id.img_more:
                break;
        }
    }
}
