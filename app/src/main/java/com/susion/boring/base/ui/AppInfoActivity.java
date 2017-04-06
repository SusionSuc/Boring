package com.susion.boring.base.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.view.SToolBar;
import com.susion.boring.http.BaseURL;
import com.susion.boring.utils.SystemOperationUtils;

public class AppInfoActivity extends BaseActivity {

    private TextView mTvVersion;
    private AppBarLayout mAppBarLayout;
    private int mLogMarginTop;
    private int mLogCurrentMarginTop;
    private ImageView mIvLogo;
    private TextView mTvWeiBo;
    private TextView mTvGitHub;
    private WebView mWv;
    private ViewGroup mWvParent;

    private boolean isGitPage;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AppInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info;
    }

    @Override
    public void findView() {
        mTvVersion = (TextView) findViewById(R.id.ac_app_info_tv_version);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.ac_play_list_app_bar_layout);
        mIvLogo = (ImageView) findViewById(R.id.ac_app_info_iv_logo);
        mTvWeiBo = (TextView) findViewById(R.id.ac_app_info_tv_weibo);
        mTvGitHub = (TextView) findViewById(R.id.ac_app_info_tv_github);
        mWvParent = (ViewGroup) findViewById(R.id.ac_app_info_wv_parent);
    }

    @Override
    public void initView() {
        mToolBar.setTitle("关于随心");
        mToolBar.setContext(this);
        mTvVersion.setText("版本 v" + SystemOperationUtils.getAppVersion(this));

        mLogMarginTop = (int) (getResources().getDimension(R.dimen.app_info_log_margin_top));
        mLogCurrentMarginTop = mLogMarginTop;

        isGitPage = true;
        addWebView();
    }

    private void addWebView() {

        if (mWvParent.getChildCount() > 0) {
            mWvParent.removeAllViews();
        }

        mWv = new WebView(this);
        SystemOperationUtils.setWebViewSetting(mWv);
        if (isGitPage) {
            mWv.loadUrl(BaseURL.BORING_GIT);
        } else {
            mWv.loadUrl(BaseURL.WEIBO);
        }
        mWvParent.addView(mWv);
    }

    @Override
    public void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int mMaxScrollSize = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mMaxScrollSize < 0) {
                    mMaxScrollSize = (int) (appBarLayout.getTotalScrollRange() - getResources().getDimension(R.dimen.tool_bar_height));
                }

                verticalOffset += getResources().getDimension(R.dimen.tool_bar_height);
                float percent = verticalOffset * 1.0f / mMaxScrollSize;
                int offset = (int) (percent * mLogMarginTop);
                mLogCurrentMarginTop = mLogMarginTop + offset;

                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mIvLogo.getLayoutParams();
                marginParams.topMargin = mLogCurrentMarginTop;
                mIvLogo.setLayoutParams(marginParams);

                mIvLogo.setScaleX(1 - Math.abs(percent));
                mIvLogo.setScaleY(1 - Math.abs(percent));
            }
        });

        mTvGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGitPage) {
                    return;
                }

                isGitPage = true;
                addWebView();
            }
        });

        mTvWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGitPage) {
                    return;
                }

                isGitPage = false;
                addWebView();
            }
        });
    }

    @Override
    public void initData() {

    }

}
