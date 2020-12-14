package com.example.miis200;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoiceimageActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    private RecyclerView recyclerView;
    private ArrayList<ChoiceimageItemrecycler> choiceimageItemrecyclers = new ArrayList<>();
    private ChoiceimageAdapter adapter;
    private Button btn_printer;

    String patientid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choiceimage);

        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.choiceimagerecyclerview);

        btn_printer = findViewById(R.id.chimg_printer);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ChoiceimageAdapter(this, choiceimageItemrecyclers);
        recyclerView.setAdapter(adapter);



        createList();

        btn_printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getSelected().size() == 0){
                    showToast("No Selection.");
                } else if(adapter.getSelected().size() > 4){
                    showToast("Select up to four photos.");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getName());
                        stringBuilder.append("\n");
                    }
                    showToast(stringBuilder.toString().trim());
                }
            }
        });

    }

    private void createList() {
        Intent intent = this.getIntent();
        patientid = intent.getStringExtra("patientid");
        int imagesize = databaseHelper.getImagePath(patientid).size();

        choiceimageItemrecyclers = new ArrayList<>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        for (int i=0; i<imagesize; i++){
            Bitmap thumbnail = BitmapFactory.decodeFile((String) databaseHelper.getImagePath(patientid).get(i), options);
            ChoiceimageItemrecycler choiceimageItemrecycler = new ChoiceimageItemrecycler();
            choiceimageItemrecycler.setName((String) databaseHelper.getImagePath(patientid).get(i));
            choiceimageItemrecycler.setEyeimage(thumbnail);
            choiceimageItemrecyclers.add(choiceimageItemrecycler);
        }
        adapter.setChoiceimageItemrecyclers(choiceimageItemrecyclers);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
