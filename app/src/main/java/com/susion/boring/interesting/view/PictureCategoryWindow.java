package com.susion.boring.interesting.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.http.APIHelper;
import com.susion.boring.interesting.mvp.model.PictureCategoryResult;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/26.
 */
public class PictureCategoryWindow {

    private Context mContext;
    FixedPopupWindow mWindow;
    private RecyclerView mRv;
    private List<PictureCategoryResult.CategoryList.PictureCategory> mData;

    public PictureCategoryWindow(Context context) {
        mContext = context;
        mWindow = new FixedPopupWindow();
        init();
    }

    private void init() {
        View windowLayout = LayoutInflater.from(mContext).inflate(R.layout.view_picture_category, null);
        mWindow.setContentView(windowLayout);
        mWindow.setWidth(UIUtils.getScreenWidth() / 2);
        mWindow.setHeight(UIUtils.getScreenHeight() / 3 * 2);
        mWindow.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.white)));

        mRv = (RecyclerView) windowLayout.findViewById(R.id.list_view);
        mRv.setLayoutManager(RVUtils.getLayoutManager(mContext, LinearLayoutManager.VERTICAL));
        mRv.setAdapter(new BaseRVAdapter((Activity) mContext, mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new PictureCategoryIH();
                    }
                });
            }

            @Override
            protected int getViewType(int position) {
                return 0;
            }
        });
        Rect rect = new Rect(UIUtils.dp2Px(15), 0, UIUtils.dp2Px(15), 0);
        mRv.addItemDecoration(new RVUtils.NoLastDividerDecoration(mContext, R.color.white, 1, rect));
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        mWindow.showAsDropDown(anchor, xoff, yoff, gravity);
        loadData();
    }

    public void dismiss() {
        mWindow.dismiss();
    }

    public boolean isShowing() {
        return mWindow.isShowing();
    }

    private void loadData() {
        APIHelper.getPictureService()
                .getPictureCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PictureCategoryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PictureCategoryResult res) {
                        if (res != null && res.getShowapi_res_body().getList() != null) {
                            mData.addAll(res.getShowapi_res_body().getList());
                            mRv.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

}
