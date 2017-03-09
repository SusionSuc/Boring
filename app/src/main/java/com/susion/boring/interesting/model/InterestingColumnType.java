package com.susion.boring.interesting.model;

import com.susion.boring.R;
import com.susion.boring.SAppApplication;
import com.susion.boring.http.BaseURL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public class InterestingColumnType {

    private  static List<InterestingColumn> mColumns;

    public static List<InterestingColumn> getColumnS(){
        if (mColumns == null) {
            String imgURL;
            mColumns = new ArrayList<>();
            imgURL = String.format("res://%s/%s", SAppApplication.getAppContext().getPackageName(), R.mipmap.zhihu);
            mColumns.add(new InterestingColumn("知乎日报", imgURL, "2.6万浏览", "每天三次, 每次七分钟"));
            imgURL = String.format("res://%s/%s", SAppApplication.getAppContext().getPackageName(), R.mipmap.laugh);
            mColumns.add(new InterestingColumn("笑话大全", imgURL, "7.6万浏览", "不知道你笑点有多高, 反正我笑点很低!"));
            imgURL = String.format("res://%s/%s", SAppApplication.getAppContext().getPackageName(), R.mipmap.fight_picture);
            mColumns.add(new InterestingColumn("搞笑趣图", imgURL, "8.8万浏览", "这里我就不放图了,自己去看看吧"));
        }
        return mColumns;
    }


}
