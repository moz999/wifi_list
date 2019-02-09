package com.mooz.wifi_list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mooz.wifi_list.RegisteredPoint.RegisteredFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;

    @Override
    protected void onResume(){
        super.onResume();

        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPagerViewer();
    }

    private void setPagerViewer(){
        FragmentManager manager = getSupportFragmentManager();
        ViewPager viewPager = findViewById(R.id.viewpager);
        PagerAdapter adapter = new PagerAdapter(manager);
        viewPager.setAdapter(adapter);

        //Initialize TabLayout
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);    //ViewPagerとTabLayoutを紐付ける
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.list_item:
                Toast.makeText(this, "list item clicked", Toast.LENGTH_SHORT).show();
                // wifi scan
                final WifiManager manager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                if(manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED){
                    List<ScanResult> result = manager.getScanResults();
                    String[] items = new String[result.size()];

                    for(int i = 0; i < result.size(); i++){
                        items[i] = result.get(i).SSID;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
