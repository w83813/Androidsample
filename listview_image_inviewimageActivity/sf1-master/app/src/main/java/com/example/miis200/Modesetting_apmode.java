package com.example.miis200;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

public class Modesetting_apmode extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {


    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    WifiManager wifiManager;
    List scannedResult;
    List NEWscannedResult;

    String[] SSIDlist = { "Suresh Dasari", "Trishika Dasari", "Rohini Alavala", "Praveen Kumar", "Madhav Sai" };
    String[] SSIDarrayTOstring;

    private Spinner spHUBSSID;
    private EditText edHUBSSIDPWD;


    private EditText edAP300IP;
    private EditText edAPDIBIP;


    private Button saveButton,cancelButton;

    private TextView AP_mode_input200or300IP,AP_mode_200or300linkAP;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String AP300IP = "AP300IP";
    public static final String APDIBIP = "APDIBIP";
    public static final String APHUBSSIDPWD = "APHUBSSIDPWD";

    SharedPreferences DSCsharedPreferences;
    int DSCorDEC;
    int unknowSSID_Item;





    private String text1;
    private String text2;
    private String text3;
    private String text4;

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



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("AP mode 設定");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modesetting_apmode);


        spHUBSSID = (Spinner) findViewById(R.id.APHUBSSID);
        edHUBSSIDPWD = (EditText) findViewById(R.id.APHUBSSIDPWD);
        edAP300IP = (EditText) findViewById(R.id.AP300IP);
        edAPDIBIP = (EditText) findViewById(R.id.APDIBIP);


        DSCsharedPreferences = getSharedPreferences("DSC_MODE", MODE_PRIVATE);
        DSCorDEC = DSCsharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        AP_mode_input200or300IP = (TextView) findViewById(R.id.AP_mode_input200or300IP);
        AP_mode_200or300linkAP = (TextView) findViewById(R.id.AP_mode_200or300linkAP);
        if (DSCorDEC == 0) {
            AP_mode_200or300linkAP.setText(R.string.Modesetting_3_DSC_link_AP);
            AP_mode_input200or300IP.setText(R.string.Modesetting_6_input_DSC_IP);
        } else {
            AP_mode_200or300linkAP.setText(R.string.Modesetting_3_DEC_link_AP);
            AP_mode_input200or300IP.setText(R.string.Modesetting_6_input_DEC_IP);
        }

        saveButton = (Button) findViewById(R.id.APsave);
        cancelButton = (Button) findViewById(R.id.cancel);

        scannedResult = new ArrayList();
        NEWscannedResult = new ArrayList();

        if (mWifiManager == null) {
            mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        }
        getPerMission();//权限
        initView();//控件初始化

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();

        wifiListBeanList.clear();

        //开启wifi
        MyWifiManager.openWifi(mWifiManager);
        //获取到wifi列表
        mScanResultList = MyWifiManager.getWifiList(mWifiManager);

        for (int i = 0; i < mScanResultList.size(); i++) {
            scannedResult.add( mScanResultList.get(i).SSID);
            unknowSSID_Item++;
        }
        Log.i("<unknowSSID_Item",String.valueOf(unknowSSID_Item));
        scannedResult.add("< unknow SSID >");
        NEWscannedResult = (List) scannedResult.stream().distinct().collect(Collectors.toList());
        SSIDarrayTOstring = (String[]) NEWscannedResult.toArray(new String[NEWscannedResult.size()]);


        try {
            Thread.sleep(500);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.myspinner, SSIDlist);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.myspinner, SSIDarrayTOstring);
            adapter.setDropDownViewResource(R.layout.myspinner1);
            spHUBSSID.setAdapter(adapter);
            spHUBSSID.setOnItemSelectedListener(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Button_Click.isFastDoubleClick()) {
                    edAP300IP.setText(edAP300IP.getText().toString());
                    edAPDIBIP.setText(edAPDIBIP.getText().toString());
                    edHUBSSIDPWD.setText(edHUBSSIDPWD.getText().toString());
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
        mPermissionsChecker = new PermissionsChecker(Modesetting_apmode.this);
        if (mPermissionsChecker.lacksPermissions(permsLocation)) {
            ActivityCompat.requestPermissions(Modesetting_apmode.this, permsLocation, RESULT_CODE_LOCATION);
        }
    }

    private void initView() {
        wifiListBeanList = new ArrayList<>();
        mScanResultList = new ArrayList<>();
    }



    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(AP300IP, edAP300IP.getText().toString());
        editor.putString(APDIBIP, edAPDIBIP.getText().toString());
        editor.putString(APHUBSSIDPWD, edHUBSSIDPWD.getText().toString());

        int APHUBssid = spHUBSSID.getSelectedItemPosition();
        editor.putInt("spHUBSSID",APHUBssid);
        editor.putString("APHUBSSID", SSIDarrayTOstring[APHUBssid]);


        editor.apply();

        Toast.makeText(this, R.string.Modesetting_setting_succ, Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text1 = sharedPreferences.getString(AP300IP, "192.168.0.100");
        text2 = sharedPreferences.getString(APDIBIP, "192.168.0.123");
        text3 = sharedPreferences.getString(APHUBSSIDPWD, "APHUBSSIDPWD");



        for (int i=0;i<spHUBSSID.getCount();i++){
            if (spHUBSSID.getItemAtPosition(i).equals(sharedPreferences.getString("APHUBSSID","test"))){
                spHUBSSID.setSelection(i);
                break;
            } else {
                spHUBSSID.setSelection(i);
            }
        }


    }

    public void updateViews() {
        edAP300IP.setText(text1);
        edAPDIBIP.setText(text2);
        edHUBSSIDPWD.setText(text3);


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

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(), "Selected User: "+SSIDlist[position] ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }
}