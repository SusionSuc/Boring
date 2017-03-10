package com.susion.boring.interesting.itemhandler;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.interesting.activity.ZhiHuDailyNewsActivity;
import com.susion.boring.interesting.model.InterestingColumn;
import com.susion.boring.interesting.model.InterestingColumnType;

/**
 * Created by susion on 17/3/9.
 */
public class InterestingPageIH extends SimpleItemHandler<InterestingColumn> {

    @Override
    public void onBindDataView(ViewHolder vh, InterestingColumn data, int position) {
        vh.getTextView(R.id.item_interesting_page_tv_title).setText(data.getTitle());
        vh.getTextView(R.id.item_interesting_page_tv_count).setText(data.getContentCount() + "");
        vh.getTextView(R.id.item_interesting_page_tv_desc).setText(data.getDesc());
        SimpleDraweeView sdv = vh.get(R.id.item_interesting_page_iv_bg);
        sdv.setImageURI(data.getBgUrl());
        sdv.getDrawable().setAlpha(200);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_interesting_page;
    }

    @Override
    public void onClick(View v) {
        switch (mData.getType()) {
            case InterestingColumnType.COLUMN_TYPE_ZHIHU:
                ZhiHuDailyNewsActivity.start(mContext);
                break;
            case InterestingColumnType.COLUMN_TYPE_LAUGH:

                break;
            case InterestingColumnType.COLUMN_TYPE_FIGHT_PICTURE:

                break;
        }
    }
}
