package com.lk.satellitemenu.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.lk.satellitemenu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */

public class CopySatelliteMenu extends FrameLayout {

    public static final String TAG = "CopyStatelliteMenu";

    private int distance    = 200;
    private int baseDegress = 270;
    private int totalDegree = 90;
    private int totalMenuSize;

    private boolean isOpen = false;

    private ImageView mainImage;

    private List<CopySatelliteMenuItem> mMenuItems = new ArrayList<>();

    private MainImageClickListener mMainImageClickListener;

    private OnSatelliteMenuItemClickListener mMenuItemClickListener;

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
        mainImage = (ImageView) findViewById(R.id.sat_main);

        mainImage.setOnClickListener(mMainImageClickListener);

        this.removeView(mainImage);
        this.addView(mainImage);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LayoutParams params = (LayoutParams) mainImage.getLayoutParams();
        params.bottomMargin = (int) (distance * Math.sin(Math.toRadians(baseDegress + totalDegree)));
        mainImage.setLayoutParams(params);

        int totalHeight = mainImage.getHeight() + distance + caculateMeasureDiff() + (int) (distance * Math.sin(Math.toRadians(baseDegress + totalDegree)));
        int totalWidth = mainImage.getWidth() + distance + caculateMeasureDiff();

        setMeasuredDimension(totalWidth, totalHeight);
    }

    /**
     * 宽高需要有一定的空余，因为动画有一个插值器，在达到最大距离的时候可能会显示不全
     * @return
     */
    private int caculateMeasureDiff() {
        int diff = 0;
        diff = (int) (distance * 0.2);
        return diff;
    }

    private Animation createInAnim(int x, int xDelt, int y, int yDelt) {
        TranslateAnimation translateAnimation = new TranslateAnimation(x, xDelt, y, yDelt);
        translateAnimation.setStartOffset(0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(getContext(), R.anim.sat_item_overshoot_interpolator);
        return translateAnimation;
    }

    private Animation createOutAnim(int x, int xDelt, int y, int yDelt) {
        TranslateAnimation translateAnimation = new TranslateAnimation(x, xDelt, y, yDelt);
        translateAnimation.setStartOffset(0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(getContext(), R.anim.sat_item_anticipate_interpolator);
        return translateAnimation;
    }

    public void addMenus(List<CopySatelliteMenuItem> menus) {
        this.mMenuItems.addAll(menus);
        this.totalMenuSize = menus.size();

        int index = 0;
        for (final CopySatelliteMenuItem menuItem : mMenuItems) {

            float degree = caculateDegrees(totalMenuSize)[index];

            int finalX = (int) (distance * Math.cos(Math.toRadians(degree)));
            int finalY = (int) (distance * Math.sin(Math.toRadians(degree)));

            int bottomDiff = (int) (distance * Math.sin(Math.toRadians(baseDegress + totalDegree)));

            final ImageView imageView1 = (ImageView) LayoutInflater.from(getContext())
                    .inflate(R.layout.sat_item_cr, this, false);
            final ImageView cloneView1 = (ImageView) LayoutInflater.from(getContext())
                    .inflate(R.layout.sat_item_cr, this, false);
            imageView1.setImageResource(menuItem.getImageResId());
            imageView1.setTag(menuItem.getId());
            cloneView1.setTag(menuItem.getId());
            LayoutParams params = (LayoutParams) cloneView1.getLayoutParams();
            if (degree > baseDegress + 90) {
                params.bottomMargin = -Math.abs(finalY) + bottomDiff;
            } else {
                params.bottomMargin = Math.abs(finalY) + bottomDiff;
            }
            params.leftMargin = Math.abs(finalX);
            cloneView1.setLayoutParams(params);
            cloneView1.setImageResource(menuItem.getImageResId());
            cloneView1.setOnClickListener(new ItemImageClickListener(menuItem.getId()));

            Animation inAnim = createInAnim(
                    0,
                    finalX,
                    totalDegree > 90 ? -bottomDiff : 0,
                    totalDegree > 90 ? finalY + (-bottomDiff) : finalY);
            Animation outAnim = createOutAnim(
                    finalX,
                    0,
                    totalDegree > 90 ? finalY + (-bottomDiff) : finalY,
                    totalDegree > 90 ? -bottomDiff : 0);

            menuItem.setImageView(imageView1);
            menuItem.setCloneImage(cloneView1);
            menuItem.setInAnimation(inAnim);
            menuItem.setOutAnimation(outAnim);
            menuItem.setFinalX(finalX);
            menuItem.setFinalY(finalY);

            inAnim.setAnimationListener(new InAnimListener(menuItem));
            outAnim.setAnimationListener(new OutAnimListener(menuItem));

            this.addView(imageView1);
            this.addView(cloneView1);

            index++;
        }
    }

    /**
     * 打开菜单
     */
    private void openMenu() {
        for (CopySatelliteMenuItem mMenuItem : mMenuItems) {
            mMenuItem.getImageView().setVisibility(VISIBLE);
            mMenuItem.getImageView().startAnimation(mMenuItem.getInAnimation());
        }
        isOpen = !isOpen;
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        for (CopySatelliteMenuItem mMenuItem : mMenuItems) {
            mMenuItem.getImageView().setVisibility(VISIBLE);
            mMenuItem.getImageView().startAnimation(mMenuItem.getOutAnimation());
        }
        isOpen = !isOpen;
    }

    /**
     * 主菜单点击监听
     */
    private class MainImageClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (!isOpen) {
                openMenu();
            } else {
                closeMenu();
            }
        }
    }

    /**
     * 菜单点击监听
     */
    private class ItemImageClickListener implements OnClickListener {

        private int id;

        public ItemImageClickListener(int id) {
            this.id = id;
        }

        @Override
        public void onClick(View view) {
            if (mMenuItemClickListener != null) {
                mMenuItemClickListener.onSatelliteItemClick(id, view);
            }
            closeMenu();
        }
    }

    /**
     * item出现动画监听
     */
    private class InAnimListener implements Animation.AnimationListener {

        private CopySatelliteMenuItem menuItem;

        public InAnimListener(CopySatelliteMenuItem menuItem) {
            this.menuItem = menuItem;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            menuItem.getImageView().setVisibility(GONE);
            menuItem.getCloneImage().setVisibility(VISIBLE);
            menuItem.getImageView().clearAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    /**
     * item结束动画监听
     */
    private class OutAnimListener implements Animation.AnimationListener {

        private CopySatelliteMenuItem menuItem;

        public OutAnimListener(CopySatelliteMenuItem menuItem) {
            this.menuItem = menuItem;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            menuItem.getImageView().setVisibility(VISIBLE);
            menuItem.getCloneImage().setVisibility(GONE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            menuItem.getImageView().setVisibility(GONE);
            menuItem.getCloneImage().setVisibility(GONE);
            menuItem.getImageView().clearAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    /**
     * 计算每个item的角度
     *
     * @param count item总数
     * @return 每个item的角度
     */
    private float[] caculateDegrees(int count) {
        float[] result = new float[count];

        //小于4个的时候每个item显示在中间；大于4个的时候第一个和最后一个显示在边缘，其他的显示在中间
        if (count < 4) {
            for (int i = 0; i < count; i++) {
                result[i] = baseDegress + (float) totalDegree / (count + 1) * (i + 1);
            }
        } else {
            for (int i = 0; i < count; i++) {
                if (i == 0) {   //第一个
                    result[i] = baseDegress;
                } else if (i == count - 1) {    //最后一个
                    result[i] = baseDegress + totalDegree;
                } else {    //中间的
                    result[i] = baseDegress + (float) totalDegree / (count - 1) * (i);
                }
            }
        }

        return result;
    }

    public void setOnMenuItemClickListener(OnSatelliteMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public interface OnSatelliteMenuItemClickListener {
        void onSatelliteItemClick(int id, View itemView);
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setTotalDegree(int totalDegree) {
        this.totalDegree = totalDegree;
    }
}
