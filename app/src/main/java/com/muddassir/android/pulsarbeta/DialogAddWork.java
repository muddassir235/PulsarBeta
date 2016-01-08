package com.muddassir.android.pulsarbeta;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muddassir.android.pulsarbeta.data.PulsarUtils;
import com.muddassir.android.pulsarbeta.data.WorkContract;


public class DialogAddWork extends ActionBarActivity implements WeekSelectionDialog.SelectWeekDialogListener {

    EditText nameET;
    EditText hoursPerWeekET;
    EditText numberOfWeeksET;
    EditText pathOfPicET;
    Button addWorkBTN;
    Button cancelDialogBTN;
    String startWeek;
    String nameOfWork;
    int hoursPerWeek;
    int numberOfWeeks;
    String pathOfPic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_work);

        bindViews();

        addWorkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameOfWork=nameET.getText().toString();
                Uri uri=WorkContract.WorkEntry.buildWorkName(nameOfWork);
                Cursor workCursor =getContentResolver().query(
                        uri,
                        null,
                        null,
                        null,
                        null
                );
                if(workCursor.moveToFirst())
                {
                    Log.v("Muddassir","The name alreay exists");
                    Toast.makeText(getApplicationContext(), "This job or may be course already exists!!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                hoursPerWeek=Integer.valueOf(hoursPerWeekET.getText().toString());
                numberOfWeeks=Integer.valueOf(numberOfWeeksET.getText().toString());
                pathOfPic=pathOfPicET.getText().toString();
                try {
                    boolean Ask= PulsarUtils.determineAskUserForStart(PulsarUtils.getCalendar(PulsarUtils.getCurrentWeek()));
                    if(!Ask)
                    {
                        startWeek=PulsarUtils.getCurrentWeek();
                        Log.v("Muddassir","This is the current week"+startWeek);
                        Log.v("Muddassir","Entered the dont ask scope");
                        ContentValues values=PulsarUtils.getNewContentValuesWork(WorkContract.IS_A_JOB,nameOfWork,startWeek,pathOfPic,hoursPerWeek,numberOfWeeks);
                        getContentResolver().insert(WorkContract.WorkEntry.CONTENT_URI,values);
                        Log.v("Muddassir","Added work to the database ");
                        Toast.makeText(getApplicationContext(),"Added Succesfully",Toast.LENGTH_SHORT);
                        finish();

                    }
                    else
                    {
                        showNoticeDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("Muddassir","Insertion failed");
                    finish();
                }

            }
            }
        });

        cancelDialogBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_add_work, menu);
        return true;
    }
    public void bindViews(){
        //binding Views
        nameET=(EditText) findViewById(R.id.name_of_new_work);
        hoursPerWeekET=(EditText) findViewById(R.id.hours_per_week_work);
        numberOfWeeksET=(EditText) findViewById(R.id.number_of_weeks_work);
        pathOfPicET=(EditText) findViewById(R.id.path_of_picture_work);
        addWorkBTN=(Button) findViewById(R.id.work_dialog_add_button);
        cancelDialogBTN=(Button) findViewById(R.id.work_dialog_cancel_button);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new WeekSelectionDialog();
        dialog.show(getFragmentManager(),"WorkSelectionDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        try {
            startWeek=PulsarUtils.getStartWeek(PulsarUtils.START_NEXT_WEEK,PulsarUtils.getCalendar(PulsarUtils.getCurrentWeek()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ContentValues values=PulsarUtils.getNewContentValuesWork(WorkContract.IS_A_JOB,nameOfWork,startWeek,pathOfPic,hoursPerWeek,numberOfWeeks);
            getContentResolver().insert(WorkContract.WorkEntry.CONTENT_URI, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Added Succesfully",Toast.LENGTH_SHORT);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        try {
            startWeek=PulsarUtils.getStartWeek(PulsarUtils.START_THIS_WEEK,PulsarUtils.getCalendar(PulsarUtils.getCurrentWeek()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ContentValues values=PulsarUtils.getNewContentValuesWork(WorkContract.IS_A_JOB,nameOfWork,startWeek,pathOfPic,hoursPerWeek,numberOfWeeks);
            getContentResolver().insert(WorkContract.WorkEntry.CONTENT_URI, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Added Succesfully",Toast.LENGTH_SHORT);
        finish();
    }
}
