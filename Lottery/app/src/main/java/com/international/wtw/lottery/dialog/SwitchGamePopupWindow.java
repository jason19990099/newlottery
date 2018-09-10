package com.international.wtw.lottery.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PopupMenuAdapter;
import com.international.wtw.lottery.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.lottery.json.PopupMenuBean;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SwitchGamePopupWindow extends BaseCustomPopup {

    private Context context;
    private int gameCode;
    private OnItemClickListener onItemClickListener;
    private GridView gv_popup;
    private List<PopupMenuBean> list;

    public SwitchGamePopupWindow(Context context, int gameCode) {
        super(context);
        this.context = context;
        this.gameCode = gameCode;
    }

    public SwitchGamePopupWindow setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_layout_switch_game, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setAnimationStyle(R.style.AnimDown);
    }

    @Override
    protected void initViews(View view) {

//        LinearLayout ll_container = (LinearLayout) view.findViewById(R.id.ll_container);
//        ll_container.getBackground().setAlpha(240);

        gv_popup = (GridView) view.findViewById(R.id.gv_popup);
        list = new ArrayList<>();

//        LinearLayout llContainer = getView(R.id.ll_container);
        Map<Integer, String> otherGames = LotteryUtil.get().getAllOtherGames(gameCode);
//        llContainer.removeAllViews();
        Set<Map.Entry<Integer, String>> entries = otherGames.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {

            PopupMenuBean Pmb = new PopupMenuBean();
            Pmb.setCode(entry.getKey());
            Pmb.setName(entry.getValue());
            list.add(Pmb);

//            TextView textView = new TextView(context);
//            textView.setTag(entry.getKey());
//            textView.setText(entry.getValue());
//            textView.setTextSize(16);
//            textView.setTextColor(Color.WHITE);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(40)
//            );
//            textView.setGravity(Gravity.CENTER);
//            textView.setOnClickListener(this);
//            llContainer.addView(textView, params);
        }
        PopupMenuAdapter adapter = new PopupMenuAdapter(context,list);
        gv_popup.setAdapter(adapter);
        gv_popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int gameCode = (int) view.getTag();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(list.get(position).getCode());
                }
                dismiss();
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int gameCode);
    }
}
