package com.droidbyme.recyclerviewselection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.droidbyme.recyclerviewselection.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btnMultiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        btnMultiple.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnMultiple:
                startActivity(new Intent(MainActivity.this, MultipleSelectionActivity.class));
                break;


        }
    }

    private void initView() {

        btnMultiple = (AppCompatButton) findViewById(R.id.btnMultiple);

    }
}