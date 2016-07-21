package com.lk.satellitemenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lk.satellitemenu.View.CopySatelliteMenu;
import com.lk.satellitemenu.View.CopySatelliteMenuItem;
import com.lk.satellitemenu.View.SatelliteMenu;
import com.lk.satellitemenu.View.SatelliteMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SatelliteMenu mSatelliteMenu;
    private CopySatelliteMenu mCopySatelliteMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSatelliteMenu();
        initCopySatellite();
    }

    private void initSatelliteMenu() {
        mSatelliteMenu = (SatelliteMenu) findViewById(R.id.satellite_menu);

        List<SatelliteMenuItem> items = new ArrayList<>();
        items.add(new SatelliteMenuItem(0, R.mipmap.sat_item));
        items.add(new SatelliteMenuItem(1, R.mipmap.sat_item));
        items.add(new SatelliteMenuItem(2, R.mipmap.sat_item));
        items.add(new SatelliteMenuItem(3, R.mipmap.sat_item));
        items.add(new SatelliteMenuItem(4, R.mipmap.sat_item));
        items.add(new SatelliteMenuItem(4, R.mipmap.sat_item));
        items.add(new SatelliteMenuItem(4, R.mipmap.sat_item));
        items.add(new SatelliteMenuItem(4, R.mipmap.sat_item));
        mSatelliteMenu.setTotalSpacingDegree(120);
        mSatelliteMenu.addItems(items);
    }

    private void initCopySatellite() {
        mCopySatelliteMenu = (CopySatelliteMenu) findViewById(R.id.copySatellite);

        List<CopySatelliteMenuItem> imageViews = new ArrayList<>();
        imageViews.add(new CopySatelliteMenuItem(0, R.mipmap.sat_item));
        imageViews.add(new CopySatelliteMenuItem(1, R.mipmap.sat_item));
        imageViews.add(new CopySatelliteMenuItem(2, R.mipmap.sat_item));
        imageViews.add(new CopySatelliteMenuItem(3, R.mipmap.sat_item));
//        imageViews.add(new CopySatelliteMenuItem(4, R.mipmap.sat_item));
//        imageViews.add(new CopySatelliteMenuItem(5, R.mipmap.sat_item));

        mCopySatelliteMenu.setDistance(180);
        mCopySatelliteMenu.setTotalDegree(90);
        mCopySatelliteMenu.addMenus(imageViews);
        mCopySatelliteMenu.setOnMenuItemClickListener(new CopySatelliteMenu.OnSatelliteMenuItemClickListener() {
            @Override
            public void onSatelliteItemClick(int id, View itemView) {
                Toast.makeText(MainActivity.this, "item" + id, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
