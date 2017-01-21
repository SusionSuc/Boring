package com.susion.boring.base;

import com.susion.boring.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/18.
 */
public class DrawerData{

    public static List<Object> sData;

    private DrawerData(){
    }

    public static List<Object> getData(){
        if (sData == null) {
            sData = new ArrayList<>();

            sData.add(new DrawerHeader("susion"));

            sData.add(new DrawerItem("关于作者", R.mipmap.drawer_author));
            sData.add(new DrawerItem("我的收藏", R.mipmap.drawer_collect));
            sData.add(new DrawerItem("应用信息", R.mipmap.drawer_app_info));
            sData.add(new DrawerItem("应用换肤", R.mipmap.drawer_change_skin));


            sData.add(new DrawerItem("第三方开源库", -1));

            sData.add(new DrawerItem("Fresco", R.mipmap.drawer_open_library));
            sData.add(new DrawerItem("Retrofit", R.mipmap.drawer_open_library));
            sData.add(new DrawerItem("RxJava", R.mipmap.drawer_open_library));
            sData.add(new DrawerItem("MVP Design", R.mipmap.drawer_open_library));
            sData.add(new DrawerItem("Event Bus", R.mipmap.drawer_open_library));

        }

        return sData;
    }


    public static class DrawerHeader{
        public String icon;
        public String username;
        public String background;

        public DrawerHeader(String username) {
            this.username = username;
        }
    }

    public static class DrawerItem{
        public int imageRes;
        public String item;
        public String appendDesc;

        public DrawerItem(String item) {
            this.item = item;
        }

        public DrawerItem(String item, int imageRes) {
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
