package com.example.miis200;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Review extends AppCompatActivity implements AdapterView.OnItemClickListener {



    TextView tv1,tv2;
    ListView listView;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Review_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_review);

        tv1 = (TextView) findViewById( R.id.tv1 );
        tv2 = (TextView) findViewById( R.id.tv2 );
        listView = (ListView) findViewById( R.id.datafile );

        fileMessage();


    }

    private void fileMessage(){
        File fileport = Environment.getExternalStorageDirectory();
        File file = new File( fileport,"1.MiiS/1.PDF");
        tv2.setText( R.string.Review_file_path + " : " + file );
        Log.d("Files", "Path: " + file);

        File[] files = file.listFiles();

        Log.d("Files", "Size: "+ files.length);
        tv1.setText( R.string.Review_total_file + " : " + files.length );


        ArrayList <String> fileNames = new ArrayList<String>();
        for (int i = files.length-1; i >=0 ; i--)
        {

            fileNames.add(files[i].getName());
            Log.d("Files", "FileName:" + files[i].getName());
        }



        listAdapter = new ArrayAdapter<String>(Review.this, android.R.layout.simple_list_item_1, fileNames);

        // Set the file list to a widget
        listView = (ListView)findViewById(R.id.datafile);
        listView.setDividerHeight(1);
        listView.setAdapter(listAdapter);
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemClickListener(Review.this);


    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Object viewFile = parent.getItemAtPosition(position);

        Toast.makeText(Review.this, R.string.Open + viewFile.toString() , Toast.LENGTH_LONG).show();


        File fileport = Environment.getExternalStorageDirectory();
        File file = new File( fileport,"1.MiiS/1.PDF/"+ viewFile);

        if (file.exists())
        {
            try
            {
                Intent intent=new Intent(Intent.ACTION_VIEW);

                Uri uri = FileProvider.getUriForFile(this, "com.example.miis200.fileprovider", file);
                Log.d("URL", "URL:" + uri);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(Review.this, R.string.Review_cant_view_PDF, Toast.LENGTH_LONG).show();
            }
        }

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
