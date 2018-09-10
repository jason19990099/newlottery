package com.international.wtw.lottery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetSxlDialogAdapter;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.listener.OnSingleClickListener;

import java.util.List;

/**
 * Created by user on 2017/7/3.
 */

public class SxlViewDialog extends Dialog {
    private TextView tv_number, tv_total, tv_title;
    private Button btn_bet, btn_cancel;
    private View mContentView;
    private Context mContext;
    private RecyclerView recyclerView;
    private String type;

    public SxlViewDialog(Context context, int playCode, String lmPlayCode, final List<NewOddsBean.ListBean> list, String money, SureCallBack callBack) {
        super(context, R.style.DialogTheme);
        mContext = context;
        mContentView = View.inflate(getContext(), R.layout.dialog_sxl, null);
        setContentView(mContentView);

        tv_number = (TextView) mContentView.findViewById(R.id.tv_number);
        tv_total = (TextView) mContentView.findViewById(R.id.tv_total);
        tv_title = (TextView) mContentView.findViewById(R.id.tv_title);
        int total = 0;
        if (playCode == 0) {  // 六合彩连码
            total = calculationLmBets(lmPlayCode, list.size());
        } else if (playCode == 9) { //广东快乐十分 重庆幸运农场  连码
            total = calculationGDLmBets(lmPlayCode, list.size());
        } else if (TextUtils.isEmpty(lmPlayCode)) {  // 六合彩其他
            total = calculationBets(playCode, list.size());
        }
        tv_number.setText(total + "");
        tv_title.setText(type);
        int sum = Integer.valueOf(money) * total;
        tv_total.setText(sum + "元");
        btn_bet = (Button) mContentView.findViewById(R.id.btn_bet);
        btn_cancel = (Button) mContentView.findViewById(R.id.btn_cancel);
        recyclerView = (RecyclerView) mContentView.findViewById(R.id.recycle_bet);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == list.size() - 1 && list.size() % 2 == 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        final BetSxlDialogAdapter adapter = new BetSxlDialogAdapter(mContext, R.layout.item_sxl_bet, list, money, recyclerView);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();

        recyclerView.setAdapter(adapter);
        btn_bet.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                if (callBack != null) {
                    callBack.onSure();
                }
                dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onCancel();
                }
                dismiss();
            }
        });


        recyclerView.setLayoutParams(params);

    }

    /**
     * 六合彩连码
     */
    private int calculationLmBets(String lmPlayCode, int size) {
        int totalNum = 0;
        if ("613".equals(lmPlayCode)) { //二全中
            type = mContext.getResources().getString(R.string.er_qz);
            totalNum = doFactorTotal(size, 2);
        } else if ("615".equals(lmPlayCode)) { //中二
            type = mContext.getResources().getString(R.string.zho_er);
            totalNum = doFactorTotal(size, 2);
        } else if ("616".equals(lmPlayCode)) { //特串
            type = mContext.getResources().getString(R.string.te_chu);
            totalNum = doFactorTotal(size, 2);
        } else if ("617".equals(lmPlayCode)) { //三全中
            type = mContext.getResources().getString(R.string.san_qz);
            totalNum = doFactorTotal(size, 3);
        } else if ("619".equals(lmPlayCode)) { //中三
            type = mContext.getResources().getString(R.string.zho_san);
            totalNum = doFactorTotal(size, 3);
        } else if ("808".equals(lmPlayCode)) { //四中一
            type = mContext.getResources().getString(R.string.si_z_yi);
            totalNum = doFactorTotal(size, 4);
        }
        return totalNum;
    }

    /**
     * 六合彩 合肖  生肖连 尾数连  全不中
     */
    private int calculationBets(int playCode, int size) {
        int totalNum = 0;
        switch (playCode) {
            case 17:
                type = mContext.getResources().getString(R.string.er_x);
                totalNum = 1;
                break;
            case 18:
                type = mContext.getResources().getString(R.string.san_x);
                totalNum = 1;
                break;
            case 19:
                type = mContext.getResources().getString(R.string.si_x);
                totalNum = 1;
                break;
            case 20:
                type = mContext.getResources().getString(R.string.wu_x);
                totalNum = 1;
                break;
            case 21:
                type = mContext.getResources().getString(R.string.liu_x);
                totalNum = 1;
                break;
            //-------------------生肖连-----------------------------
            case 27:
                totalNum = doFactorTotal(size, 2);
                type = mContext.getResources().getString(R.string.sxl_2);
                break;
            case 28:
                totalNum = doFactorTotal(size, 3);
                type = mContext.getResources().getString(R.string.sxl_3);
                break;
            case 29:
                totalNum = doFactorTotal(size, 4);
                type = mContext.getResources().getString(R.string.sxl_4);
                break;
            case 30:
                totalNum = doFactorTotal(size, 5);
                type = mContext.getResources().getString(R.string.sxl_5);
                break;
            case 31:
                totalNum = doFactorTotal(size, 2);
                type = mContext.getResources().getString(R.string.sxl_2_no);
                break;
            case 32:
                totalNum = doFactorTotal(size, 3);
                type = mContext.getResources().getString(R.string.sxl_3_no);
                break;
            case 33:
                totalNum = doFactorTotal(size, 4);
                type = mContext.getResources().getString(R.string.sxl_4_no);
                break;
            //------------------尾数连-------------------------
            case 34:
                totalNum = doFactorTotal(size, 2);
                type = mContext.getResources().getString(R.string.wsl_2);
                break;
            case 35:
                totalNum = doFactorTotal(size, 3);
                type = mContext.getResources().getString(R.string.wsl_3);
                break;
            case 36:
                totalNum = doFactorTotal(size, 4);
                type = mContext.getResources().getString(R.string.wsl_4);
                break;
            case 37:
                totalNum = doFactorTotal(size, 2);
                type = mContext.getResources().getString(R.string.wsl_2_no);
                break;
            case 38:
                totalNum = doFactorTotal(size, 3);
                type = mContext.getResources().getString(R.string.sxl_3_no);
                break;
            case 39:
                totalNum = doFactorTotal(size, 4);
                type = mContext.getResources().getString(R.string.sxl_4_no);
                break;
            //--------------------------全不中---------------------------------
            case 40: //五不中
                totalNum = doFactorTotal(size, 5);
                type = mContext.getResources().getString(R.string.qbz_5);
                break;
            case 41: //六不中
                totalNum = doFactorTotal(size, 6);
                type = mContext.getResources().getString(R.string.qbz_6);
                break;
            case 42: //七不中
                totalNum = doFactorTotal(size, 7);
                type = mContext.getResources().getString(R.string.qbz_7);
                break;
            case 43: //八不中
                totalNum = doFactorTotal(size, 8);
                type = mContext.getResources().getString(R.string.qbz_8);
                break;
            case 44: //九不中
                totalNum = doFactorTotal(size, 9);
                type = mContext.getResources().getString(R.string.qbz_9);
                break;
            case 45: //十不中
                totalNum = doFactorTotal(size, 10);
                type = mContext.getResources().getString(R.string.qbz_10);
                break;
            case 46: //十一不中
                totalNum = doFactorTotal(size, 11);
                type = mContext.getResources().getString(R.string.qbz_11);
                break;
            case 47: //十二不中
                totalNum = doFactorTotal(size, 12);
                type = mContext.getResources().getString(R.string.qbz_12);
                break;
        }
        return totalNum;
    }

    /**
     * 广东快乐十分 连码
     */
    private int calculationGDLmBets(String lmPlayCode, int size) {
        int totalNum = 0;
        if ("2032".equals(lmPlayCode)) { //任选二
            type = mContext.getResources().getString(R.string.optional_two);
            totalNum = doFactorTotal(size, 2);
        } else if ("2035".equals(lmPlayCode)) { //任选三
            type = mContext.getResources().getString(R.string.optional_three);
            totalNum = doFactorTotal(size, 3);
        } else if ("2038".equals(lmPlayCode)) { //任选四
            type = mContext.getResources().getString(R.string.optional_four);
            totalNum = doFactorTotal(size, 4);
        } else if ("2039".equals(lmPlayCode)) { //任选5
            type = mContext.getResources().getString(R.string.optional_five);
            totalNum = doFactorTotal(size, 5);
        }
        return totalNum;
    }

    private int doFactorTotal(int size, int select) {
        int a = compute(size, select);
        int b = doFactorial(size - select);
        return a / (b == 0 ? 1 : b);
    }

    private int doFactorial(int n) {
        if (n < 2) {
            return n;
        } else {
            return n * doFactorial(n - 1);
        }
    }

    public int compute(int size, int select) {
        int result = 1;
        for (int i = size; i > select; i--) {
            result *= i;
        }
        return result;
    }

    public interface SureCallBack {
        void onSure();

        void onCancel();
    }
}
