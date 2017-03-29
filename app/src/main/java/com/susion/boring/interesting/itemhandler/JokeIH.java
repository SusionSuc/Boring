package com.susion.boring.interesting.itemhandler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.interesting.mvp.model.Joke;
import com.susion.boring.utils.ToastUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/16.
 */
public class JokeIH extends SimpleItemHandler<Joke> {

    private ImageView mIvLike;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mIvLike = vh.getImageView(R.id.item_joke_iv_like);
        mIvLike.setOnClickListener(this);
    }

    @Override
    public void onBindDataView(ViewHolder vh, Joke data, int position) {
        vh.getTextView(R.id.item_joke_tv_content).setText(data.getContent());
        refreshLikeUI();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_joke;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_joke_iv_like) {
            mData.favorite = !mData.favorite;
            new DbBaseOperate<Joke>(DbManager.getLiteOrm(), mContext, Joke.class)
                    .add(mData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        private Boolean success;

                        @Override
                        public void onCompleted() {
                            if (!success) {
                                mData.favorite = !mData.favorite;
                                ToastUtils.showShort("喜欢失败");
                            } else {
                                if (mData.favorite) {
                                    ToastUtils.showShort("已喜欢");
                                } else {
                                    ToastUtils.showShort("已从喜欢列表删除");
                                }
                            }
                            refreshLikeUI();
                        }

                        @Override
                        public void onError(Throwable e) {
                            success = false;
                        }

                        @Override
                        public void onNext(Boolean success) {
                            this.success = success;
                        }
                    });
        }
    }

    private void refreshLikeUI() {
        if (mData.favorite) {
            mIvLike.setImageResource(R.mipmap.ic_love);
        } else {
            mIvLike.setImageResource(R.mipmap.ic_un_love);
        }
    }
}
