package com.lk.satellitemenu.View;

/**
 * Created by Administrator on 2016/6/17.
 */

public class CopySatelliteMenuItem {
    private int id;
    private int imageResId;

    public CopySatelliteMenuItem(int id, int imageResId) {
        this.id = id;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
