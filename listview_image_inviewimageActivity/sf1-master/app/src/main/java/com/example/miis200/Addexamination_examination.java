package com.example.miis200;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.example.miis200.bean.WifiListBean;
import com.example.miis200.utils.MyWifiManager;
import com.example.miis200.utils.PermissionsChecker;
import com.example.miis200.utils.WifiUtil;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Addexamination_examination extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener, BSImagePicker.ImageLoaderDelegate {

    private TextView tv_text;
    private int clo = 0;
    private int new_image_val = -1;

    ArrayList<String> mylist;
    String Imagename;
    private String ImagePath;

    private ProgressDialog DSCdialog;

    String pName,pID,pBir,pGender,pNum,pAge;

    SimpleDateFormat simpleDateFormat;
    Date date;
    String checkTime;

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    int savedRadioIndex;
    int DSCmode;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String DECSSID = "DECSSID";
    public static final String DECSSIDPWD = "DECSSIDPWD";
    public static final String DIBSSID = "DIBSSID";
    public static final String DIBSSIDPWD = "DIBSSIDPWD";
    public static final String PRINTERSSID = "PRINTERSSID";
    public static final String PRINTERSSIDPWD = "PRINTERSSIDPWD";


    public static final String AP300IP = "AP300IP";
    public static final String APDIBIP = "APDIBIP";
    public static final String APHUBSSID = "APHUBSSID";
    public static final String APHUBSSIDPWD = "APHUBSSIDPWD";

    public static final String MH300IP = "MH300IP";
    public static final String MHDIBIP = "MHDIBIP";


    public static final String AS300SSID = "AS300SSID";
    public static final String AS300SSIDPWD = "AS300SSIDPWD";
    public static final String ASDIBSSID = "ASDIBSSID";
    public static final String ASDIBSSIDPWD = "ASDIBSSIDPWD";
    public static final String ASPRINTERSSID = "ASPRINTERSSID";
    public static final String ASPRINTERSSIDPWD = "ASPRINTERSSIDPWD";
    public static final String ASHUBSSID = "ASHUBSSID";
    public static final String ASHUBSSIDPWD = "ASHUBSSIDPWD";


    private String DecSSID;
    private String DecSSIDpwd;
    private String DibSSID;
    private String DibSSIDpwd;
    private String PrinterSSID;
    private String PrinterSSIDpwd;

    private String AP_300IP;
    private String AP_DIBIP;
    private String AP_HUBSSID;
    private String AP_HUBSSIDPWD;

    private String MH_300IP;
    private String MH_DIBIP;

    private String AS_300SSID;
    private String AS_300SSIDPWD;
    private String AS_DIBSSID;
    private String AS_DIBSSIDPWD;
    private String AS_PRINTERSSID;
    private String AS_PRINTERSSIDPWD;
    private String AS_HUBSSID;
    private String AS_HUBSSIDPWD;


    private Handler mHandler = new Handler();

    private String IP300;


    WifiManager wifiManager;
    List scannedResult;


    private void aiWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AS_DIBSSID, AS_DIBSSIDPWD);
    }


    private void DEC200Wifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AS_300SSID, AS_300SSIDPWD);
    }

    private void AP_HUB() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AP_HUBSSID, AP_HUBSSIDPWD);
    }


    String eyeLp,eyeRp,eyePath1,eyePath2,eyePath3,eyePath4,eyePath5,eyePath6,eyePath7,eyePath8;



    RadioGroup radioGroup;
    ImageView Bigphoto,BigphotoLibrary;
    LinearLayout Biginputimage,inputimage;
    RecyclerView recycleView;

    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;


    TextView pname,pid,pbir,pgender,pphone,pchecktime;
    String page;

    public Context context;
    public static final String TAG = "Addexamination_examination";


    private RecyclerView recyclerView;
    private List<Addexamination_ItemRecycler> listRecycler = new ArrayList<Addexamination_ItemRecycler>(  );
    private MyAdapter myAdapter;
    int checkInterval = 3000; //set return time
    Handler updateHandler; // return time
    boolean viewingList; // return time
    int fristvalue;

    public static final SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
    Date current = new Date();
    public String today = sdf5.format(current).toString();
    public String folder0 = "/DCIM/"+sdf5.format(current).toString();
    String rootDir = "/DCIM/"+today;
    String directoryName = rootDir;
    private String mLastPictureName = null;

    //get new time
    public static final SimpleDateFormat time = new SimpleDateFormat("HHmmss");

    Button cancelPatientexam;

    String API300 ,API300_100;
    String API300_100_1;

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

    private int adapterImageName;
    private ArrayList<String> totalData;

    private Addexamination_ItemRecycler itemRecyclerSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //======================================

        loadData();
        updateViews();

        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();
        Cursor data = myDB.getListContents0();
        int numRows = data.getCount();


        //抓取手機上時間
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy年MM月dd日 ");
        Date date = new Date( System.currentTimeMillis() );
        //顯示在檢查日期上


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexamination_examination);

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Addex_exam_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        Intent intent = this.getIntent();
        pName=intent.getStringExtra("patientname");
        pID=intent.getStringExtra("patientid");
        pBir=intent.getStringExtra("patientbir");
        pGender=intent.getStringExtra("patientgender");
        pNum=intent.getStringExtra("patientphonenumber");
        pAge=intent.getStringExtra("age");


        pname = findViewById( R.id.CheckPatientName );
        pid = findViewById( R.id.CheckPatientID );
        pbir = findViewById( R.id.CheckPatientBir );
        pgender = findViewById( R.id.CheckPatientGender );
        pphone = findViewById( R.id.CheckPatientPhone );
        pchecktime = findViewById(R.id.Check_Time);

        simpleDateFormat = new SimpleDateFormat( "yyyy年MM月dd日 ");
        date = new Date( System.currentTimeMillis());
        checkTime = simpleDateFormat.format(date);

        pname.setText(pName);
        pid.setText( pID );
        pbir.setText( pBir );
        pgender.setText( pGender );
        pphone.setText( pNum );
        pchecktime.setText(checkTime);
        page = pAge;

        Button enter = (Button) findViewById(R.id.printer);
        //enter.setOnClickListener(nextenter);

        Button view = (Button) findViewById(R.id.viewimage);
        //view.setOnClickListener(nextview);

        Button saveex = (Button) findViewById(R.id.saveexamination);
        saveex.setOnClickListener(nextsaveexamination);

        cancelPatientexam = (Button) findViewById(R.id.cancelPatientexam);
        //cancelPatientexam.setOnClickListener(nextCancel);

        ArrayAdapter<CharSequence> adapterBalls = ArrayAdapter.createFromResource(
                this, R.array.Branch,android.R.layout.simple_spinner_item);
        // 設定Spinner顯示的格式
        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 設定Spinner的資料來源

        // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener



        // Start out viewing the list.    return time
        viewingList = true;


        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置
        recyclerView.setLayoutManager(manager);
        //实例化适配器
        myAdapter = new MyAdapter(listRecycler);
        //设置适配器
        recyclerView.setAdapter(myAdapter);


        Bigphoto = findViewById(R.id.Bigphoto);
        Bigphoto.setOnClickListener(nextBigphoto);
        BigphotoLibrary = findViewById(R.id.BigphotoLibrary);
        BigphotoLibrary.setOnClickListener(nextBigphotoLibrary);
        Biginputimage = findViewById(R.id.Biginputimage);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();

        if( savedRadioIndex == 0){

            API300 = "http://" + AP_300IP;
            API300_100 = "http://" + AP_300IP + "/command.cgi?op=100";
            IP300 = AP_300IP;

        }
        else if(savedRadioIndex == 1){

            API300 = "http://" + MH_300IP;
            API300_100 = "http://" + MH_300IP + "/command.cgi?op=100";
            IP300 = MH_300IP;

        }
        else if(savedRadioIndex == 2){

            if (DSCmode == 0){
                API300 = "http://dcim.local";
                API300_100 = "http://dcim.local/command.cgi?op=100";
            }
            else {
                API300 = "http://flashair";
                API300_100 = "http://flashair/command.cgi?op=100";
            }

        }
        updateHandler = new Handler(); // return time

        adapterImageName = myAdapter.getItemCount();
        totalData = new ArrayList<>( );

    }

    private Button.OnClickListener nextsaveexamination = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!Button_Click.isFastDoubleClick()) {
                int adapterImageName = myAdapter.getItemCount();
                ArrayList<String> totalData = new ArrayList<String>();
                for(int i = 0 ; i < adapterImageName; i++) {
                    Addexamination_ItemRecycler tmpData = myAdapter.getData( i);
                    Log.i("Data", tmpData.getmName());
                    totalData.add( tmpData.getmName());
                }
                File fileport = Environment.getExternalStorageDirectory();
                File imagefile = new File(fileport,"1.MiiS/2.IMAGE");
                final ArrayList<String> imageList = (ArrayList<String>) (totalData);
                for(int i = 0; i <imageList.size(); i++) {
                    if (imageList.get(i).length() > 12){
                        ImagePath = imageList.get(i);
                    } else {
                        ImagePath = imagefile + "/" + imageList.get(i);
                    }
                    myDB.insertImagePath(pID,ImagePath);
                    myDB.insertchecktime(checkTime);
                }
                myDB.updateatientstatus(pID,checkTime);
            }
        }
    };

    //获取权限
    private void getPerMission() {
        mPermissionsChecker = new PermissionsChecker(Addexamination_examination.this);
        if (mPermissionsChecker.lacksPermissions(permsLocation)) {
            ActivityCompat.requestPermissions(Addexamination_examination.this, permsLocation, RESULT_CODE_LOCATION);
        }
    }

    private void initView() {
        wifiListBeanList = new ArrayList<>();
        mScanResultList = new ArrayList<>();
    }

    public void startRepeating(View v) {
        //mHandler.postDelayed(mToastRunnable, 5000);
    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        DecSSID = sharedPreferences.getString(DECSSID, "flashair_ec21e54ca69e");
        DecSSIDpwd = sharedPreferences.getString(DECSSIDPWD, "12345678");
        DibSSID = sharedPreferences.getString(DIBSSID, "DIB-00044b8d480c");
        DibSSIDpwd = sharedPreferences.getString(DIBSSIDPWD, "Aa123456");
        PrinterSSID = sharedPreferences.getString(PRINTERSSID, "MiiS-OA");
        PrinterSSIDpwd = sharedPreferences.getString(PRINTERSSIDPWD, "vacationoa");

        AP_300IP = sharedPreferences.getString(AP300IP, "192.168.0.100");
        AP_DIBIP = sharedPreferences.getString(APDIBIP, "192.168.0.123");
        AP_HUBSSID = sharedPreferences.getString("APHUBSSID", "MiiS-FAB");
        AP_HUBSSIDPWD = sharedPreferences.getString(APHUBSSIDPWD, "vacationfab");

        MH_300IP = sharedPreferences.getString(MH300IP, "192.168.0.102");
        MH_DIBIP = sharedPreferences.getString(MHDIBIP, "192.168.0.103");


        AS_300SSID = sharedPreferences.getString("AS300SSID", "123");
        AS_300SSIDPWD = sharedPreferences.getString(AS300SSIDPWD, "300ssidpwd");
        AS_DIBSSID = sharedPreferences.getString("ASDIBSSID", "DIBssid");
        AS_DIBSSIDPWD = sharedPreferences.getString(ASDIBSSIDPWD, "DIBssidpwd");
        AS_PRINTERSSID = sharedPreferences.getString("ASPRINTERSSID", "PRINTERssid");
        AS_PRINTERSSIDPWD = sharedPreferences.getString(ASPRINTERSSIDPWD, "PRINTERssidpwd");
        AS_HUBSSID = sharedPreferences.getString("ASHUBSSID", "HUBssid");
        AS_HUBSSIDPWD = sharedPreferences.getString(ASHUBSSIDPWD, "HUBssidpwd");

        SharedPreferences sharedPreferences1 = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        savedRadioIndex = sharedPreferences1.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);

        SharedPreferences DSCsharedPreferences = getSharedPreferences("DSC_MODE", MODE_PRIVATE);
        DSCmode = DSCsharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);

    }

    public void updateViews() {

        Log.i("DecSSID",String.valueOf(DecSSID));
        Log.i("DecSSIDpwd",String.valueOf(DecSSIDpwd));
        Log.i("DibSSID",String.valueOf(DibSSID));
        Log.i("DibSSIDpwd",String.valueOf(DibSSIDpwd));
        Log.i("PrinterSSID",String.valueOf(PrinterSSID));
        Log.i("PrinterSSIDpwd",String.valueOf(PrinterSSIDpwd));
        Log.i("AP_300IP",String.valueOf(AP_300IP));
        Log.i("AP_DIBIP",String.valueOf(AP_DIBIP));
        Log.i("AP_HUBSSID",String.valueOf(AP_HUBSSID));
        Log.i("AP_HUBSSIDPWD",String.valueOf(AP_HUBSSIDPWD));
        Log.i("MH_300IP",String.valueOf(MH_300IP));
        Log.i("MH_DIBIP",String.valueOf(MH_DIBIP));
        Log.i("AS_300SSID",String.valueOf(AS_300SSID));
        Log.i("AS_300SSIDPWD",String.valueOf(AS_300SSIDPWD));
        Log.i("AS_DIBSSID",String.valueOf(AS_DIBSSID));
        Log.i("AS_DIBSSIDPWD",String.valueOf(AS_DIBSSIDPWD));
        Log.i("AS_PRINTERSSID",String.valueOf(AS_PRINTERSSID));
        Log.i("AS_PRINTERSSIDPWD",String.valueOf(AS_PRINTERSSIDPWD));
        Log.i("AS_HUBSSID",String.valueOf(AS_HUBSSID));
        Log.i("AS_HUBSSIDPWD",String.valueOf(AS_HUBSSIDPWD));
        Log.i("MODE_NUMBER",String.valueOf(savedRadioIndex));
        Log.i("DSC_MODE",String.valueOf(DSCmode));

    }

    private Button.OnClickListener nextBigphoto = new Button.OnClickListener() {
        @Override
        public void onClick(final View v) {

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            final String ssid = wifiInfo.getSSID();
            //startRepeating(v);


            if(savedRadioIndex == 0){

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
                }

                if(scannedResult.indexOf(AP_HUBSSID)<0){
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                    a_builder.setMessage(R.string.Dialog_DEC_1_check_DSC_status_2_check_SSID_setting)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle(R.string.Alert);
                    alert.show();
                }
                else if(!ssid.equals("\"" + AP_HUBSSID + "\"")){
                    AP_HUB();
                    Context context1 = v.getContext();
                    DSCdialog= ProgressDialog.show(context1,getResources().getString(R.string.Linking_AP), getResources().getString(R.string.Please_wait),true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }).start();

                    Handler handlerd = new Handler();
                    handlerd.postDelayed(new Runnable(){

                        @Override
                        public void run() {

                            DSCdialog.dismiss();
                            String DSCping = Ping(IP300);
                            System.out.println("PINGPINGPING : " + DSCping);

                            if(DSCping.equals("success")){

                                Biginputimage.setVisibility(View.GONE);
                                inputimage = findViewById(R.id.inputimage);
                                inputimage.setVisibility(View.VISIBLE);
                                recycleView = findViewById(R.id.recycleView);
                                recycleView.setVisibility(View.VISIBLE);
                                //DEC200Wifi();
                                tv_text = (TextView) findViewById(R.id.tv_text);
                                tv_text.setVisibility(View.VISIBLE);
                                shark();

                                Handler startUpdatehandler = new Handler();
                                startUpdatehandler.postDelayed(new Runnable(){

                                    @Override
                                    public void run() {

                                        startUpdate(); // return time
                                        System.out.println("DSC_APAPAPAPAP000000000");

                                    }}, 4000);

                            } else if(DSCping.equals("faild")){
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                                a_builder.setMessage(R.string.Addex_exam_1_IP_invalid_2_check_DSC_status_3_check_DSC_IP)
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle(R.string.Alert);
                                alert.show();
                            }

                        }}, 6000);


                } else{

                    String DSCping = Ping(IP300);
                    System.out.println("PINGPINGPING : " + DSCping);
                    if(DSCping.equals("success")){
                        Toast.makeText(Addexamination_examination.this, R.string.Linking,Toast.LENGTH_SHORT).show();
                        Biginputimage.setVisibility(View.GONE);
                        inputimage = findViewById(R.id.inputimage);
                        inputimage.setVisibility(View.VISIBLE);
                        recycleView = findViewById(R.id.recycleView);
                        recycleView.setVisibility(View.VISIBLE);

                        tv_text = (TextView) findViewById(R.id.tv_text);
                        tv_text.setVisibility(View.VISIBLE);
                        shark();

                        Handler startUpdatehandler = new Handler();
                        startUpdatehandler.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                startUpdate(); // return time
                                System.out.println("DSC_APAPAPAPAP11111111");

                            }}, 4000);
                    }
                    else if(DSCping.equals("faild")){
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                        a_builder.setMessage(R.string.Addex_exam_DSC_not_link_AP)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle(R.string.Alert);
                        alert.show();
                    }


                }

            }


            else if(savedRadioIndex == 1){
                startUpdate();
                Biginputimage.setVisibility(View.GONE);
                inputimage = findViewById(R.id.inputimage);
                inputimage.setVisibility(View.VISIBLE);
                recycleView = findViewById(R.id.recycleView);
                recycleView.setVisibility(View.VISIBLE);

                tv_text = (TextView) findViewById(R.id.tv_text);
                tv_text.setVisibility(View.VISIBLE);
                shark();
            }


            else if(savedRadioIndex == 2){

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
                }

                if(scannedResult.indexOf(AS_300SSID)<0){
                    openDialog();
                }
                else if(!ssid.equals("\"" + AS_300SSID + "\"")){
                    DEC200Wifi();
                    Biginputimage.setVisibility(View.GONE);
                    inputimage = findViewById(R.id.inputimage);
                    inputimage.setVisibility(View.VISIBLE);
                    recycleView = findViewById(R.id.recycleView);
                    recycleView.setVisibility(View.VISIBLE);

                    //DEC200Wifi();
                    Context context1 = v.getContext();
                    tv_text = (TextView) findViewById(R.id.tv_text);
                    tv_text.setVisibility(View.VISIBLE);
                    shark();
                    final ProgressDialog dialog= ProgressDialog.show(context1,getResources().getString(R.string.Addex_exam_DSC_linking), getResources().getString(R.string.Please_wait),true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sleep(5000);
                                dialog.dismiss();
                                //tv_text = (TextView) findViewById(R.id.tv_text);
                                //tv_text.setVisibility(View.VISIBLE);
                                shark();
                            }
                            catch(InterruptedException ex){
                                ex.printStackTrace();
                            }
                        }
                    }).start();


                    Handler startUpdatehandler = new Handler();
                    startUpdatehandler.postDelayed(new Runnable(){

                        @Override
                        public void run() {

                            startUpdate(); // return time
                            System.out.println("startUpdatestartUpdatestartUpdatestartUpdatestartUpdatestartUpdate");

                        }}, 4000);


                } else{
                    Toast.makeText(Addexamination_examination.this, R.string.Linking,Toast.LENGTH_SHORT).show();
                    Biginputimage.setVisibility(View.GONE);
                    inputimage = findViewById(R.id.inputimage);
                    inputimage.setVisibility(View.VISIBLE);
                    recycleView = findViewById(R.id.recycleView);
                    recycleView.setVisibility(View.VISIBLE);

                    tv_text = (TextView) findViewById(R.id.tv_text);
                    tv_text.setVisibility(View.VISIBLE);
                    shark();


                    Handler startUpdatehandler = new Handler();
                    startUpdatehandler.postDelayed(new Runnable(){

                        @Override
                        public void run() {

                            startUpdate(); // return time
                            System.out.println("startUpdatestartUpdatestartUpdatestartUpdatestartUpdatestartUpdate");

                        }}, 4000);

                }
            }
        }
    };

    public void onAlignmentSelected(final View view) {
        radioGroup = findViewById(R.id.radioGroup);
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.photo:

                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                final String ssid = wifiInfo.getSSID();

                if(savedRadioIndex == 0){

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
                    }

                    if(scannedResult.indexOf(AP_HUBSSID)<0){
                        openDialog();
                    }
                    else if(!ssid.equals("\"" + AP_HUBSSID + "\"")){
                        AP_HUB();
                        Biginputimage.setVisibility(View.GONE);
                        inputimage = findViewById(R.id.inputimage);
                        inputimage.setVisibility(View.VISIBLE);
                        recycleView = findViewById(R.id.recycleView);
                        recycleView.setVisibility(View.VISIBLE);
                        startUpdate();

                        //DEC200Wifi();
                        Context context1 = this;
                        tv_text = (TextView) findViewById(R.id.tv_text);
                        tv_text.setVisibility(View.VISIBLE);
                        shark();
                        final ProgressDialog dialog= ProgressDialog.show(context1,getResources().getString(R.string.Addex_exam_DSC_linking), getResources().getString(R.string.Please_wait),true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    sleep(5000);
                                    dialog.dismiss();
                                    tv_text = (TextView) findViewById(R.id.tv_text);
                                    tv_text.setVisibility(View.VISIBLE);
                                    shark();
                                }
                                catch(InterruptedException ex){
                                    ex.printStackTrace();
                                }
                            }
                        }).start();

                        Handler startUpdatehandler = new Handler();
                        startUpdatehandler.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                startUpdate(); // return time
                                System.out.println("startUpdatestartUpdatestartUpdatestartUpdatestartUpdatestartUpdate");

                            }}, 4000);


                    } else{
                        Toast.makeText(Addexamination_examination.this, R.string.Linking,Toast.LENGTH_SHORT).show();
                        tv_text = (TextView) findViewById(R.id.tv_text);
                        tv_text.setVisibility(View.VISIBLE);
                        shark();

                        Handler startUpdatehandler = new Handler();
                        startUpdatehandler.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                startUpdate(); // return time
                                System.out.println("startUpdatestartUpdatestartUpdatestartUpdatestartUpdatestartUpdate");

                            }}, 4000);

                    }

                }


                else if(savedRadioIndex == 1){
                    tv_text = (TextView) findViewById(R.id.tv_text);
                    tv_text.setVisibility(View.VISIBLE);
                    startUpdate();
                }


                else if(savedRadioIndex == 2){

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
                    }

                    if(scannedResult.indexOf(AS_300SSID)<0){
                        DEC200Wifi();
                        openDialog();
                    }
                    else if(!ssid.equals("\"" + AS_300SSID + "\"")){
                        DEC200Wifi();
                        Context context1 = this;
                        tv_text = (TextView) findViewById(R.id.tv_text);
                        tv_text.setVisibility(View.VISIBLE);
                        shark();
                        final ProgressDialog dialog= ProgressDialog.show(context1,getResources().getString(R.string.Addex_exam_DSC_linking), getResources().getString(R.string.Please_wait),true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    sleep(5000);
                                    dialog.dismiss();
                                    tv_text = (TextView) findViewById(R.id.tv_text);
                                    tv_text.setVisibility(View.VISIBLE);
                                    shark();
                                }
                                catch(InterruptedException ex){
                                    ex.printStackTrace();
                                }
                            }
                        }).start();


                        Handler startUpdatehandler = new Handler();
                        startUpdatehandler.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                startUpdate(); // return time
                                System.out.println("startUpdatestartUpdatestartUpdatestartUpdatestartUpdatestartUpdate");

                            }}, 4000);


                    } else{
                        Toast.makeText(Addexamination_examination.this, R.string.Linking,Toast.LENGTH_SHORT).show();
                        tv_text = (TextView) findViewById(R.id.tv_text);
                        tv_text.setVisibility(View.VISIBLE);
                        shark();


                        Handler startUpdatehandler = new Handler();
                        startUpdatehandler.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                startUpdate(); // return time
                                System.out.println("startUpdatestartUpdatestartUpdatestartUpdatestartUpdatestartUpdate");

                            }}, 4000);


                    }
                }





                recycleView = findViewById(R.id.recycleView);
                recycleView.setVisibility(View.VISIBLE);




                break;



            // ========================================================================================
            // ========================================================================================
            // ========================================================================================
            // ========================================================================================
            // ========================================================================================
            // ========================================================================================
            // ========================================================================================


            case R.id.photoLibrary:


                tv_text = (TextView) findViewById(R.id.tv_text);
                tv_text.setVisibility(View.GONE);
                shark();




                BSImagePicker pickerDialog = new BSImagePicker.Builder("com.example.miis200.fileprovider")
                        .setMaximumDisplayingImages(Integer.MAX_VALUE)
                        .isMultiSelect()
                        .setMinimumMultiSelectCount(1)
                        .setMaximumMultiSelectCount(10)
                        .build();
                pickerDialog.show(getSupportFragmentManager(), "picker");
                break;





        }
    }

    private Button.OnClickListener nextBigphotoLibrary = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {




            inputimage = findViewById(R.id.inputimage);




            inputimage.setVisibility(View.VISIBLE);
            Biginputimage.setVisibility(View.GONE);
            BSImagePicker pickerDialog = new BSImagePicker.Builder("com.example.miis200.fileprovider")
                    .setMaximumDisplayingImages(Integer.MAX_VALUE)
                    .isMultiSelect()
                    .setMinimumMultiSelectCount(1)
                    .setMaximumMultiSelectCount(10)
                    .build();
            pickerDialog.show(getSupportFragmentManager(), "picker");
        }
    };





    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        //Glide.with(Addexamination_examination.this).load(uri).into(ivImage2);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        for (int i=0; i < uriList.size(); i++) {
            if (i >= 10) return;

            System.out.println("oooooooooooooooooooaa11");


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap thumbnail = BitmapFactory.decodeFile(uriList.get(i).getPath(), options);


            itemRecyclerSetting = new Addexamination_ItemRecycler(thumbnail, uriList.get(i).getPath());
            myAdapter.addData(myAdapter.getItemCount(), itemRecyclerSetting);


            refreshImageList();



        }

    }

    @Override
    public void loadImage(File imageFile, ImageView ivImage) {
        Glide.with(Addexamination_examination.this).load(imageFile).into(ivImage);
    }





    //===============================================================================================
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
                int adapterImageName = myAdapter.getItemCount();
                ArrayList<String> totalData = new ArrayList<String>();
                for (int i = 0; i < adapterImageName; i++) {
                    Addexamination_ItemRecycler tmpData = myAdapter.getData(i);
                    Log.i("Data", tmpData.getmName());
                    totalData.add(tmpData.getmName());
                }
                ArrayList<String> imageList = (ArrayList<String>) (totalData);
                System.out.println("sssssssssssssss : " + imageList.size());

                if(imageList.size() == 0){
                    Intent intent = new Intent(getApplicationContext(), Option.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                    a_builder.setMessage(R.string.Go_home_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CharSequence PPID= pid.getText();
                                    SQLiteDatabase idDB = myDB.getReadableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("STATUS","0");
                                    values.put("LEFTIMAGEPATH"," ");
                                    values.put("RIGHTIMAGEPATH"," ");
                                    values.put("Eyepath1"," ");
                                    values.put("Eyepath2"," ");
                                    values.put("Eyepath3"," ");
                                    values.put("Eyepath4"," ");
                                    values.put("Eyepath5"," ");
                                    values.put("Eyepath6"," ");
                                    values.put("Eyepath7"," ");
                                    values.put("Eyepath8"," ");
                                    values.put("AIIMG1"," ");
                                    values.put("AIIMG2"," ");
                                    values.put("AIIMG3"," ");
                                    values.put("AIIMG4"," ");
                                    values.put("AIIMG5"," ");
                                    values.put("AIIMG6"," ");
                                    values.put("AIIMG7"," ");
                                    values.put("AIIMG8"," ");
                                    values.put("AIIMG9"," ");
                                    values.put("AIIMG10"," ");
                                    values.put("AIIMG11"," ");
                                    values.put("AIIMG12"," ");
                                    values.put("AIIMG13"," ");
                                    values.put("AIIMG14"," ");
                                    values.put("AIIMG15"," ");
                                    values.put("AIIMG16"," ");
                                    values.put("DOCTORDESC"," ");
                                    values.put("DOCTORDESC2"," ");
                                    values.put("DOCTORDESC3"," ");
                                    values.put("DOCTORDESC4"," ");
                                    values.put("DOCTORDESC5"," ");
                                    values.put("DOCTORDESC6"," ");
                                    values.put("DOCTORDESC7"," ");
                                    values.put("DOCTORDESC8"," ");
                                    values.put("DOCTORDESC9"," ");
                                    values.put("DOCTORDESC10"," ");
                                    values.put("DOCTORDESC11"," ");
                                    values.put("DOCTORDESC12"," ");
                                    values.put("DOCTORDESC13"," ");
                                    values.put("DOCTORDESC14"," ");
                                    values.put("DOCTORDESC15"," ");
                                    values.put("DOCTORDESC16"," ");
                                    values.put("DOCTORDESC17"," ");
                                    values.put("DOCTORDESC18"," ");
                                    values.put("DOCTORDESC19"," ");
                                    values.put("DOCTORDESC20"," ");
                                    values.put("DOCTORDESC21"," ");
                                    values.put("DOCTORDESC22"," ");
                                    values.put("DOCTORDESC23"," ");
                                    values.put("DOCTORDESC24"," ");
                                    values.put("DOCTORDESC25"," ");
                                    values.put("DOCTORDESC26"," ");

                                    idDB.update("users_data",values, "PATIENTID=?", new String[]{PPID.toString()});
                                    updateHandler.removeCallbacks(DSCstatusChecker);

                                    Intent intent = new Intent(getApplicationContext(), Option.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle(R.string.Go_home_title);
                    alert.show();
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================

    private Spinner.OnItemSelectedListener spnPreferListener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            String sel=parent.getSelectedItem().toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };
    //return time   Start
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            viewingList = true;
        }
        else {
            viewingList = false;
        }
    }
    public boolean checkIfListView() {
        // Check if user is viewing a content list
        if(viewingList) {
            return true;
        }
        return false;
    }

    public Runnable statusChecker = new Runnable() {
        @Override
        public void run() {

            String Dirpath = "/" + rootDir;
            ArrayList <NameValuePair> httpParams = new  ArrayList <NameValuePair> ();
            httpParams.add(new BasicNameValuePair("DIR", Dirpath));
            Dirpath = URLEncodedUtils.format (httpParams, "UTF-8" );
            Log.i("DirpathDirpath",Dirpath);


            if (DSCmode == 0 ){
                API300_100_1 = API300_100 + "/" + "&DIR=/DCIM" + "/" + today;
            } else {
                API300_100_1 = API300_100 + "&" + Dirpath;
            }


            if (checkIfListView() == true) {
                new AsyncTask<String, Void, String>(){
                    @Override
                    protected String doInBackground(String... params) {
                        return FlashAirRequest.getString(params[0]);
                    }
                    @Override
                    protected void onPostExecute(String status) {
                        String totalfile = status;

                        mylist = new ArrayList<String>();

                        Pattern p = Pattern.compile("[A-Z]+[A-Z]+[0-9]+.jpg");
                        Matcher m = p.matcher(totalfile);



                        int image_val = 0;


                        while (m.find()){
                            System.out.println("Found JPG :" + m.group());
                            mylist.add(m.group());
                            image_val ++;
                        }
                        Log.i("totalimg",String.valueOf(image_val));

                        if(new_image_val == -1){
                            new_image_val = image_val;
                        }

                        else if (new_image_val < image_val) {
                            listDirectory(directoryName);
                            new_image_val = image_val;
                        }

                    }
                }.execute(API300_100_1);
            }
            updateHandler.postDelayed(statusChecker, checkInterval);
        }
    };


    public Runnable DSCstatusChecker = new Runnable() {
        @Override
        public void run() {

            if(savedRadioIndex == 0) {

                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                final String ssid = wifiInfo.getSSID();

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
                }
                if(!ssid.equals("\"" + AP_HUBSSID + "\"")){
                    tv_text = (TextView) findViewById(R.id.tv_text);
                    tv_text.setVisibility(View.GONE);
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                    a_builder.setMessage(R.string.Addex_exam_unlink_check_AP_status)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    updateHandler.removeCallbacks(DSCstatusChecker);
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle(R.string.Alert);
                    alert.show();
                    updateHandler.removeCallbacks(DSCstatusChecker);
                }
                else if(ssid.equals("\"" + AP_HUBSSID + "\"")){

                    String DSCping = Ping(IP300);
                    System.out.println("PINGPINGPING : " + DSCping);
                    if (DSCping.equals("faild")) {
                        tv_text = (TextView) findViewById(R.id.tv_text);
                        tv_text.setVisibility(View.GONE);
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                        a_builder.setMessage(R.string.Addex_exam_unlink_check_DSC_status)
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        updateHandler.removeCallbacks(DSCstatusChecker);
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle(R.string.Alert);
                        alert.show();
                        updateHandler.removeCallbacks(DSCstatusChecker);

                    } else {
                        updateHandler.postDelayed(DSCstatusChecker, 10000);
                    }
                }
            }

            if(savedRadioIndex == 1) {
                String DSCping = Ping(IP300);

                if (DSCping.equals("faild")) {
                    tv_text = (TextView) findViewById(R.id.tv_text);
                    tv_text.setVisibility(View.GONE);
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                    a_builder.setMessage(R.string.Addex_exam_MH_1_check_hotspot_2_check_DSC_status_2_check_IP)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    updateHandler.removeCallbacks(DSCstatusChecker);
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle(R.string.Alert);
                    alert.show();
                    updateHandler.removeCallbacks(DSCstatusChecker);

                } else {
                    updateHandler.postDelayed(DSCstatusChecker, 10000);
                }
            }



            if (savedRadioIndex == 2){

                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                final String ssid = wifiInfo.getSSID();
                System.out.println("DSCstatusCheckerssssssssssssssssssID" + ssid);

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
                }

                if(!ssid.equals("\"" + AS_300SSID + "\"")){

                    tv_text = (TextView) findViewById(R.id.tv_text);
                    tv_text.setVisibility(View.GONE);
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_examination.this);
                    a_builder.setMessage(R.string.Addex_exam_unlink_check_DSC_status)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    updateHandler.removeCallbacks(DSCstatusChecker);
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle(R.string.Alert);
                    alert.show();
                    updateHandler.removeCallbacks(DSCstatusChecker);

                } else if(ssid.equals("\"" + AS_300SSID + "\"")){
                    updateHandler.postDelayed(DSCstatusChecker, 10000);
                }

            }

        }
    };



    public void startUpdate() {
        statusChecker.run();
        DSCstatusChecker.run();

    }
    public void stopUpdate() {

    }
    //return time   End


    public void listDirectory(String dir) {

        // Fetch number of items in directory and display in a TextView
        dir = "/" + dir;
        Log.i("dasdasdasdasdasmko",dir);
        ArrayList <NameValuePair> httpParams = new  ArrayList <NameValuePair> ();
        httpParams.add(new BasicNameValuePair("DIR", dir));
        dir = URLEncodedUtils.format (httpParams, "UTF-8" );
        Log.i("dasdasdasdasdasmko",dir);





        // Fetch list of items in directory and display in a ListView
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {

                String dir = params[0];
                ArrayList<String> fileNames = new ArrayList<String>();

                try {

                    System.out.println("mylistmylistmylistmylist" + mylist);
                    System.out.println("mylistmylistmylistmylist" + mylist.get(mylist.size()-1));
                    System.out.println("mylistmylistmylistmylist" + rootDir);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Imagename = mylist.get(mylist.size()-1);


                // View Lister Image
                ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

                String fileName = "";
                fileName = API300 + rootDir + "/" + Imagename;
                Map<String, Object> entry = new HashMap<String, Object>();

                Bitmap thumbnail = FlashAirRequest.getBitmap(fileName);
                entry.put("fname", fileNames); // Put file name onto the map
                data.add(entry);

                Pattern p = Pattern.compile("[A-Z]+[0-9]+.jpg");

                itemRecyclerSetting = new Addexamination_ItemRecycler(thumbnail, Imagename);
                System.out.println("oooooooooooooooooooaa : " + myAdapter.getItemCount());
                myAdapter.addData(myAdapter.getItemCount(), itemRecyclerSetting);
                mLastPictureName = Imagename;


                //資料夾路徑
                File fileport = Environment.getExternalStorageDirectory();

                //APP內存
                //File externalFilesDir = getExternalFilesDir("1MiiS/IMAGE");

                //設定新的資料夾路徑
                //SD card 儲存
                File imagefile = new File(fileport,"1.MiiS/2.IMAGE");
                Log.d("Files", "imagefilePath: " + imagefile);
                //創建資料夾
                imagefile.mkdirs();

                try {

                    //建立JPG檔
                    Log.i("mLastPictureNamemLastPictureName", Imagename);
                    File imageFile = new File(imagefile, "/" + Imagename);
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(imageFile);
                    //Image 儲存
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    Log.i("CCCCCCCCCC","CCCCCCCCCCCC");
                }


                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }catch(IOException e) {
                    e.printStackTrace();
                }
                //儲存照片 END


                refreshImageList();
                // Set the file list to a widget
                return null;
            }
        }.execute(dir);



    }

    public void refreshImageList(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
            }
        });
    }






    public void onBackPressed() {
        finish();
    }


    public void openDialog() {
        DiaLogDEC diaLogDEC = new DiaLogDEC();
        diaLogDEC.show(getSupportFragmentManager(), "example dialog");
    }

    private void shark() {
        Timer timer = new Timer();
        TimerTask taskcc = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (clo == 0) {
                            clo = 1;
                            tv_text.setTextColor(Color.WHITE);
                        } else {
                            if (clo == 1) {

                                clo = 2;
                                tv_text.setTextColor(Color.GRAY);
                            } else if (clo == 2) {

                                clo = 3;
                                tv_text.setTextColor(Color.WHITE);

                            } else {
                                clo = 0;
                                tv_text.setTextColor(Color.GRAY);
                            }
                        }
                    }
                });
            }
        };
        timer.schedule(taskcc, 1, 2000);  //<span style="color: rgb(85, 85, 85); font-family: 'microsoft yahei'; font-size: 15px; line-height: 35px;">第二个参数分别是delay（多长时间后执行），第三个参数是：duration（执行间隔）单位为：ms</span>
    }

    public String Ping(String str) {
        String resault = "";
        Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 1 -w 2 " + str);
            int status = p.waitFor();
            System.out.println("dddddddddddd1111 : " + status);

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            System.out.println("Return ============" + buffer.toString());

            if (status == 0) {
                resault = "success";
            } else {
                resault = "faild";
            }
            System.out.println("dddddddddddd" + resault);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return resault;
    }



}
