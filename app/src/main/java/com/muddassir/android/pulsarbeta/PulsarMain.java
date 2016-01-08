package com.muddassir.android.pulsarbeta;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.muddassir.android.pulsarbeta.data.WorkContract;


public class PulsarMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsar_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PulsarMainFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pulsar_main, menu);
        return false;
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

    /**
     * THE PULSAR MAIN FRAGMENT!!!!!!!!!!!!!!!!!!!!!
     */
    public class PulsarMainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

        public static final int HOBBIE_LOADER=0;


        public final String[] COLUMNS={
                WorkContract.WorkEntry.TABLE_NAME+"."+ WorkContract.WorkEntry._ID,
                WorkContract.WorkEntry.COLUMN_JOB_OR_HOBBIE,
                WorkContract.WorkEntry.COLUMN_NAME_OF_TASK,
                WorkContract.WorkEntry.COLUMN_TASK_PICTURE,
                WorkContract.WorkEntry.COLUMN_START_WEEK,
                WorkContract.WorkEntry.COLUMN_CURR_WEEK,
                WorkContract.WorkEntry.COLUMN_END_WEEK,
                WorkContract.WorkEntry.COLUMN_HOURS_PER_THIS_WEEK,
                WorkContract.WorkEntry.COLUMN_TOTAL_TIME_REQUIRED,
                WorkContract.WorkEntry.COLUMN_TOTAL_TIME_GIVEN,
                WorkContract.WorkEntry.COLUMN_TIME_GIVEN_THIS_WEEK,
                WorkContract.WorkEntry.COLUMN_COMPLETED

        } ;

        public static final int COLUMN_ID=0;
        public static final int COLUMN_JOB_OR_HOBBIE=1;
        public static final int COLUMN_NAME=2;
        public static final int COLUMN_PIC_PATH=3;
        public static final int COLUMN_START_WEEK=4;
        public static final int COLUMN_CURRENT_WEEK=5;
        public static final int COLUMN_END_WEEK=6;
        public static final int COLUMN_HOURS_PER_WEEK=7;
        public static final int COLUMN_TOTAL_TIME_REQUIRED=8;
        public static final int COLUMN_TOTAL_TIME_GIVEN=9;
        public static final int COLUMN_TIME_GIVEN_THIS_WEEK=10;
        public static final int COLUMN_IS_COMPLETED=11;

        //This is my Cursor adapter for Hobbies
        private HobbieAdapter myHobbieAdapter;
        Button addWorkButton;
        Button addHobbieButton;
        ListView workListView;
        public PulsarMainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pulsar_main, container, false);


                addHobbieButton=(Button) rootView.findViewById(R.id.add_new_hobbie);
                addWorkButton=(Button) rootView.findViewById(R.id.add_new_work);
                workListView=(ListView) rootView.findViewById(R.id.listview_all_tasks);

            myHobbieAdapter=new HobbieAdapter(getActivity(),null,0);

            workListView.setAdapter(myHobbieAdapter);
            addHobbieButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),DialogAddHobby.class);
                    startActivity(intent);
                }
            });
            addWorkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),DialogAddWork.class);
                    startActivity(intent);
                }
            });

            return rootView;
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getSupportLoaderManager().initLoader(HOBBIE_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getApplicationContext(),
                    WorkContract.WorkEntry.CONTENT_URI,
                    COLUMNS,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            myHobbieAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            myHobbieAdapter.swapCursor(null);
        }
    }
}
