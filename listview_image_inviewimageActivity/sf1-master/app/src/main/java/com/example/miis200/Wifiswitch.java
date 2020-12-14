package com.example.miis200;

import android.Manifest;
import android.util.Log;
import android.view.View;

import com.example.miis200.base.BaseActivity;
import com.example.miis200.base.OnPermissionCallbackListener;
import com.example.miis200.utils.WifiUtil;

import java.util.List;

/**
 * 切换到指定wifi
 */
public class Wifiswitch extends BaseActivity {



    public Wifiswitch(){
        Log.i("wifi","WWWWW");
        initView();
    };
    private void initPermission() {
        onRequestPermission(new String[]{
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,

        }, new OnPermissionCallbackListener() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
            }
        });
    }

    @Override
    public void initView() {
        initPermission();

        findViewById(R.id.AIButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wifi","WWWWWWWWWWWWWWWWWWW");
                changeWifi2();
            }
        });
    }


    private String aimWifiName = "DIB-00044bc5807f";
    private String aimWifiPwd = "Aa123456";

    private void changeWifi2() {
            WifiUtil.getIns().init(getApplicationContext());
            WifiUtil.getIns().changeToWifi(aimWifiName, aimWifiPwd);

    }

    @Override
    public void installData() {

    }
}
