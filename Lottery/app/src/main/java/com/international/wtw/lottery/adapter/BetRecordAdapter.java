package com.international.wtw.lottery.adapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.BetRecord;
import com.international.wtw.lottery.json.BetRecordDetail;
import com.international.wtw.lottery.json.LoadMoreItem;
import java.util.List;

/**
 * Created by XiaoXin on 2017/9/26.
 * 描述：
 */

public class BetRecordAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_1_1 = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BetRecordAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.channel_expandablelistview2);
        addItemType(TYPE_LEVEL_1, R.layout.channel_gridview_item);
        addItemType(TYPE_LEVEL_1_1, R.layout.view_load_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case TYPE_LEVEL_0:
                BetRecord.SummaryBean.RecordItem recordItem = (BetRecord.SummaryBean.RecordItem) item;
                if (recordItem.isExpanded()) {
                    helper.setText(R.id.tv_rq, recordItem.dateTime)
                            .setText(R.id.tv_js, recordItem.type == 0 ? "未结" : "已结")
                            .setText(R.id.tv_tzds, recordItem.allNum + "")
                            .setText(R.id.tv_xzje, recordItem.allMoney)
                            .setText(R.id.tv_ksje, recordItem.allWinMoney)
                            .setText(R.id.tv_jrts, recordItem.allCut);
                    helper.setTextColor(R.id.tv_rq, mContext.getResources().getColor(R.color.money_color));
                    helper.setTextColor(R.id.tv_js, mContext.getResources().getColor(R.color.money_color));
                } else {
                    helper.setText(R.id.tv_rq, recordItem.dateTime)
                            .setText(R.id.tv_js, recordItem.type == 0 ? "未结" : "已结")
                            .setText(R.id.tv_tzds, recordItem.allNum + "")
                            .setText(R.id.tv_xzje, recordItem.allMoney)
                            .setText(R.id.tv_ksje, recordItem.allWinMoney)
                            .setText(R.id.tv_jrts, recordItem.allCut);
                    helper.setTextColor(R.id.tv_rq, mContext.getResources().getColor(R.color.color_primary));
                    helper.setTextColor(R.id.tv_js, mContext.getResources().getColor(R.color.color_primary));
                }
                break;
            case TYPE_LEVEL_1:
                BetRecordDetail.ResBean recordDetail = (BetRecordDetail.ResBean) item;
                helper.setText(R.id.tv_zdh, recordDetail.getNo());
                helper.setText(R.id.tv_yx_wf, "第" + recordDetail.getRound() + "期");
                helper.setText(R.id.tv_yx_zl, recordDetail.getGame_name());
                helper.setText(R.id.tv_yxwf, recordDetail.getDetail());
                helper.setText(R.id.tv_xzsj, recordDetail.getTime());
                helper.setText(R.id.tv_xzje, recordDetail.getMoney());
                helper.setText(R.id.tv_kyje, recordDetail.getWin_money());
                break;
            case TYPE_LEVEL_1_1:
                LoadMoreItem loadMoreItem = (LoadMoreItem) item;
                this.convert2(loadMoreItem, helper);
                break;
        }
    }

    public void convert2(LoadMoreItem loadMoreItem, BaseViewHolder holder) {
        switch (loadMoreItem.getStatus()) {
            case LoadMoreItem.STATUS_LOADING:
                visibleLoading(holder, true);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case LoadMoreItem.STATUS_FAIL:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                holder.setText(R.id.tv_prompt, "加载失败, 点击重试");
                holder.addOnClickListener(R.id.load_more_load_fail_view);
                visibleLoadEnd(holder, false);
                break;
            case LoadMoreItem.STATUS_END:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, true);
                break;
            case LoadMoreItem.STATUS_HAS_MORE:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                holder.setText(R.id.tv_prompt, "点击加载更多");
                holder.addOnClickListener(R.id.load_more_load_fail_view);
                visibleLoadEnd(holder, false);
                break;
        }
    }

    private void visibleLoading(BaseViewHolder holder, boolean visible) {
        holder.setVisible(R.id.load_more_loading_view, visible);
    }

    private void visibleLoadFail(BaseViewHolder holder, boolean visible) {
        holder.setVisible(R.id.load_more_load_fail_view, visible);
    }

    private void visibleLoadEnd(BaseViewHolder holder, boolean visible) {
        final int loadEndViewId = R.id.load_more_load_end_view;
        holder.setVisible(loadEndViewId, visible);
    }
}
