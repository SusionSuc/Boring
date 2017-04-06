package com.susion.boring.base.ui.mainui.drawer;

import android.content.Context;

import com.susion.boring.R;
import com.susion.boring.base.SAppApplication;
import com.susion.boring.base.ui.AppInfoActivity;
import com.susion.boring.base.ui.AuthorActivity;
import com.susion.boring.base.ui.CollectActivity;
import com.susion.boring.base.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/18.
 */
public class DrawerData {

    public static final String DRAWER_AUTHOR = "关于作者";
    public static final String DRAWER_COLLECT = "我的收藏";
    public static final String DRAWER_APP_INFO = "应用信息";
    public static final String DRAWER_SETTING = "设置";

    public static List<DividerMark> sData;

    private DrawerData() {
    }

    public static List<DividerMark> getData() {
        if (sData == null) {
            sData = new ArrayList<>();
            sData.add(new DrawerHeader(SAppApplication.getAppContext().getString(R.string.author_nickname), true));
//            sData.add(new DrawerItem(DRAWER_AUTHOR, R.mipmap.ic_drawer_author, false));
            sData.add(new DrawerItem(DRAWER_COLLECT, R.mipmap.ic_drawer_collect, false));
            sData.add(new DrawerItem(DRAWER_APP_INFO, R.mipmap.ic_drawer_app_info, false));
//            sData.add(new DrawerItem(DRAWER_SETTING, R.mipmap.ic_drawer_setting, false));
        }
        return sData;
    }

    public static void onItemClick(Context context, String type) {
        switch (type) {
            case DRAWER_AUTHOR:
                AuthorActivity.start(context);
                break;

            case DRAWER_COLLECT:
                CollectActivity.start(context);
                break;

            case DRAWER_APP_INFO:
                AppInfoActivity.start(context);
                break;

            case DRAWER_SETTING:
                SettingActivity.start(context);
                break;
        }
    }

    public static class DrawerHeader extends DividerMark {
        public String icon;
        public String username;
        public String background;

        public DrawerHeader(String username, boolean needDivider) {
            super(needDivider);
            this.username = username;
        }
    }

    public static class DrawerItem extends DividerMark {
        public int imageRes;
        public String type;
        public String appendDesc;


        public DrawerItem(String item, int imageRes, boolean needDivider) {
            super(needDivider);
            this.type = item;
            this.imageRes = imageRes;
        }
    }
}
