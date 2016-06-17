package com.lk.satellitemenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
        mSatelliteMenu.addItems(items);
    }

    private void initCopySatellite() {
        mCopySatelliteMenu = (CopySatelliteMenu) findViewById(R.id.copySatellite);

        List<CopySatelliteMenuItem> imageViews = new ArrayList<>();
        CopySatelliteMenuItem item1 = new CopySatelliteMenuItem(0, R.mipmap.ic_launcher);
        CopySatelliteMenuItem item2 = new CopySatelliteMenuItem(0, R.mipmap.ic_launcher);
        imageViews.add(item1);
        imageViews.add(item2);
        mCopySatelliteMenu.addMenus(imageViews);
    }

}
