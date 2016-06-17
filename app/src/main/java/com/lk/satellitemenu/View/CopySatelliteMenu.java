package com.lk.satellitemenu.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lk.satellitemenu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */

public class CopySatelliteMenu extends FrameLayout {

    public static final String TAG = "CopyStatelliteMenu";

    private int distance = 200;

    private int totalMenuSize;

    private TranslateAnimation translateAnimation;

    private MainImageClickListener mMainImageClickListener;

    private List<CopySatelliteMenuItem> mMenuItems = new ArrayList<>();

    private HashMap<ImageView, TranslateAnimation> mViewTranslateAnimation = new HashMap<>();


    public CopySatelliteMenu(Context context) {
        super(context);
        init();
    }

    public CopySatelliteMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CopySatelliteMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mMainImageClickListener = new MainImageClickListener();

        LayoutInflater.from(getContext()).inflate(R.layout.sat_main, this, true);
        ImageView mainImage = (ImageView) findViewById(R.id.sat_main);

        mainImage.setOnClickListener(mMainImageClickListener);

//        ImageView imageItem1 = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.sat_item_cr, this, false);
//        mMenuItems.add(imageItem1);
//        imageItem1.setImageResource(R.mipmap.sat_item);
//        imageItem1.setVisibility(VISIBLE);

        this.removeView(mainImage);
        this.addView(mainImage);
//        this.addView(imageItem1);

    }

    private void initAnim(int count) {
        translateAnimation = new TranslateAnimation(0, (float)(distance * Math.cos(Math.toRadians(caculateDegree(count)))), 0, (float)(distance * Math.sin(Math.toRadians(caculateDegree(count)))));
        translateAnimation.setStartOffset(0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(getContext(), R.anim.sat_item_overshoot_interpolator);
    }

    public void addMenus(List<CopySatelliteMenuItem> menus) {
        this.mMenuItems.addAll(menus);
        this.totalMenuSize = menus.size();

        int index = 0;
        for (CopySatelliteMenuItem menuItem : mMenuItems) {
            ImageView imageView1 = (ImageView) LayoutInflater.from(getContext())
                    .inflate(R.layout.sat_item_cr, this, false);
            imageView1.setVisibility(VISIBLE);
            imageView1.setTag(menuItem.getId());
            imageView1.setImageResource(menuItem.getImageResId());
            initAnim(index + 1);
            mViewTranslateAnimation.put(imageView1, translateAnimation);
            this.addView(imageView1);

            index++;
        }
    }

    private class MainImageClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            for (ImageView imageView : mViewTranslateAnimation.keySet()) {
                imageView.startAnimation(mViewTranslateAnimation.get(imageView));
            }
        }
    }

    private int caculateDegree(int count) {
        int result;
        if (totalMenuSize == 1) {
            return 45;
        }
        result = 270 + 90 / (totalMenuSize + 1) * count;
        return result;
    }

}
