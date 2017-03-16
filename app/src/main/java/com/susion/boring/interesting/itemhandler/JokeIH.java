package com.susion.boring.interesting.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.interesting.mvp.model.Joke;

/**
 * Created by susion on 17/3/16.
 */
public class JokeIH extends SimpleItemHandler<Joke>{

    @Override
    public void onBindDataView(ViewHolder vh, Joke data, int position) {
        vh.getTextView(R.id.item_joke_tv_content).setText(data.getContent());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_joke;
    }

    @Override
    public void onClick(View v) {

    }
}
