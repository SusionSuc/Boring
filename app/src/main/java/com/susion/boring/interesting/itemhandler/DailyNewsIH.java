package com.susion.boring.interesting.itemhandler;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.interesting.activity.ZhiHuEssayActivity;
import com.susion.boring.interesting.model.DailyNews;

/**
 * Created by susion on 17/3/9.
 */
public class DailyNewsIH extends SimpleItemHandler<DailyNews.StoriesBean>{

    @Override
    public void onBindDataView(ViewHolder vh, DailyNews.StoriesBean data, int position) {
        vh.getTextView(R.id.item_daily_new_tv_title).setText(data.getTitle());
        SimpleDraweeView mIvImg = vh.get(R.id.item_daily_new_iv_img);
        if (!data.getImages().isEmpty()) {
            mIvImg.setImageURI(data.getImages().get(0));
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_daily_news;
    }

    @Override
    public void onClick(View v) {
        ZhiHuEssayActivity.start(mContext, mData.getId());
    }
}
