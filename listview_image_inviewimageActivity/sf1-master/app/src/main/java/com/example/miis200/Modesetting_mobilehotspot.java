package com.example.miis200;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Modesetting_mobilehotspot extends AppCompatActivity {

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    WifiManager wifiManager;
    List scannedResult;


    private EditText edMH300IP;
    private EditText edMHDIBIP;

    private EditText edHubssid;
    private EditText edHubssidpwd;


    private Button saveButton,cancelButton;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MH300IP = "MH300IP";
    public static final String MHDIBIP = "MHDIBIP";

    SharedPreferences DSCsharedPreferences;
    int DSCorDEC;





    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String text6;
    private String text7;
    private String text8;

    private TextView MH_mode_input200or300IP,MH_mode_200or300IPlinkPhone;

    private boolean switchOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Modesetting_mobilehotspot_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting_mobilehotspot);






        edMH300IP = (EditText) findViewById(R.id.MH300IP);
        edMHDIBIP = (EditText) findViewById(R.id.MHDIBIP);

        saveButton = (Button) findViewById(R.id.MHsave);
        cancelButton =(Button) findViewById(R.id.cancel);

        DSCsharedPreferences = getSharedPreferences("DSC_MODE", MODE_PRIVATE);
        DSCorDEC = DSCsharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        MH_mode_input200or300IP = (TextView) findViewById(R.id.MH_mode_input200or300IP);
        if (DSCorDEC == 0) {
            MH_mode_input200or300IP.setText("5. 請輸入 DSC 300 IP");
        } else {
            MH_mode_input200or300IP.setText("5. 請輸入 DEC 200 IP");
        }

        MH_mode_200or300IPlinkPhone = (TextView) findViewById(R.id.MH_mode_200or300IPlinkPhone);
        if (DSCorDEC == 0) {
            MH_mode_200or300IPlinkPhone.setText(R.string.Modesetting_mobilehotspot_2_DSC_link_hotspot);
        } else {
            MH_mode_200or300IPlinkPhone.setText(R.string.Modesetting_mobilehotspot_2_DEC_link_hotspot);
        }



        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String ssid = wifiInfo.getSSID();
        //startRepeating(v);




        if (scannedResult.size() > 0){
            scannedResult.clear();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
        if (wifiManager.isWifiEnabled()){
            new AsyncTask<Void, String, String>(){
                @Override
                protected String doInBackground(Void... voids) {
                    List<ScanResult> scanResultList = wifiManager.getScanResults();
                    for (int i = 0; i < scanResultList.size(); i++){
                        scannedResult.add(scanResultList.get(i).SSID+"\n");
                    }
                    return scannedResult.toString();
                }

                @Override
                protected void onPostExecute(String s) {
                    Log.i("s3",s);


                }
            }.execute();
        }




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Button_Click.isFastDoubleClick()) {
                    edMH300IP.setText(edMH300IP.getText().toString());
                    edMHDIBIP.setText(edMHDIBIP.getText().toString());
                    saveData();
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadData();
        updateViews();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MH300IP, edMH300IP.getText().toString());
        editor.putString(MHDIBIP, edMHDIBIP.getText().toString());



        editor.apply();

        Toast.makeText(this, R.string.Modesetting_setting_succ, Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text1 = sharedPreferences.getString(MH300IP, "192.168.0.102");
        text2 = sharedPreferences.getString(MHDIBIP, "192.168.0.103");

    }

    public void updateViews() {
        edMH300IP.setText(text1);
        edMHDIBIP.setText(text2);


        Log.i("ZZZ",String.valueOf(text4));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.download:
                Intent intent = new Intent(getApplicationContext(), Option.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}