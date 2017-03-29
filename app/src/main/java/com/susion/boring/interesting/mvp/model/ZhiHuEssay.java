package com.susion.boring.interesting.mvp.model;

import com.susion.boring.base.mvp.model.FavoriteOb;

import java.util.Date;
import java.util.List;

/**
 * Created by susion on 17/3/28.
 */
public class ZhiHuEssay extends FavoriteOb {
    public String id;
    public int type;
    public String ga_prefix;
    public String title;
    public List<String> images;
    public Date date;
}
