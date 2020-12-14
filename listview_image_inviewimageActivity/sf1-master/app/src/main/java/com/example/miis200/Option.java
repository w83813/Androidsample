package com.example.miis200;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;

import com.example.miis200.utils.WifiUtil;

import java.util.ArrayList;
import java.util.List;

public class Option extends AppCompatActivity {

    public static final String TAG = "OptionActivity";

    WifiManager wifiManager;
    List scannedResult;

    LinearLayout addexamination,addpatient,WaitVimg,WaitPrinter,PatientPDFlist,Setting;

    Intent mAddexaminationPatientInetnt,mWaitviewimageIntent,mWaitprinterInent;


    private String aimWifiName = "flashair_ec21e54ca69e";
    private String aimWifiPwd = "12345678";

    private void changeWifi2() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(aimWifiName, aimWifiPwd);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("選單");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        mAddexaminationPatientInetnt = new Intent(this, PatientlistActivity.class);
        mAddexaminationPatientInetnt.putExtra(PatientlistActivity.WORKFLOW, Workflow.ADDEXAMINATION);

        mWaitviewimageIntent = new Intent(this, PatientlistActivity.class);
        mWaitviewimageIntent.putExtra(PatientlistActivity.WORKFLOW, Workflow.WAITVIEWIMAGE);

        mWaitprinterInent = new Intent(this, PatientlistActivity.class);
        mWaitprinterInent.putExtra(PatientlistActivity.WORKFLOW, Workflow.WAITPRINTER);


        addexamination = (LinearLayout) findViewById(R.id.addexamination);
        addpatient = (LinearLayout) findViewById(R.id.addpatient);
        WaitVimg = (LinearLayout) findViewById(R.id.BtnWaitVimg);
        WaitPrinter = (LinearLayout) findViewById(R.id.BtnWaitPrinter);
        PatientPDFlist = (LinearLayout) findViewById(R.id.patientpdflist);
        Setting = (LinearLayout) findViewById(R.id.setting);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();




        addexamination.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(final View view) {
                if (!Button_Click.isFastDoubleClick()) {
                    startActivity(mAddexaminationPatientInetnt);
                }
            }
        });

        addpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, AddPatient.class);
                    startActivity(intent);
                }
            }
        });

        WaitVimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    startActivity(mWaitviewimageIntent);
                }
            }
        });

        WaitPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    startActivity(mWaitprinterInent);
                }
            }
        });

        PatientPDFlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, Review.class);
                    startActivity(intent);
                }
            }
        });

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, Modesetting.class);
                    startActivity(intent);
                }
            }
        });




    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Option.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.Exit)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}