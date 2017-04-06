package com.susion.boring.read.itemhandler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.read.mvp.entity.PictureCategoryResult;
import com.susion.boring.utils.UIUtils;

/**
 * Created by susion on 17/3/27.
 */
public class PictureCategoryIH extends SimpleItemHandler<PictureCategoryResult.CategoryList.PictureCategory> {

    private TextView mTvTitle;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mTvTitle = vh.getTextView(R.id.item_picture_category_tv_name);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mTvTitle.getLayoutParams();
        layoutParams.leftMargin = UIUtils.dp2Px(10);
    }

    @Override
    public void onBindDataView(ViewHolder vh, PictureCategoryResult.CategoryList.PictureCategory data, int position) {
        mTvTitle.setText(data.getName());
        mTvTitle.setTextColor(UIUtils.getColor(mContext, R.color.colorPrimary));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_picture_category;
    }

    @Override
    public void onClick(View v) {
    }
}
