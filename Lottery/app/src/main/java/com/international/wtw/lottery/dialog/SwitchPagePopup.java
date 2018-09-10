package com.international.wtw.lottery.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.lottery.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SwitchPagePopup extends BaseCustomPopup implements View.OnClickListener {

    private int currentPage = 1;
    private BaseQuickAdapter<Integer, BaseViewHolder> mAdapter;
    private RecyclerView mRecyclerView;
    private OnItemClickListener onItemClickListener;

    private TextView tvCurrentPage;
    private TextView tvPreviousPage;
    private TextView tvNextPage;

    public SwitchPagePopup(Context context) {
        super(context);
    }

    public SwitchPagePopup setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_switch_page, ViewGroup.LayoutParams.MATCH_PARENT, -2);
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setDimValue(0.2f);
        mAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_popup_switch_page) {

            @Override
            protected void convert(BaseViewHolder helper, Integer item) {
                helper.setText(R.id.tv_page_index, String.format(Locale.CHINA, "第%d页", item));
                if (item == currentPage) {
                    helper.itemView.setSelected(true);
                } else {
                    helper.itemView.setSelected(false);
                }
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Integer item = (Integer) adapter.getData().get(position);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item);
                    dismiss();
                }
            }
        });
    }

    @Override
    protected void initViews(View view) {
        LinearLayout llCurrentPage = getView(R.id.llCurrentPage);
        tvCurrentPage = getView(R.id.tvCurrentPage);
        //tvCurrentPage.setEnabled(true);
        llCurrentPage.setOnClickListener(this);
        tvPreviousPage = getView(R.id.tvPreviousPage);
        tvPreviousPage.setOnClickListener(this);
        tvNextPage = getView(R.id.tvNextPage);
        tvNextPage.setOnClickListener(this);
        mRecyclerView = getView(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setCurrentPage(int currentPage, int pageCount) {
        this.currentPage = currentPage;
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            pages.add(i);
        }
        mAdapter.setNewData(pages);

        tvCurrentPage.setText(String.format(Locale.CHINA, "第%d页", currentPage));
        if (currentPage == 1 && tvPreviousPage.isEnabled()) {
            tvPreviousPage.setEnabled(false);
        } else if (currentPage != 1 && !tvPreviousPage.isEnabled()) {
            tvPreviousPage.setEnabled(true);
        }
        if (currentPage == pageCount && tvNextPage.isEnabled()) {
            tvNextPage.setEnabled(false);
        } else if (currentPage != pageCount && !tvNextPage.isEnabled()) {
            tvNextPage.setEnabled(true);
        }

        mRecyclerView.scrollToPosition(currentPage - 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCurrentPage:
                dismiss();
                break;
            case R.id.tvPreviousPage:
                if (onItemClickListener != null) {
                    onItemClickListener.onPreviousPage();
                    dismiss();
                }
                break;
            case R.id.tvNextPage:
                if (onItemClickListener != null) {
                    onItemClickListener.onNextPage();
                    dismiss();
                }
                break;
        }
    }

    public interface OnItemClickListener {

        void onPreviousPage();

        void onNextPage();

        void onItemClick(int currentPage);
    }
}
