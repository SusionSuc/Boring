package com.susion.boring.base.ui.mainui.drawer;

import com.susion.boring.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/18.
 */
public class DrawerData{

    public static List<DividerMark> sData;

    private DrawerData(){
    }

    public static List<DividerMark> getData(){
        if (sData == null) {
            sData = new ArrayList<>();

            sData.add(new DrawerHeader("susion", true));

            sData.add(new DrawerItem("关于作者", R.mipmap.drawer_author, false));
            sData.add(new DrawerItem("我的收藏", R.mipmap.drawer_collect,false));
            sData.add(new DrawerItem("应用信息", R.mipmap.drawer_app_info, true));

            sData.add(new DrawerItem("设置", R.mipmap.drawer_setting, false));

        }

        return sData;
    }


    public static class DrawerHeader extends DividerMark{
        public String icon;
        public String username;
        public String background;

        public DrawerHeader(String username, boolean needDivider) {
            super(needDivider);
            this.username = username;
        }
    }

    public static class DrawerItem extends DividerMark{
        public int imageRes;
        public String item;
        public String appendDesc;


        public DrawerItem(String item, int imageRes, boolean needDivider) {
            super(needDivider);
            this.item = item;
            this.imageRes = imageRes;
        }



    }

    public static class DrawerTitle{
        public String title;

        public DrawerTitle(String title) {
            this.title = title;
        }
    }

}
