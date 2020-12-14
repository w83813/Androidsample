package com.example.miis200;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miis200.utils.WifiUtil;

import java.util.ArrayList;

public class Printerwait extends AppCompatActivity {
    private String printName = "MiiS-OA";
    private String printPwd = "vacationoa";

    private void printWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(printName, printPwd);
    }



    ArrayList<User> userList;
    ListView listView;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        printWifi();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printerwait);


        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Printerwait_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================



        userList = new ArrayList<>();

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
                Intent intent = new Intent(getApplicationContext(), Option.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================
}
