package com.example.miis200;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPatient extends AppCompatActivity {

    EditText etPatientname,etPatientid,etPatientnumber;
    RadioButton rbMan,rbWomen;
    Button btnAdd;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    TextView Patientbirthday;
    String Age;
    Button Cancel;
    DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("新增客戶");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================



        etPatientname = (EditText) findViewById(R.id.etPatientname);
        etPatientid = (EditText) findViewById(R.id.etPatientid);
        etPatientnumber = (EditText) findViewById(R.id.etPatientphonenumber);
        Patientbirthday = (TextView) findViewById( R.id.etPatientbirthday);
        Patientbirthday.setClickable(true);

        rbMan = (RadioButton) findViewById(R.id.radioMan);
        rbWomen = (RadioButton) findViewById(R.id.radioWomen);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        Cancel = (Button) findViewById(R.id.cancel);

        databaseHelper = new DatabaseHelper(this);


        Patientbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closekeyboard();
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // DatePickerDialog  Max Day is today
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(year, month, dayOfMonth);
                // DatePickerDialog  Min Day -150 year
                Calendar minDate = Calendar.getInstance();
                minDate.set(year - 150, month, dayOfMonth);
                datePickerDialog = new DatePickerDialog(AddPatient.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy");
                        int NowYear = Integer.parseInt(format.format(new Date()));
                        Age = ( (NowYear - year) + "歲");
                        Patientbirthday.setText( year + "年" + (month + 1) + "月" +dayOfMonth + "日" );
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                //  Set  Max、Min Day
                final DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMinDate(minDate.getTimeInMillis());
                datePicker.setMaxDate(maxDate.getTimeInMillis());
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Button_Click.isFastDoubleClick()) {

                    //Get Values From EditText
                    String patientname = etPatientname.getText().toString();
                    String patientid = etPatientid.getText().toString();
                    String patientphonenumber = etPatientnumber.getText().toString();
                    String patientgender=rbMan.isChecked()?"男":"女";
                    String patientbirthday = Patientbirthday.getText().toString();

                    if(!patientname.isEmpty() && !patientid.isEmpty() && !patientphonenumber.isEmpty() && !patientgender.isEmpty() && !patientbirthday.isEmpty()){
                        if(databaseHelper.insertpatientinfo(patientname,patientid,patientphonenumber,patientgender,patientbirthday,"0")){
                            //Display Toast Message
                            Toast.makeText(getApplicationContext(),"Data Inserted..",Toast.LENGTH_SHORT).show();
                            //Clear Edit
                            etPatientname.setText("");
                            etPatientid.setText("");
                            etPatientnumber.setText("");
                            Patientbirthday.setText("");
                        }
                    }
                }
            }
        });

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
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        finish();
    }

    private  void closekeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}