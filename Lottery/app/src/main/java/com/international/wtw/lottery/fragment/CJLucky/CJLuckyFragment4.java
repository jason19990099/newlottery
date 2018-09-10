package com.international.wtw.lottery.fragment.CJLucky;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetLianItemAdapter;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.view.CustomGridView;
import com.international.wtw.lottery.dialog.RecyclerViewDialog;
import com.international.wtw.lottery.dialog.SxlViewDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.fragment.BetBaseFragment;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.listener.StateInterface;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by XIAOYAN on 2017/7/12.
 */

public class CJLuckyFragment4 extends BetBaseFragment implements StateInterface {
    private View view;
    private View method_one, method_two, method_three, method_four;
    private List<NewOddsBean> lianmOddBetMethodsInfo; //存放连码投注方式
    private List<NewOddsBean.ListBean> alreadyBettedBeans;
    private List<NewOddsBean.ListBean> lianMaOddsBeans; //存放被投注的球的信息
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<NewOddsBean.ListBean>(); //被选中的bean
    private boolean IsFeng;
    private int currentPlayTypeCode = Constants.GD_HAPPY_PLAY_TYPE.JOINT_MARK; //这里面，如果是两面盘直接使用；如果是FIRST_POINT，还要进去取值，获取具体是哪一个球,作为玩法代码
    private Gson gson = new Gson();
    //连码
    private List<View> lianMaTopItemView = new ArrayList<>();
    private SxlViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.view_gd_happy_lian, null);
        mFlOddsContainer.addView(view);
        initalLianMaLayout();
        getData();

        return rootView;
    }

    /**
     * 连码投注方式界面修改
     */
    private void initalLianMaLayout() {
        if (null == getActivity())
            return;
        refreshView();
        method_one = view.findViewById(R.id.method_one);
        method_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lianmOddBetMethodsInfo == null || lianmOddBetMethodsInfo.isEmpty()) {
                    return;
                }
                NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(0);
                boolean selected = one.getSelectedState();
                one.setSelectedState(!selected);
                ImageView angle = (ImageView) method_one.findViewById(R.id.img_angle);
                RelativeLayout bg = (RelativeLayout) method_one;
                if (selected) {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
                    angle.setVisibility(View.GONE);
                } else {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_selected_item));
                    angle.setVisibility(View.VISIBLE);
                }

                //disable other methods selected state
                disableMethod_two();
                disableMethod_three();
                disableMethod_four();

                refreshView();
            }
        });

        method_two = view.findViewById(R.id.method_two);
        method_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(1);
                boolean selected = one.getSelectedState();
                one.setSelectedState(!selected);
                RelativeLayout bg = (RelativeLayout) method_two;
                ImageView angle = (ImageView) method_two.findViewById(R.id.img_angle);
                if (selected) {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
                    angle.setVisibility(View.GONE);
                } else {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_selected_item));
                    angle.setVisibility(View.VISIBLE);
                }

                //disable other methods selected state
                disableMethod_one();
                disableMethod_three();
                disableMethod_four();

                refreshView();

            }
        });

        method_three = view.findViewById(R.id.method_three);
        method_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(2);
                boolean selected = one.getSelectedState();
                one.setSelectedState(!selected);
                RelativeLayout bg = (RelativeLayout) method_three;
                ImageView angle = (ImageView) method_three.findViewById(R.id.img_angle);
                if (selected) {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
                    angle.setVisibility(View.GONE);
                } else {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_selected_item));
                    angle.setVisibility(View.VISIBLE);
                }

                disableMethod_one();
                disableMethod_two();
                disableMethod_four();

                refreshView();

            }
        });

        method_four = view.findViewById(R.id.method_four);
        method_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(3);
                boolean selected = one.getSelectedState();
                one.setSelectedState(!selected);
                RelativeLayout bg = (RelativeLayout) method_four;
                ImageView angle = (ImageView) method_four.findViewById(R.id.img_angle);
                if (selected) {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
                    angle.setVisibility(View.GONE);
                } else {
                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_selected_item));
                    angle.setVisibility(View.VISIBLE);
                }
                //disable other methods selected state
                disableMethod_one();
                disableMethod_two();
                disableMethod_three();

                refreshView();

            }
        });
        //底部的球，初始化
        initalLianMaPoints();
    }


    /**
     * 刷新界面
     */
    private void refreshView() {
        alreadyBettedBeans = findTheSelectedBeans();
        if (alreadyBettedBeans.size() != 0) {
            clearbettingseleter();
            if (null != getActivity()) {
                ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
            }
        }
    }

    /**
     * 连码投注的球
     * 为连码投注创建本地数据源
     * 点击选中，修改该球对应的数据源状态
     */
    private void initalLianMaPoints() {
        lianMaOddsBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NewOddsBean.ListBean one = new NewOddsBean.ListBean();
            one.setName(String.valueOf(i + 1));
            one.setSelectedState(false);
            lianMaOddsBeans.add(one);
        }

        CustomGridView gridView = (CustomGridView) view.findViewById(R.id.gridViewItems);
        BetLianItemAdapter adapter = new BetLianItemAdapter(lianMaOddsBeans, getActivity(), IsFeng, new BetLianItemAdapter.ItemBettingCallback() {
            @Override
            public void ItemClick() {
                int size = findTheSelectedBeans().size();

                if (null != getActivity()) {
                    ((ShowSelectNumbersInterface) getActivity()).showSelextNum(size);
                }
            }
        });
        gridView.setAdapter(adapter);
        gridView.setFocusable(false);
    }

    @Override
    protected void refreshOdds() {
        getData();
    }

    private void getData() {
        requestOdds(mActivity.getLotteryType(), Constants.GD_HAPPY_PLAY_TYPE.JOINT_MARK);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        lianmOddBetMethodsInfo = data;
        showOdds();
        setupLianMaViews(lianMaTopItemView, lianmOddBetMethodsInfo);
    }

    /**
     * 设置连码投注方式
     */
    private void setupLianMaViews(List<View> topItemViews, List<NewOddsBean> beans) {
        if (null == topItemViews || null == beans || null == getActivity()) {
            return;
        }
        if (null != getActivity()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NewOddsBean oneBean = beans.get(0);
                    //设置method_one
                    TextView methodOneType = (TextView) method_one.findViewById(R.id.lmp_lv_item_tv_pm);
                    methodOneType.setText(oneBean.getList().get(0).getName());
                    TextView methodOneOdd = (TextView) method_one.findViewById(R.id.lmp_lv_item_tv_pl);
                    methodOneOdd.setText(oneBean.getList().get(0).getOdds());


                    //设置method_two
                    TextView methodTwoType = (TextView) method_two.findViewById(R.id.lmp_lv_item_tv_pm);
                    methodTwoType.setText(oneBean.getList().get(1).getName());
                    TextView methodTwoOdd = (TextView) method_two.findViewById(R.id.lmp_lv_item_tv_pl);
                    methodTwoOdd.setText(oneBean.getList().get(1).getOdds());

                    //设置method_three
                    TextView methodThreeType = (TextView) method_three.findViewById(R.id.lmp_lv_item_tv_pm);
                    methodThreeType.setText(oneBean.getList().get(2).getName());
                    TextView methodThreeOdd = (TextView) method_three.findViewById(R.id.lmp_lv_item_tv_pl);
                    methodThreeOdd.setText(oneBean.getList().get(2).getOdds());

                    //设置method_four
                    TextView methodFourType = (TextView) method_four.findViewById(R.id.lmp_lv_item_tv_pm);
                    methodFourType.setText(oneBean.getList().get(3).getName());
                    TextView methodFourOdd = (TextView) method_four.findViewById(R.id.lmp_lv_item_tv_pl);
                    methodFourOdd.setText(oneBean.getList().get(3).getOdds());

                    //set the default selected method
                    NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(0);
                    one.setSelectedState(false);
                    RelativeLayout bg = (RelativeLayout) method_one;
                    ImageView angle = (ImageView) method_one.findViewById(R.id.img_angle);

                    bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_selected_item));
                    angle.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    private void disableMethod_one() {

        NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(0);
        one.setSelectedState(false);
        RelativeLayout bg = (RelativeLayout) method_one;
        ImageView angle = (ImageView) method_one.findViewById(R.id.img_angle);

        bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
        angle.setVisibility(View.GONE);
    }

    private void disableMethod_two() {

        NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(1);
        one.setSelectedState(false);
        RelativeLayout bg = (RelativeLayout) method_two;
        ImageView angle = (ImageView) method_two.findViewById(R.id.img_angle);
        bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
        angle.setVisibility(View.GONE);

    }

    private void disableMethod_three() {

        NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(2);
        one.setSelectedState(false);
        RelativeLayout bg = (RelativeLayout) method_three;
        ImageView angle = (ImageView) method_three.findViewById(R.id.img_angle);

        bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
        angle.setVisibility(View.GONE);


    }

    private void disableMethod_four() {
        NewOddsBean.ListBean one = lianmOddBetMethodsInfo.get(0).getList().get(3);

        one.setSelectedState(false);
        RelativeLayout bg = (RelativeLayout) method_four;
        ImageView angle = (ImageView) method_four.findViewById(R.id.img_angle);

        bg.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_normal_item));
        angle.setVisibility(View.GONE);
    }

    /**
     * 查找选中状态的BetItem
     * 如果是两面盘，直接查找otherOddBeans
     * 如果不是两面盘，查找pointOddsBeanInfo里面的选中的Item
     *
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans() {
        return findTheLianMaSelectedBeans(lianMaOddsBeans);
    }

    /**
     * 查找连码中选中的数据
     *
     * @param lianMaOddsBeans
     * @return
     */
    private List<NewOddsBean.ListBean> findTheLianMaSelectedBeans(List<NewOddsBean.ListBean> lianMaOddsBeans) {
        List<NewOddsBean.ListBean> selectedBean = new ArrayList<>();
        if (null != lianMaOddsBeans) {
            int size = lianMaOddsBeans.size();
            for (int i = 0; i < size; i++) {
                NewOddsBean.ListBean one;
                one = lianMaOddsBeans.get(i);
                if (one.getSelectedState()) {
                    selectedBean.add(one);
                }
            }
        }
        return selectedBean;
    }

    /**
     * 清除选中
     */
    public void clearbettingseleter() {
        alreadyBettedBeans = findTheSelectedBeans();
        if (null == alreadyBettedBeans) {
            return;
        }
        int selectSize = alreadyBettedBeans.size();
        if (selectSize > 0) {
            for (int i = 0; i < selectSize; i++) {
                NewOddsBean.ListBean listBean = alreadyBettedBeans.get(i);
                listBean.setSelectedState(false);
            }
            alreadyBettedBeans.clear();
            initalLianMaLayout();
        }

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }

    }

    /**
     * 开封盘刷新界面
     */
    private void openAndCloseInitView() {
        clearbettingseleter();
        initalLianMaLayout();
    }


    public void getBet() {
        if (null == getActivity())
            return;
        bet_money = SharePreferencesUtil.getString(getActivity(), LotteryId.LOTTERY_BET_MONEY, "0");
        LotteryId.BETTING_MONEY = bet_money;
        if (TextUtils.isEmpty(bet_money) || bet_money.equals("0")) {
            ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
            return;
        }
        selectedBeans = findTheSelectedBeans();
        if (selectedBeans == null || selectedBeans.size() == 0) {
            ToastDialog.error(getString(R.string.select_betting_item)).show(getFragmentManager());
            return;
        }

        if (currentPlayTypeCode == Constants.GD_HAPPY_PLAY_TYPE.JOINT_MARK) {
            NewOddsBean one = lianmOddBetMethodsInfo.get(0);
            NewOddsBean.ListBean selectedMethodType = null;
            for (int i = 0; i < one.getList().size(); i++) {
                NewOddsBean.ListBean oneMethod = one.getList().get(i);
                if (oneMethod.getSelectedState()) {
                    selectedMethodType = oneMethod;
                    break;
                }
            }
            if (selectedMethodType == null) {
                selectedMethodType = one.getList().get(0);
            }
            String bet_max_lest = getResources().getString(R.string.bet_max_item);
            String bet_lest_item = getResources().getString(R.string.bet_lest_item);
            switch (selectedMethodType.getKey()) {
                case "2032":  //任选二
                    if (selectedBeans.size() < 2 || selectedBeans.size() > 8) {
                        ToastDialog.error(String.format(bet_lest_item, 2) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                        return;
                    }
                    break;
                case "2035":  //任选三
                    if (selectedBeans.size() < 3 || selectedBeans.size() > 8) {
                        ToastDialog.error(String.format(bet_lest_item, 3) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                        return;
                    }
                    break;
                case "2038":  //任选四
                    if (selectedBeans.size() < 4 || selectedBeans.size() > 6) {
                        ToastDialog.error(String.format(bet_lest_item, 4) + String.format(bet_max_lest, 6)).show(getFragmentManager());
                        return;
                    }
                    break;
                case "2039":  //任选五
                    if (selectedBeans.size() < 5 || selectedBeans.size() > 6) {
                        ToastDialog.error(String.format(bet_lest_item, 5) + String.format(bet_max_lest, 6)).show(getFragmentManager());
                        return;
                    }
                    break;
            }
            mDialog = new SxlViewDialog(getActivity(), Constants.GD_HAPPY_PLAY_TYPE.JOINT_MARK, selectedMethodType.getKey(), selectedBeans, bet_money, new SxlViewDialog.SureCallBack() {
                @Override
                public void onSure() {
                    performBetting(getBetParam());
                    clearbettingseleter();
                }

                @Override
                public void onCancel() {
                    clearbettingseleter();
                }
            });
            mDialog.show();
            return;
        }

        RecyclerViewDialog dialog = new RecyclerViewDialog(getActivity(), selectedBeans, bet_money, new RecyclerViewDialog.SureCallBack() {
            @Override
            public void onSure() {
                performBetting(getBetParam());
                clearbettingseleter();
            }

            @Override
            public void onCancel() {
                alreadyBettedBeans = findTheSelectedBeans();
                if (alreadyBettedBeans.size() != 0) {
                    clearbettingseleter();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            clearbettingseleter();
        }
    }

    /**
     * 实现投注
     *
     * @param paramMap
     */
    protected void performBetting(@NonNull ArrayMap<String, String> paramMap) {
        paramMap.put("oid", SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        paramMap.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(paramMap)).toString());
        requestBet(body);
    }

    protected ArrayMap<String, String> getBetParam() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put("game_code", String.valueOf(mActivity.getLotteryType()));
        betParams.put("type_code", String.valueOf(Constants.GD_HAPPY_PLAY_TYPE.JOINT_MARK));
        betParams.put("round", SharePreferencesUtil.getString(getActivity(), "round", ""));

        alreadyBettedBeans = findTheSelectedBeans();
        //连面盘；1到8球；连码，传递的投注参数不同
        switch (currentPlayTypeCode) {

            case Constants.GD_HAPPY_PLAY_TYPE.DOUBLE_SIDE:
            case Constants.GD_HAPPY_PLAY_TYPE.ONE_TO_FOUR:
            case Constants.GD_HAPPY_PLAY_TYPE.FIVE_TO_EIGHT:
                for (int i = 0; i < alreadyBettedBeans.size(); i++) {
                    NewOddsBean.ListBean oneBetItem = alreadyBettedBeans.get(i);
                    betParams.put(oneBetItem.getKey(), bet_money);
                }
                break;

            case Constants.GD_HAPPY_PLAY_TYPE.JOINT_MARK:
                setLianMaParam(betParams);
                break;

        }

        return betParams;
    }

    /**
     * 设置连码投注
     *
     * @param betParams
     */
    private void setLianMaParam(ArrayMap<String, String> betParams) {
        if (lianmOddBetMethodsInfo == null || lianmOddBetMethodsInfo.size() <= 0) {
            return;
        }
        NewOddsBean one = lianmOddBetMethodsInfo.get(0);
        NewOddsBean.ListBean selectedMethodType = null;
        for (int i = 0; i < one.getList().size(); i++) {
            NewOddsBean.ListBean oneMethod = one.getList().get(i);
            if (oneMethod.getSelectedState()) {
                selectedMethodType = oneMethod;
                break;
            }
        }
        if (selectedMethodType == null) {
            selectedMethodType = one.getList().get(0);
        }

        StringBuilder selectedPoints = new StringBuilder();
        List<NewOddsBean.ListBean> selectedItems = new ArrayList<NewOddsBean.ListBean>();
        for (int i = 0; i < lianMaOddsBeans.size(); i++) {
            NewOddsBean.ListBean onePoint = lianMaOddsBeans.get(i);
            if (onePoint.getSelectedState()) {
                selectedItems.add(onePoint);
            }
        }
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedPoints.append(selectedItems.get(i).getName());
            if (i < selectedItems.size() - 1) {
                selectedPoints.append(",");
            }
        }

        // put param
        betParams.put("typecode", selectedMethodType.getKey());
        betParams.put("number", selectedPoints.toString());
        betParams.put("betmoney", bet_money);

    }

    @Override
    public void open(Boolean b) {
        IsFeng = b;
        openAndCloseInitView();
    }

    @Override
    public void close(Boolean b) {
        IsFeng = b;
        openAndCloseInitView();
        if (b && mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }

}
