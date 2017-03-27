package com.susion.boring.interesting.view;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.interesting.mvp.model.PictureCategoryResult;

/**
 * Created by susion on 17/3/26.
 */
public class PictureCategoryIH extends SimpleItemHandler<PictureCategoryResult.CategoryList.PictureCategory> {
    @Override
    public void onBindDataView(ViewHolder vh, PictureCategoryResult.CategoryList.PictureCategory data, int position) {
        vh.getTextView(R.id.item_picture_category_tv_name).setText(data.getName());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_picture_category;
    }

    @Override
    public void onClick(View v) {

    }
}
