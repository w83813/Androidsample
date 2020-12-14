package com.example.miis200;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miis200.utils.PermissionsChecker;

public class Loging extends AppCompatActivity {

    private EditText edtid,edtpw;
    private TextView error;
    private Button login;

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private final int RESULT_CODE_LOCATION = 0x001;
    //定位权限,获取app内常用权限
    String[] permsLocation = {"android.permission.ACCESS_WIFI_STATE"
            , "android.permission.CHANGE_WIFI_STATE"
            , "android.permission.ACCESS_COARSE_LOCATION"
            , "android.permission.ACCESS_FINE_LOCATION"
            , "android.permission.WRITE_EXTERNAL_STORAGE"
            , "android.permission.READ_EXTERNAL_STORAGE"};

    //获取权限
    private void getPerMission() {
        mPermissionsChecker = new PermissionsChecker(Loging.this);
        if (mPermissionsChecker.lacksPermissions(permsLocation)) {
            ActivityCompat.requestPermissions(Loging.this, permsLocation, RESULT_CODE_LOCATION);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_loging);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getPerMission();

        edtid = (EditText)findViewById(R.id.edtid);
        edtid.clearFocus();
        edtpw = (EditText)findViewById(R.id.edtpw);
        error = (TextView) findViewById(R.id.error);
        login = (Button) findViewById(R.id.login);
        //建立自訂義執行動作
        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] account = {"admin", "guest",""};
                String[] password = {"admin", "guest",""};

                // 設定兩組固定帳號密碼
                for (int i = 0; i < account.length; i++) { // 用一個迴圈把帳號跟密碼從陣列取出
                    if (edtid.getText().toString().equals(account[i])
                            && edtpw.getText().toString().equals(password[i])) {    //設定判斷式，是否帳號密碼都正確

                        error.setText(R.string.Login_login_success);  //顯示訊息

                        Intent intent = new Intent(); //登入成功轉跳至首頁
                        intent.setClass(Loging.this, Option.class);
                        startActivity(intent);
                        Loging.this.finish();

                        break;//一定要break,否則會無法判斷第二組

                    } else {
                        error.setText(R.string.Login_Account_or_Password_fail);
                    }
                }
            }
        } );

    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Loging.this);
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
