package com.susion.boring.interesting.mvp.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.ViewPageFragment;
import com.susion.boring.http.APIHelper;
import com.susion.boring.interesting.itemhandler.SimplePictureIH;
import com.susion.boring.interesting.mvp.model.SimplePicture;
import com.susion.boring.interesting.mvp.model.SimplePictureList;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/15.
 */
public class PictureFragment extends ViewPageFragment implements OnLastItemVisibleListener, SwipeRefreshLayout.OnRefreshListener {

    private FloatingActionButton mFlMenu;
    private LoadMoreRecycleView mRv;
    private List<SimplePicture> mData = new ArrayList<>();
    private int mPage = 1;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean mRefreshing;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_picture_layout, container, false);
        return mView;
    }

    @Override
    protected void findView() {
        mFlMenu = (FloatingActionButton) mView.findViewById(R.id.float_button);
        mRv = (LoadMoreRecycleView) mView.findViewById(R.id.list_view);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_layout);
    }

    @Override
    protected void initView() {
        mRefreshLayout.setColorSchemeColors(new int[]{getResources().getColor(R.color.colorAccent)});
        mRefreshLayout.setOnRefreshListener(this);
        mRv.setLayoutManager(RVUtils.getStaggeredGridLayoutManager(2));
        mRv.setOnLastItemVisibleListener(this);
        mRv.setAdapter(new BaseRVAdapter(this, mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new SimplePictureIH();
                    }
                });
            }

            @Override
            protected int getViewType(int position) {
                return 0;
            }
        });
    }

    @Override
    public void initListener() {
        mFlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("是时候展现真正的技术了");
            }
        });
    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        APIHelper.getPictureService()
                .getPicturesByType("2004", String.valueOf(mPage))
                .compose(APIHelper.applyBackThreadSchedulers())
                .subscribe(new Observer<SimplePictureList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(SimplePictureList simplePictureList) {
                        if (simplePictureList != null && simplePictureList.getShowapi_res_body().getPagebean().getContentlist() != null) {
                            List<SimplePictureList.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentList = simplePictureList.getShowapi_res_body().getPagebean().getContentlist();
                            if (!mRefreshing) {
                                for (SimplePictureList.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean : contentList) {
                                    mData.addAll(bean.getList());
                                }

                                mRv.getAdapter().notifyDataSetChanged();
                                mPage++;
                            } else {
                                mRefreshing = false;
                                mRefreshLayout.setRefreshing(mRefreshing);

                                mData.clear();
                                for (SimplePictureList.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean : contentList) {
                                    mData.addAll(bean.getList());
                                }
                                mRv.getAdapter().notifyDataSetChanged();
                            }
                        } else {
                            mRefreshing = false;
                            mRefreshLayout.setRefreshing(mRefreshing);
                        }
                    }
                });
    }

    @Override
    public String getTitle() {
        return "图片精选";
    }


    @Override
    public void onLastItemVisible() {
        loadData();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        loadData();
        mRefreshing = true;
    }
}
