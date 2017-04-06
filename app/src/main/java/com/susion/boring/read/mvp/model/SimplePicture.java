package com.susion.boring.read.mvp.model;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.susion.boring.base.mvp.model.FavoriteOb;

import java.io.Serializable;

/**
 * Created by susion on 17/3/17.
 */
@Table("picture")
public class SimplePicture implements Serializable {

    @PrimaryKey(AssignType.BY_MYSELF)
    public String id;

    private String big;
    private String small;
    private String middle;

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }
}
