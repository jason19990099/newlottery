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
import com.international.wtw.lottery.activity.first.NewAgGameActivity;
import com.international.wtw.lottery.activity.first.PreferentialActivity;
import com.international.wtw.lottery.activity.login.LoginActivity;
import com.international.wtw.lottery.activity.login.RegisterActivity;
import com.international.wtw.lottery.activity.lottery.BetActivity;
import com.international.wtw.lottery.activity.lottery.CJLuckyActivity;
import com.international.wtw.lottery.activity.lottery.Horse88Activity;
import com.international.wtw.lottery.activity.lottery.SpeedCarActivity;
import com.international.wtw.lottery.activity.lottery.GDHappyActivity;
import com.international.wtw.lottery.activity.lottery.Lucky28Activity;
import com.international.wtw.lottery.activity.lottery.LuckyFlyActivity;
import com.international.wtw.lottery.activity.lottery.MarkSixActivity;
import com.international.wtw.lottery.activity.lottery.PK10Activity;
import com.international.wtw.lottery.activity.lottery.Quick3Activity;
import com.international.wtw.lottery.activity.lottery.SSCaiActivity;
import com.international.wtw.lottery.activity.lottery.RomeSSCaiActivity;
import com.international.wtw.lottery.activity.lottery.SpeedMarkSixActivity;
import com.international.wtw.lottery.activity.lottery.SpeedSSCActivity;
import com.international.wtw.lottery.activity.lottery.VeniceActivity;
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
import com.international.wtw.lottery.json.HomeMsgBean;
import com.international.wtw.lottery.json.LotterySortingModel;
import com.international.wtw.lottery.json.LunbotuBean;
import com.international.wtw.lottery.json.Notice;
import com.international.wtw.lottery.json.PreferentialBean;
import com.international.wtw.lottery.json.PreferentialProBean;
import com.international.wtw.lottery.json.UserModel;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MoneyInfoManager;
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
 * Created by 18Steven on 2017/6/24. 首页
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
    private List<HomeMsgBean> list = new ArrayList<>();
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
    private List<String> sort;

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

        getLotterySorting();

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

    private void getLotterySorting() {
        HttpRequest.getInstance().getLotterySorting(getActivity(), new HttpCallback<LotterySortingModel>() {
            @Override
            public void onSuccess(LotterySortingModel data) {
                sort = data.getSort();
                setLotteryOrder();
                initLotteryData(list);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                setLotteryNormalOrder();
                initLotteryData(list);
                LogUtil.e("LotterySorting-onFailure-" + msgCode + "-" + errorMsg);
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
    private void initLotteryData(List<HomeMsgBean> msgBeen) {
        if (null == getActivity())
            return;
        recycle_menu.setFocusable(false);
        menuAdapter = new NewRecyclerViewBaseAdapter(getActivity(), msgBeen);
        recycle_menu.setAdapter(menuAdapter);
        recycle_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeMsgBean homeMsgBean = list.get(position);
                navToTargetActivity(homeMsgBean.getGame_code());
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

    /**
     * 设置彩票顺序（接口返回参数排序）
     */
    public void setLotteryOrder() {
        if (sort != null) {
            for (int i = 0; i < sort.size(); i++) {
                String s = sort.get(i);
                int code = Integer.parseInt(s);
                switch (code) {
                    case Constants.LOTTERY_TYPE.HORSE_88:
                        list.add(new HomeMsgBean(getString(R.string.horse_88),
                                Constants.LOTTERY_TYPE.HORSE_88,
                                R.mipmap.icon_item_hores_88));
                        break;
                    case Constants.LOTTERY_TYPE.SPEED_MARK_SIX:
                        list.add(new HomeMsgBean(getString(R.string.speed_mark_six),
                                Constants.LOTTERY_TYPE.SPEED_MARK_SIX,
                                R.mipmap.icon_item_speed_mark_six));
                        break;
                    case Constants.LOTTERY_TYPE.SPEED_CAR:
                        list.add(new HomeMsgBean(getString(R.string.speed_car),
                                Constants.LOTTERY_TYPE.SPEED_CAR,
                                R.mipmap.icon_item_speed_car));
                        break;
                    case Constants.LOTTERY_TYPE.SPEED_SSC:
                        list.add(new HomeMsgBean(getString(R.string.speed_ssc),
                                Constants.LOTTERY_TYPE.SPEED_SSC,
                                R.mipmap.icon_item_speed_ssc));
                        break;
                    case Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT:
                        list.add(new HomeMsgBean(getString(R.string.real_rowing),
                                Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT,
                                R.mipmap.icon_rowing));
                        break;
                    case Constants.LOTTERY_TYPE.ROME_LOTTERY:
                        list.add(new HomeMsgBean(getString(R.string.roma_ssc),
                                Constants.LOTTERY_TYPE.ROME_LOTTERY,
                                R.mipmap.icon_item_roma));
                        break;
                    case Constants.LOTTERY_TYPE.PJ_PK_10:
                        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNamePK10),
                                Constants.LOTTERY_TYPE.PJ_PK_10,
                                R.mipmap.icon_item_pj_pk10));
                        break;
                    case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY:
                        list.add(new HomeMsgBean(getString(R.string.mark_six),
                                Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY,
                                R.mipmap.icon_item_hk_mark_six));
                        break;
                    case Constants.LOTTERY_TYPE.REAL_VIDEO:
                        list.add(new HomeMsgBean(getString(R.string.real_video),
                                Constants.LOTTERY_TYPE.REAL_VIDEO,
                                R.mipmap.icon_item_ag));
                        break;
                    case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY:
                        list.add(new HomeMsgBean(getString(R.string.lucy_28),
                                Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY,
                                R.mipmap.icon_item_pc_dd));
                        break;
                    case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY:
                        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNameCJucky),
                                Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY,
                                R.mipmap.icon_item_cj_lucky_lottery));
                        break;
                    case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY:
                        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNameGDHappy),
                                Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY,
                                R.mipmap.icon_item_gd_happy));
                        break;
                    case Constants.LOTTERY_TYPE.CJ_LOTTERY:
                        list.add(new HomeMsgBean(getString(R.string.cq_ssc),
                                Constants.LOTTERY_TYPE.CJ_LOTTERY,
                                R.mipmap.icon_item_cj_lottery));
                        break;
                    case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY:
                        list.add(new HomeMsgBean(getString(R.string.lucy_fly),
                                Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY,
                                R.mipmap.icon_item_lucky_fly));
                        break;
                    case Constants.LOTTERY_TYPE.JS_QUICK_3:
                        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNameJSQuick3),
                                Constants.LOTTERY_TYPE.JS_QUICK_3,
                                R.mipmap.icon_item_quick_3));
                        break;
                    case Constants.LOTTERY_TYPE.ONLINE_SERVICE:
                        list.add(new HomeMsgBean(getString(R.string.service_online),
                                Constants.LOTTERY_TYPE.ONLINE_SERVICE,
                                R.mipmap.icon_item_kf));
                        break;
                    case Constants.LOTTERY_TYPE.REAL_MORE:
                        list.add(new HomeMsgBean(getString(R.string.real_more),
                                Constants.LOTTERY_TYPE.REAL_MORE,
                                R.mipmap.icon_more));
                        break;
                }
            }
        } else {
            setLotteryNormalOrder();
        }
    }


    /***
     *   设置彩票默认顺序
     */
    private void setLotteryNormalOrder() {
        list.add(new HomeMsgBean(getString(R.string.horse_88),
                Constants.LOTTERY_TYPE.HORSE_88,
                R.mipmap.icon_item_hores_88));
        list.add(new HomeMsgBean(getString(R.string.speed_mark_six),
                Constants.LOTTERY_TYPE.SPEED_MARK_SIX,
                R.mipmap.icon_item_speed_mark_six));
        list.add(new HomeMsgBean(getString(R.string.speed_car),
                Constants.LOTTERY_TYPE.SPEED_CAR,
                R.mipmap.icon_item_speed_car));
        list.add(new HomeMsgBean(getString(R.string.speed_ssc),
                Constants.LOTTERY_TYPE.SPEED_SSC,
                R.mipmap.icon_item_speed_ssc));
        list.add(new HomeMsgBean(getString(R.string.real_rowing),
                Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT,
                R.mipmap.icon_rowing));
        list.add(new HomeMsgBean(getString(R.string.roma_ssc),
                Constants.LOTTERY_TYPE.ROME_LOTTERY,
                R.mipmap.icon_item_roma));
        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNamePK10),
                Constants.LOTTERY_TYPE.PJ_PK_10,
                R.mipmap.icon_item_pj_pk10));
        list.add(new HomeMsgBean(getString(R.string.mark_six),
                Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY,
                R.mipmap.icon_item_hk_mark_six));

        list.add(new HomeMsgBean(getString(R.string.real_video),
                Constants.LOTTERY_TYPE.REAL_VIDEO,
                R.mipmap.icon_item_ag));
        list.add(new HomeMsgBean(getString(R.string.lucy_28),
                Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY,
                R.mipmap.icon_item_pc_dd));
//        list.add(new HomeMsgBean(getString(R.string.real_more),
//                Constants.LOTTERY_TYPE.REAL_MORE,
//                R.mipmap.icon_more));
        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNameCJucky),
                Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY,
                R.mipmap.icon_item_cj_lucky_lottery));
        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNameGDHappy),
                Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY,
                R.mipmap.icon_item_gd_happy));
        list.add(new HomeMsgBean(getString(R.string.cq_ssc),
                Constants.LOTTERY_TYPE.CJ_LOTTERY,
                R.mipmap.icon_item_cj_lottery));
        list.add(new HomeMsgBean(getString(R.string.lucy_fly),
                Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY,
                R.mipmap.icon_item_lucky_fly));
        list.add(new HomeMsgBean(getString(R.string.LotteryTypeNameJSQuick3),
                Constants.LOTTERY_TYPE.JS_QUICK_3,
                R.mipmap.icon_item_quick_3));

        list.add(new HomeMsgBean(getString(R.string.service_online),
                Constants.LOTTERY_TYPE.ONLINE_SERVICE,
                R.mipmap.icon_item_kf));
    }

    @Override
    public void onResume() {
        super.onResume();
        rl_home_logo = (RelativeLayout) view.findViewById(R.id.rl_home_logo);
        String Login_oid = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, null);
        if (null != getActivity()) {
            if (SharePreferencesUtil.getBoolean(getActivity(), LotteryId.IS_SHI_WAN, false)) {
                if (null != Login_oid) {
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
                if (null != Login_oid) {
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

        if (!TextUtils.isEmpty(Login_oid)) {
            MoneyInfoManager.get().requestMoneyInfo();
        }
    }


    /**
     * 跳转到对应的Activity
     *
     * @param lotteryType
     */
    private void navToTargetActivity(int lotteryType) {
        if (null == getActivity())
            return;
        switch (lotteryType) {
            case Constants.LOTTERY_TYPE.JS_QUICK_3:
                startActivity(new Intent(getActivity(), Quick3Activity.class));
                break;
            case Constants.LOTTERY_TYPE.PJ_PK_10:
                getActivity().startActivity(new Intent(getActivity(), PK10Activity.class));
                break;
            case Constants.LOTTERY_TYPE.SPEED_MARK_SIX:
                getActivity().startActivity(new Intent(getActivity(), SpeedMarkSixActivity.class));
                break;
            case Constants.LOTTERY_TYPE.HORSE_88:
                getActivity().startActivity(new Intent(getActivity(), Horse88Activity.class));
                break;
            case Constants.LOTTERY_TYPE.SPEED_CAR:
                getActivity().startActivity(new Intent(getActivity(), SpeedCarActivity.class));
                break;
            case Constants.LOTTERY_TYPE.SPEED_SSC:
                getActivity().startActivity(new Intent(getActivity(), SpeedSSCActivity.class));
                break;
            case Constants.LOTTERY_TYPE.CJ_LOTTERY:
                getActivity().startActivity(new Intent(getActivity(), SSCaiActivity.class));
                break;
            case Constants.LOTTERY_TYPE.ROME_LOTTERY:
                getActivity().startActivity(new Intent(getActivity(), RomeSSCaiActivity.class));
                break;
            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY:
                getActivity().startActivity(new Intent(getActivity(), LuckyFlyActivity.class));
                break;
            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY:
                getActivity().startActivity(new Intent(getActivity(), GDHappyActivity.class));
                break;
            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY:
                getActivity().startActivity(new Intent(getActivity(), CJLuckyActivity.class));
                break;
            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY:
                getActivity().startActivity(new Intent(getActivity(), MarkSixActivity.class));
                break;
            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY:
                getActivity().startActivity(new Intent(getActivity(), Lucky28Activity.class));
                break;
            case Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT:
                getActivity().startActivity(new Intent(getActivity(), VeniceActivity.class));
                break;
            case Constants.LOTTERY_TYPE.ONLINE_SERVICE:
                if (!TextUtils.isEmpty(serviceUrl)) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, getString(R.string.service_online));
                    intent.putExtra(WebViewActivity.EXTRA_WEB_URL, serviceUrl);
                    intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                    getActivity().startActivity(intent);
                }
                break;
            case Constants.LOTTERY_TYPE.REAL_VIDEO:
                getActivity().startActivity(new Intent(getActivity(), NewAgGameActivity.class));
                break;
            case Constants.LOTTERY_TYPE.REAL_MORE:
                if (menuAdapter.getCount() == 9) {
                    menuAdapter.addItemNum(list.size());
                } else {
                    menuAdapter.addItemNum(9);
                }
                menuAdapter.notifyDataSetChanged();
                break;
            default:
                Intent intent = new Intent(getActivity(), BetActivity.class);
                intent.putExtra(BetActivity.GAME_CODE, lotteryType);
                startActivity(intent);
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
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.img_login_reg:
                HttpRequest.getInstance().loginDemo(getActivity(), new HttpCallback<UserModel>() {
                    @Override
                    public void onSuccess(UserModel data) {
                        SharePreferencesUtil.addString(getContext(), LotteryId.Login_oid, data.getOid());
                        SharePreferencesUtil.addString(getContext(), LotteryId.Login_username, data.getUsername());
                        SharePreferencesUtil.addString(getContext(), LotteryId.Login_realname, data.getRealname());
                        SharePreferencesUtil.addString(getContext(), LotteryId.Login_qqskype, data.getQqskype());
                        SharePreferencesUtil.addString(getContext(), LotteryId.Login_monery, data.getMoney());
                        SharePreferencesUtil.addBoolean(getContext(), LotteryId.IS_SHI_WAN, true);
                        SharePreferencesUtil.addString(getContext(), LotteryId.Login_phone, data.getTelphone());

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
                ToastDialog.error(errorMsg).show(getFragmentManager());
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
