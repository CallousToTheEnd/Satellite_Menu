package com.lk.satellitemenu.View;

import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/17.
 */

public class CopySatelliteMenuItem {
    private int id;
    private int imageResId;
    private ImageView imageView;
    private ImageView cloneImage;
    private Animation inAnimation;
    private Animation outAnimation;
    private int finalX;
    private int finalY;

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

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getCloneImage() {
        return cloneImage;
    }

    public void setCloneImage(ImageView cloneImage) {
        this.cloneImage = cloneImage;
    }

    public Animation getInAnimation() {
        return inAnimation;
    }

    public void setInAnimation(Animation inAnimation) {
        this.inAnimation = inAnimation;
    }

    public Animation getOutAnimation() {
        return outAnimation;
    }

    public void setOutAnimation(Animation outAnimation) {
        this.outAnimation = outAnimation;
    }

    public int getFinalX() {
        return finalX;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public int getFinalY() {
        return finalY;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }
}
