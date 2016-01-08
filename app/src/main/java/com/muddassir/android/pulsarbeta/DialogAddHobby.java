package com.muddassir.android.pulsarbeta;

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


public class DialogAddHobby extends ActionBarActivity {

    EditText nameET;
    EditText hoursPerWeekET;
    EditText pathOfPicET;
    Button addHobbieBTN;
    Button cancelDialogBTN;

    public static final String[] HOBBIE_COLUMNS={WorkContract.WorkEntry.COLUMN_NAME_OF_TASK};
    public static final int NAME_OF_TASK=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_hobby);

        bindViews();



        addHobbieBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameET.getText().toString();
                Uri uri=WorkContract.WorkEntry.buildWorkName(name);
                Cursor hobbieCursor =getContentResolver().query(
                        uri,
                        HOBBIE_COLUMNS,
                        null,
                        null,
                        null
                );
                if(hobbieCursor.moveToFirst())
                {
                    Log.v("Muddassir","The Name alredy exists");
                    Toast.makeText(getApplicationContext(), "This hobbie already exists!!", Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    String hoursPerWeekString = hoursPerWeekET.getText().toString();
                    int hoursPerWeek = Integer.valueOf(hoursPerWeekString);
                    String pathOfPicture = pathOfPicET.getText().toString();
                    ContentValues values = null;
                    try {
                        values = PulsarUtils.getNewContentValuesWork(WorkContract.IS_A_HOBBIE, name, "not needed", pathOfPicture, hoursPerWeek, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getContentResolver().insert(WorkContract.WorkEntry.CONTENT_URI, values);
                    Log.v("Muddassir", "add to database sussesful");
                    finish();
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


    public void bindViews(){
    //binding Views
        nameET=(EditText) findViewById(R.id.name_of_new_hobbie);
        hoursPerWeekET=(EditText) findViewById(R.id.hours_per_week);
        pathOfPicET=(EditText) findViewById(R.id.path_for_pic_of_new_hobbie);
        addHobbieBTN=(Button) findViewById(R.id.hobbie_dialog_add_button);
        cancelDialogBTN=(Button) findViewById(R.id.hobbie_dialog_cancel_button);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_add_hobby, menu);
        return true;
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
}
