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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Modesetting extends AppCompatActivity {

    WifiManager wifiManager;
    List scannedResult;

    RadioGroup radioGroup;
    RadioGroup DSCradioGroup;

    TextView textCheckedID, textCheckedIndex;
    TextView DSCtextCheckedID, DSCtextCheckedIndex;

    int checkedIndex;
    int DSCcheckedIndex;

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";

    private TextView DECssid;
    private TextView DECssidpwd;
    private TextView DIBssid;
    private TextView DIBssidpwd;
    private TextView Printerssid;
    private TextView Printerssidpwd;
    private TextView Hubssid;
    private TextView Hubssidpwd;


    private EditText edDECssid;
    private EditText edDECssidpwd;
    private EditText edDIBssid;
    private EditText edDIBssidpwd;
    private EditText edPrinterssid;
    private EditText edPrinterssidpwd;
    private EditText edHubssid;
    private EditText edHubssidpwd;

    private Button applyTextButton;
    private Button saveButton;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String DECSSID = "DECSSID";
    public static final String DECSSIDPWD = "DECSSIDPWD";
    public static final String DIBSSID = "DIBSSID";
    public static final String DIBSSIDPWD = "DIBSSIDPWD";
    public static final String PRINTERSSID = "PRINTERSSID";
    public static final String PRINTERSSIDPWD = "PRINTERSSIDPWD";
    public static final String HUBSSID = "HUBSSID";
    public static final String HUBSSIDPWD = "HUBSSIDPWD";

    public static final String MODENUMBER = "0";


    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String text6;
    private String text7;
    private String text8;

    private TextView apmode,mobilehotspot,autoswitch;

    private boolean switchOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Modesetting_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting);

        radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);

        DSCradioGroup = (RadioGroup)findViewById(R.id.DSCradiogroup);
        DSCradioGroup.setOnCheckedChangeListener(DSCradioGroupOnCheckedChangeListener);



        textCheckedID = (TextView)findViewById(R.id.checkedid);
        textCheckedIndex = (TextView)findViewById(R.id.checkedindex);

        DSCtextCheckedID = (TextView)findViewById(R.id.DSCcheckedid);
        DSCtextCheckedIndex = (TextView)findViewById(R.id.DSCcheckedindex);

        LoadPreferences();
        DSCLoadPreferences();


        apmode = findViewById(R.id.apmode);
        apmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    startActivity(new Intent(Modesetting.this, Modesetting_apmode.class));
                }

            }
        });

        mobilehotspot = findViewById(R.id.mobilehotspot);
        mobilehotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    startActivity(new Intent(Modesetting.this, Modesetting_mobilehotspot.class));
                }

            }
        });


        autoswitch = findViewById(R.id.autoswitch);
        autoswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    startActivity(new Intent(Modesetting.this, Modesetting_autoswitch.class));
                }
            }
        });



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

    }

    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(checkedId);
                    checkedIndex = radioGroup.indexOfChild(checkedRadioButton);

                    textCheckedID.setText("checkedID = " + checkedId);
                    textCheckedIndex.setText("checkedIndex = " + checkedIndex);
                    SavePreferences(KEY_SAVED_RADIO_BUTTON_INDEX, checkedIndex);
                }};

    private void SavePreferences(String key, int value){
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        RadioButton savedCheckedRadioButton = (RadioButton)radioGroup.getChildAt(savedRadioIndex);
        savedCheckedRadioButton.setChecked(true);
    }


    //===================================================================================================================


    RadioGroup.OnCheckedChangeListener DSCradioGroupOnCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    RadioButton checkedRadioButton = (RadioButton)DSCradioGroup.findViewById(checkedId);
                    DSCcheckedIndex = DSCradioGroup.indexOfChild(checkedRadioButton);

                    DSCtextCheckedID.setText("checkedID = " + checkedId);
                    DSCtextCheckedIndex.setText("checkedIndex = " + DSCcheckedIndex);
                    DSCSavePreferences(KEY_SAVED_RADIO_BUTTON_INDEX, DSCcheckedIndex);
                }};

    private void DSCSavePreferences(String key, int value){
        SharedPreferences sharedPreferences = getSharedPreferences("DSC_MODE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    private void DSCLoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("DSC_MODE", MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        RadioButton savedCheckedRadioButton = (RadioButton)DSCradioGroup.getChildAt(savedRadioIndex);
        savedCheckedRadioButton.setChecked(true);
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