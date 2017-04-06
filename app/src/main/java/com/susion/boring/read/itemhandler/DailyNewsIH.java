package com.susion.boring.read.itemhandler;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.read.mvp.entity.NewsDetail;
import com.susion.boring.read.mvp.view.ZhiHuEssayActivity;

/**
 * Created by susion on 17/3/9.
 */
public class DailyNewsIH extends SimpleItemHandler<NewsDetail> {

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
    }

    @Override
    public void onBindDataView(ViewHolder vh, NewsDetail data, int position) {
        vh.getTextView(R.id.item_daily_new_tv_title).setText(data.getTitle());
        SimpleDraweeView mIvImg = vh.get(R.id.item_daily_new_iv_img);
        if (data.getImages() != null && !data.getImages().isEmpty()) {
            mIvImg.setImageURI(data.getImages().get(0));
        } else if (data.getImage() != null) {
            mIvImg.setImageURI(data.getImage());
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_daily_news;
    }

    @Override
    public void onClick(View v) {
        ZhiHuEssayActivity.start(mContext, mData.getId(), mData.date);
    }
}
