package com.example.miis200;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WaitviewimageActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView wv_checktime,wv_name,wv_patientid,wv_birthday,wv_gender,wv_phonenumber;
    Button btn_viewimage,btn_printer;
    Intent mViewimageActivityIntent,mChoiceimageActivityIntent;

    private RecyclerView recyclerView;
    private Eyeimage_ItemRecycler itemRecyclerSetting;
    private Eyeimage_MyAdapter myAdapter;
    private List<Eyeimage_ItemRecycler> listRecycler = new ArrayList<Eyeimage_ItemRecycler>(  );

    String patientid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitviewimage);

        //============================================
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置
        recyclerView.setLayoutManager(manager);
        //实例化适配器
        myAdapter = new Eyeimage_MyAdapter(listRecycler);
        //设置适配器

        //============================================

        databaseHelper = new DatabaseHelper(this);

        wv_checktime = findViewById(R.id.wv_checktime);
        wv_name = findViewById(R.id.wv_name);
        wv_patientid = findViewById(R.id.wv_patientid);
        wv_birthday = findViewById(R.id.wv_birthday);
        wv_gender = findViewById(R.id.wv_gender);
        wv_phonenumber = findViewById(R.id.wv_phonenumber);

        btn_viewimage = findViewById(R.id.btn_viewimage);
        btn_printer = findViewById(R.id.wv_printer);

        Intent intent = this.getIntent();
        patientid = intent.getStringExtra("patientid");
        wv_name.setText(intent.getStringExtra("patientname"));
        wv_patientid.setText(intent.getStringExtra("patientid"));
        wv_birthday.setText(intent.getStringExtra("patientbir"));
        wv_gender.setText(intent.getStringExtra("patientgender"));
        wv_phonenumber.setText(intent.getStringExtra("patientphonenumber"));

        mViewimageActivityIntent = new Intent(this,ViewimageActivity.class);

        mChoiceimageActivityIntent = new Intent(this,ChoiceimageActivity.class);

        int imagesize = databaseHelper.getImagePath(intent.getStringExtra("patientid")).size();

        Log.v("AAAAAAAAAAAAA1",String.valueOf(intent.getStringExtra("patientid")));
        Log.v("AAAAAAAAAAAAA2",String.valueOf(databaseHelper.getImagePath(intent.getStringExtra("patientid")).size()));
        Log.v("AAAAAAAAAAAAA",String.valueOf(databaseHelper.getchecktime(intent.getStringExtra("patientid")).get(0)));


        for (int i=0; i<imagesize; i++){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap thumbnail = BitmapFactory.decodeFile((String) databaseHelper.getImagePath(patientid).get(i), options);
            itemRecyclerSetting = new Eyeimage_ItemRecycler(thumbnail, (String) databaseHelper.getImagePath(patientid).get(i));
                    myAdapter.addData(myAdapter.getItemCount(), itemRecyclerSetting);

        }
        recyclerView.setAdapter(myAdapter);

        btn_viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewimageActivityIntent.putExtra("patientid",patientid);
                startActivity(mViewimageActivityIntent);
            }
        });

        btn_printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChoiceimageActivityIntent.putExtra("patientid",patientid);
                startActivity(mChoiceimageActivityIntent);
            }
        });

    }
}
