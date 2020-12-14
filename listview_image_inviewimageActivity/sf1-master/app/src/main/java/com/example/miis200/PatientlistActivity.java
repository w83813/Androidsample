package com.example.miis200;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PatientlistActivity extends AppCompatActivity {

    private static final String TAG = "PatientActivity";

    private Workflow mWorkflow=Workflow.NOT_SET;
    public static String WORKFLOW="Workflow";

    DatabaseHelper databaseHelper;
    ArrayList<User> patientlist;
    ListView patientlistview;
    User user;
    Cursor data;
    Intent mExaminationIntent,mWaitviewimageIntent,mWaitprinterIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);

        mExaminationIntent = new Intent(this,Addexamination_examination.class);
        mWaitviewimageIntent = new Intent(this, WaitviewimageActivity.class);

        handleIntent(getIntent());

        databaseHelper = new DatabaseHelper(this);
        patientlist = new ArrayList<>();

        switch (mWorkflow) {
            case ADDEXAMINATION:
                data = databaseHelper.getListContents0();
                break;
            case WAITVIEWIMAGE:
                data = databaseHelper.getListContents1();
                break;
            case WAITPRINTER:
                data = databaseHelper.getListContents2();
                break;
        }


        int numRows = data.getCount();


        if(numRows == 0){
            Toast.makeText(PatientlistActivity.this,R.string.Addex_not_client,Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                user = new User(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5));
                patientlist.add(i,user);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4)+" "+data.getString(5));
                i++;
            }
        }
        ThreeColumn_ListAdapter adapter =  new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, patientlist);
        patientlistview = (ListView) findViewById(R.id.patientlistview);
        patientlistview.setAdapter(adapter);
        patientlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Button_Click.isFastDoubleClick()) {
                    Log.i("ZZZZZZZZZZZZZZZZZ",String.valueOf(position));
                    switch (mWorkflow){
                        case ADDEXAMINATION:
                            mExaminationIntent.putExtra("patientname",patientlist.get(position).getPatientName());
                            mExaminationIntent.putExtra("patientid",patientlist.get(position).getPatientID());
                            mExaminationIntent.putExtra("patientbir",patientlist.get(position).getPatientBir());
                            mExaminationIntent.putExtra("patientgender",patientlist.get(position).getPatientGender());
                            mExaminationIntent.putExtra("patientphonenumber",patientlist.get(position).getPatientPnum());
                            startActivity(mExaminationIntent);
                            break;
                        case WAITVIEWIMAGE:
                            mWaitviewimageIntent.putExtra("patientname",patientlist.get(position).getPatientName());
                            mWaitviewimageIntent.putExtra("patientid",patientlist.get(position).getPatientID());
                            mWaitviewimageIntent.putExtra("patientbir",patientlist.get(position).getPatientBir());
                            mWaitviewimageIntent.putExtra("patientgender",patientlist.get(position).getPatientGender());
                            mWaitviewimageIntent.putExtra("patientphonenumber",patientlist.get(position).getPatientPnum());
                            startActivity(mWaitviewimageIntent);
                            break;
                        case WAITPRINTER:
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        handleIntent(getIntent());
    }

    private void setWorkflow(Workflow workflow)
    {
        mWorkflow=workflow;
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent()");
        setIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.i(TAG, "handleIntent()");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if(extras.containsKey(WORKFLOW)) {
                setWorkflow((Workflow) extras.get(WORKFLOW));
            }
        }
    }
}
