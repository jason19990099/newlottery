package com.international.wtw.lottery.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.json.TypeTitle;
import com.international.wtw.lottery.utils.LotteryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiaoXin on 2017/10/7.
 * 描述：香港六合彩, 点击
 */

public class MoreGameTypePopup extends BaseCustomPopup {

    private Context mContext;
    private int mGameCode;
    private List<TypeTitle> mTypeTitles;
    private OnItemSelectListener mOnItemSelectListener;
    private BaseQuickAdapter<TypeTitle, BaseViewHolder> mAdapter;

    public MoreGameTypePopup(Context context, int gameCode, List<TypeTitle> typeTitles) {
        super(context);
        mContext = context;
        mGameCode = gameCode;
        mTypeTitles = typeTitles;
    }

    public MoreGameTypePopup setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        mOnItemSelectListener = onItemSelectListener;
        return this;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_layout_more_type, -1, -2)
                .setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(false)
                .setAnimationStyle(R.style.AnimDown);
    }

    @Override
    protected void initViews(View view) {
        /*mAdapter = new BetTypeGridAdapter(mContext);
        GridView gridView = getView(R.id.grid_view);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTypeTitles = LotteryUtil.get().getTabTitle(mGameCode);
                int key = (int) parent.getAdapter().getItemId(position);
                int start = key / 4 * 4;
                List<TypeTitle> rgTitles = new ArrayList<>();
                int size = mTypeTitles.size();
                for (int i = start; i < start + 4 && i < size; i++) {
                    rgTitles.add(mTypeTitles.get(i));
                }
                if (start + 4 <= size) {
                    //mTypeTitles.removeAtRange(start, 4);
                    for (int i = start; i < start + 4; i++) {
                        mTypeTitles.remove(start);
                    }
                } else {
                    //mTypeTitles.removeAtRange(start, mTypeTitles.size() - start);
                    for (int i = start; i < size; i++) {
                        mTypeTitles.remove(start);
                    }
                }
                if (mOnItemSelectListener != null) {
                    mOnItemSelectListener.onItemSelect(key % 4, rgTitles);
                }
                dismiss();
            }
        });*/
        RecyclerView recyclerView = getView(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new BaseQuickAdapter<TypeTitle, BaseViewHolder>(R.layout.item_game_type_title) {
            @Override
            protected void convert(BaseViewHolder helper, TypeTitle item) {
                TextView textView = helper.getView(R.id.tv_game_type);
                textView.setTag(item.getPosition());
                textView.setText(item.getTitle());
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTypeTitles = LotteryUtil.get().getTabTitle(mGameCode);
                int key = mAdapter.getData().get(position).getPosition();
                int start = key / 4 * 4;
                List<TypeTitle> rgTitles = new ArrayList<>();
                int size = mTypeTitles.size();
                for (int i = start; i < start + 4 && i < size; i++) {
                    rgTitles.add(mTypeTitles.get(i));
                }
                if (start + 4 <= size) {
                    //mTypeTitles.removeAtRange(start, 4);
                    for (int i = start; i < start + 4; i++) {
                        mTypeTitles.remove(start);
                    }
                } else {
                    //mTypeTitles.removeAtRange(start, mTypeTitles.size() - start);
                    for (int i = start; i < size; i++) {
                        mTypeTitles.remove(start);
                    }
                }
                if (mOnItemSelectListener != null) {
                    mOnItemSelectListener.onItemSelect(key % 4, rgTitles);
                }
                dismiss();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showAtAnchorView(@NonNull View anchor, @VerticalGravity int vertGravity, @HorizontalGravity int horizGravity) {
        mAdapter.setNewData(mTypeTitles);
        super.showAtAnchorView(anchor, vertGravity, horizGravity);
    }

    public interface OnItemSelectListener {
        void onItemSelect(int position, List<TypeTitle> typeTitles);
    }
}
