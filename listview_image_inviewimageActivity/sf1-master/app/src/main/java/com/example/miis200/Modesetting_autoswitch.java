package com.example.miis200;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miis200.bean.WifiListBean;
import com.example.miis200.utils.MyWifiManager;
import com.example.miis200.utils.PermissionsChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Modesetting_autoswitch extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";

    WifiManager wifiManager;

    List<String> scannedResult;
    List<String> NEWscannedResult;

    String[] SSIDarrayTOstring;
    private Spinner spAS300SSID;
    private Spinner spASDIBSSID;
    private Spinner spASPRINTERSSID;
    private Spinner spASHUBSSID;

    private EditText edAS300SSIDPWD;
    private EditText edASDIBSSIDPWD;
    private EditText edASPRINTERSSIDPWD;
    private EditText edASHUBSSIDPWD;


    private Button saveButton,cancelButton;
    private TextView AS_mode_200or300SSID;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String AS300SSID = "AS300SSID";
    public static final String AS300SSIDPWD = "AS300SSIDPWD";
    public static final String ASDIBSSID = "ASDIBSSID";
    public static final String ASDIBSSIDPWD = "ASDIBSSIDPWD";
    public static final String ASPRINTERSSID = "ASPRINTERSSID";
    public static final String ASPRINTERSSIDPWD = "ASPRINTERSSIDPWD";
    public static final String ASHUBSSID = "ASHUBSSID";
    public static final String ASHUBSSIDPWD = "ASHUBSSIDPWD";

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
    private String text9;


    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private final int RESULT_CODE_LOCATION = 0x001;
    //定位权限,获取app内常用权限
    String[] permsLocation = {"android.permission.ACCESS_WIFI_STATE"
            , "android.permission.CHANGE_WIFI_STATE"
            , "android.permission.ACCESS_COARSE_LOCATION"
            , "android.permission.ACCESS_FINE_LOCATION"};

    private WifiManager mWifiManager;
    private List<ScanResult> mScanResultList;//wifi列表
    private List<WifiListBean> wifiListBeanList;




    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Modesetting_autoswitch_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting_autoswitch);

        spAS300SSID = (Spinner) findViewById(R.id.AS300SSIDspineer);

        spASDIBSSID = (Spinner) findViewById(R.id.ASDIBSSIDspineer);

        spASPRINTERSSID = (Spinner) findViewById(R.id.ASPRINTERSSIDspineer);

        spASHUBSSID = (Spinner) findViewById(R.id.ASHUBSSIDspineer);

        edAS300SSIDPWD = (EditText) findViewById(R.id.AS300SSIDPWD);

        edASDIBSSIDPWD = (EditText) findViewById(R.id.ASDIBSSIDPWD);

        edASPRINTERSSIDPWD = (EditText) findViewById(R.id.ASPRINTERSSIDPWD);

        edASHUBSSIDPWD = (EditText) findViewById(R.id.ASHUBSSIDPWD);


        DSCsharedPreferences = getSharedPreferences("DSC_MODE", MODE_PRIVATE);
        DSCorDEC = DSCsharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        AS_mode_200or300SSID = (TextView) findViewById(R.id.AS_mode_200or300SSID);
        if (DSCorDEC == 0) {
            AS_mode_200or300SSID.setText(R.string.Modesetting_autoswitch_1_input_DSC_SSID_passwoord);
        } else {
            AS_mode_200or300SSID.setText(R.string.Modesetting_autoswitch_1_input_DEC_SSID_passwoord);
        }







        saveButton = (Button) findViewById(R.id.ASsave);
        cancelButton = (Button) findViewById(R.id.cancel);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String ssid = wifiInfo.getSSID();


        if (mWifiManager == null) {
            mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        }
        getPerMission();
        initView();

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();
        NEWscannedResult = new ArrayList();

        wifiListBeanList.clear();

        //开启wifi
        MyWifiManager.openWifi(mWifiManager);
        //获取到wifi列表
        mScanResultList = MyWifiManager.getWifiList(mWifiManager);

        for (int i = 0; i < mScanResultList.size(); i++) {
            scannedResult.add( mScanResultList.get(i).SSID);
        }
        scannedResult.add("< unknow SSID >");
        NEWscannedResult = (List) scannedResult.stream().distinct().collect(Collectors.toList());
        SSIDarrayTOstring = (String[]) NEWscannedResult.toArray(new String[NEWscannedResult.size()]);

        try {
            Thread.sleep(500);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.myspinner, SSIDlist);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.myspinner, SSIDarrayTOstring);
            adapter.setDropDownViewResource(R.layout.myspinner1);
            spAS300SSID.setAdapter(adapter);
            spAS300SSID.setOnItemSelectedListener(this);

            spASDIBSSID.setAdapter(adapter);
            spASDIBSSID.setOnItemSelectedListener(this);

            spASPRINTERSSID.setAdapter(adapter);
            spASPRINTERSSID.setOnItemSelectedListener(this);

            spASHUBSSID.setAdapter(adapter);
            spASHUBSSID.setOnItemSelectedListener(this);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Button_Click.isFastDoubleClick()) {
                    edAS300SSIDPWD.setText(edAS300SSIDPWD.getText().toString());
                    edASDIBSSIDPWD.setText(edASDIBSSIDPWD.getText().toString());
                    edASPRINTERSSIDPWD.setText(edASPRINTERSSIDPWD.getText().toString());
                    edASHUBSSIDPWD.setText(edASHUBSSIDPWD.getText().toString());
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

        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateViews();
    }

    //获取权限
    private void getPerMission() {
        mPermissionsChecker = new PermissionsChecker(Modesetting_autoswitch.this);
        if (mPermissionsChecker.lacksPermissions(permsLocation)) {
            ActivityCompat.requestPermissions(Modesetting_autoswitch.this, permsLocation, RESULT_CODE_LOCATION);
        }
    }

    private void initView() {
        wifiListBeanList = new ArrayList<>();
        mScanResultList = new ArrayList<>();
    }


    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(AS300SSIDPWD, edAS300SSIDPWD.getText().toString());


        editor.putString(ASDIBSSIDPWD, edASDIBSSIDPWD.getText().toString());


        editor.putString(ASPRINTERSSIDPWD, edASPRINTERSSIDPWD.getText().toString());


        editor.putString(ASHUBSSIDPWD, edASHUBSSIDPWD.getText().toString());


        int ssid300 = spAS300SSID.getSelectedItemPosition();
        editor.putInt("spAS300SSID",ssid300);
        //editor.putString("AS300SSID", SSIDlist[ssid300]);
         editor.putString("AS300SSID", SSIDarrayTOstring[ssid300]);

        int ssidDIB = spASDIBSSID.getSelectedItemPosition();
        editor.putInt("spASDIBSSID",ssidDIB);
        //editor.putString("ASDIBSSID", SSIDlist[ssidDIB]);
        editor.putString("ASDIBSSID", SSIDarrayTOstring[ssidDIB]);

        int ssidPRINTER = spASPRINTERSSID.getSelectedItemPosition();
        editor.putInt("spASPRINTERSSID",ssidPRINTER);
        //editor.putString("ASPRINTERSSID", SSIDlist[ssidPRINTER]);
         editor.putString("ASPRINTERSSID", SSIDarrayTOstring[ssidPRINTER]);

        int ssidHUB = spASHUBSSID.getSelectedItemPosition();
        editor.putInt("spASHUBSSID",ssidHUB);
        //editor.putString("ASHUBSSID", SSIDlist[ssidHUB]);
        editor.putString("ASHUBSSID", SSIDarrayTOstring[ssidHUB]);


        editor.apply();

        Toast.makeText(this, "設定成功", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        text2 = sharedPreferences.getString(AS300SSIDPWD, "300ssidpwd");


        text4 = sharedPreferences.getString(ASDIBSSIDPWD, "DIBssidpwd");


        text6 = sharedPreferences.getString(ASPRINTERSSIDPWD, "PRINTERssidpwd");


        text8 = sharedPreferences.getString(ASHUBSSIDPWD, "HUBssidpwd");

        int option300 = sharedPreferences.getInt("spAS300SSID",0);
        Log.i("itttttem",String.valueOf(option300));
        spAS300SSID.setSelection(option300);

        int optionDIB = sharedPreferences.getInt("spASDIBSSID",0);
        Log.i("itttttem",String.valueOf(optionDIB));
        spASDIBSSID.setSelection(optionDIB);

        int optionPRINTER = sharedPreferences.getInt("spASPRINTERSSID",0);
        Log.i("itttttem",String.valueOf(optionPRINTER));
        spASPRINTERSSID.setSelection(optionPRINTER);

        int optionHUB = sharedPreferences.getInt("spASHUBSSID",0);
        Log.i("itttttem",String.valueOf(optionHUB));
        spASHUBSSID.setSelection(optionHUB);


        for (int i=0;i<spAS300SSID.getCount();i++) {
            if (spAS300SSID.getItemAtPosition(i).equals(sharedPreferences.getString("AS300SSID", ""))) {
                spAS300SSID.setSelection(i);
                break;
            } else {
                spAS300SSID.setSelection(i);
            }
        }

        for (int i=0;i<spASDIBSSID.getCount();i++) {
            if (spASDIBSSID.getItemAtPosition(i).equals(sharedPreferences.getString("ASDIBSSID", ""))) {
                spASDIBSSID.setSelection(i);
                break;
            } else {
                spASDIBSSID.setSelection(i);
            }
        }

        for (int i=0;i<spASPRINTERSSID.getCount();i++) {
            if (spASPRINTERSSID.getItemAtPosition(i).equals(sharedPreferences.getString("ASPRINTERSSID", ""))) {
                spASPRINTERSSID.setSelection(i);
                break;
            } else {
                spASPRINTERSSID.setSelection(i);
            }
        }

        for (int i=0;i<spASHUBSSID.getCount();i++) {
            if (spASHUBSSID.getItemAtPosition(i).equals(sharedPreferences.getString("ASHUBSSID", ""))) {
                spASHUBSSID.setSelection(i);
                break;
            } else {
                spASHUBSSID.setSelection(i);
            }
        }


    }

    public void updateViews() {

        edAS300SSIDPWD.setText(text2);


        edASDIBSSIDPWD.setText(text4);


        edASPRINTERSSIDPWD.setText(text6);


        edASHUBSSIDPWD.setText(text8);


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
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(), "Selected User: "+SSIDlist[position] ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }
}