/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muddassir.android.pulsarbeta.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;


/*
    Note: This is not a complete set of tests of the Sunshine ContentProvider, but it does test
    that at least the basic functionality has been implemented correctly.

    Students: Uncomment the tests in this class as you implement the functionality in your
    ContentProvider to make sure that you've implemented things reasonably correctly.
 */
public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();
//
//    /*
//       This helper function deletes all records from both database tables using the ContentProvider.
//       It also queries the ContentProvider to make sure that the database has been successfully
//       deleted, so it cannot be used until the Query and Delete functions have been written
//       in the ContentProvider.
//
//       Students: Replace the calls to deleteAllRecordsFromDB with this one after you have written
//       the delete functionality in the ContentProvider.
//     */
    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                WorkContract.WorkEntry.CONTENT_URI,
                null,
                null
        );


        Cursor cursor = mContext.getContentResolver().query(
                WorkContract.WorkEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Weather table during delete", 0, cursor.getCount());
        cursor.close();

    }
//
//    /*
//        Student: Refactor this function to use the deleteAllRecordsFromProvider functionality once
//        you have implemented delete functionality there.
//     */
    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }
//
//    /*
//        This test checks to make sure that the content provider is registered correctly.
//        Students: Uncomment this test to make sure you've correctly registered the WeatherProvider.
//     */
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                WorkProvider.class.getName());
        Log.v("Muddassir", "info authority " + mContext.getPackageName());
        Log.v("Muddassir","content authority "+WorkContract.CONTENT_AUTHORITY);
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);


            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: WProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + WorkContract.CONTENT_AUTHORITY,
                    providerInfo.authority, WorkContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WorkProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }
//
//    /*
//            This test doesn't touch the database.  It verifies that the ContentProvider returns
//            the correct type for each type of URI that it can handle.
//            Students: Uncomment this test to verify that your implementation of GetType is
//            functioning correctly.
//         */
    public void testGetType() {
        // content://com.example.android.sunshine.app/weather/
        String type = mContext.getContentResolver().getType(WorkContract.WorkEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the WeatherEntry CONTENT_URI should return WeatherEntry.CONTENT_TYPE",
                WorkContract.WorkEntry.CONTENT_TYPE, type);

        String testHobbieName = "Drawing";
        String testWorkName="Programming";
        // content://com.example.android.sunshine.app/weather/94074


         // December 21st, 2014
        // content://com.example.android.sunshine.app/weather/94074/20140612
        type = mContext.getContentResolver().getType(
                WorkContract.WorkEntry.buildWorkName(testWorkName));
        // vnd.android.cursor.item/com.example.android.sunshine.app/weather/1419120000
        assertEquals("Error: the WeatherEntry CONTENT_URI with location and date should return WeatherEntry.CONTENT_ITEM_TYPE",
                WorkContract.WorkEntry.CONTENT_ITEM_TYPE, type);

    }
//
//
//    /*
//        This test uses the database directly to insert and then uses the ContentProvider to
//        read out the data.  Uncomment this test to see if the basic weather query functionality
//        given in the ContentProvider is working correctly.
//     */

//
//    /*
//        This test uses the database directly to insert and then uses the ContentProvider to
//        read out the data.  Uncomment this test to see if your location queries are
//        performing correctly.
//     */

//
//    /*
//        This test uses the provider to insert and then update the data. Uncomment this test to
//        see if your update location is functioning correctly.
//     */

//
//    // Make sure we can still delete after adding/updating stuff
//    //
//    // Student: Uncomment this test after you have completed writing the insert functionality
//    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
//    // query functionality must also be complete before this test can be used.
    public void testInsertReadProvider() {





        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // Fantastic.  Now that we have a location, add some weather!
        ContentValues workValues = TestUtilities.createWorkValues();
        // The TestContentObserver is a one-shot class
        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();

        mContext.getContentResolver().registerContentObserver(WorkContract.WorkEntry.CONTENT_URI, true, tco);

        Uri weatherInsertUri = mContext.getContentResolver()
                .insert(WorkContract.WorkEntry.CONTENT_URI, workValues);
        assertTrue(weatherInsertUri != null);

        // Did our content observer get called?  Students:  If this fails, your insert weather
        // in your ContentProvider isn't calling
        // getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        // A cursor is your primary interface to the query results.
        Cursor weatherCursor = mContext.getContentResolver().query(
                WorkContract.WorkEntry.CONTENT_URI,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null // columns to group by
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating WeatherEntry insert.",
                weatherCursor, workValues);

    }
//
//    // Make sure we can still delete after adding/updating stuff
//    //
//    // Student: Uncomment this test after you have completed writing the delete functionality
//    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
//    // query functionality must also be complete before this test can be used.
    public void testDeleteRecords() {
        testInsertReadProvider();



        // Register a content observer for our weather delete.
        TestUtilities.TestContentObserver workObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(WorkContract.WorkEntry.CONTENT_URI, true, workObserver);

        deleteAllRecordsFromProvider();

        // Students: If either of these fail, you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider

        workObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(workObserver);
    }

//
    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;
    static ContentValues[] createBulkInsertWorkValues() {
        long currentTestDate = TestUtilities.TEST_DATE;
        long millisecondsInADay = 1000*60*60*24;
        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, currentTestDate+= millisecondsInADay ) {
            ContentValues weatherValues = new ContentValues();
            weatherValues.put(WorkContract.WorkEntry.COLUMN_NAME_OF_TASK, "Task"+Integer.toString(i));
            weatherValues.put(WorkContract.WorkEntry.COLUMN_TASK_PICTURE, "Picture"+Integer.toString(i));
            weatherValues.put(WorkContract.WorkEntry.COLUMN_START_WEEK, "StartWeek"+Integer.toString(i));
            weatherValues.put(WorkContract.WorkEntry.COLUMN_CURR_WEEK, "CurrWeek"+Integer.toString(i));
            weatherValues.put(WorkContract.WorkEntry.COLUMN_END_WEEK, "EndWeek"+Integer.toString(i));
            weatherValues.put(WorkContract.WorkEntry.COLUMN_TOTAL_TIME_REQUIRED, 2*i);
            weatherValues.put(WorkContract.WorkEntry.COLUMN_TOTAL_TIME_GIVEN, i);
            weatherValues.put(WorkContract.WorkEntry.COLUMN_HOURS_PER_THIS_WEEK, i);
            weatherValues.put(WorkContract.WorkEntry.COLUMN_TIME_GIVEN_THIS_WEEK, 5.5 + 0.2 * (float) i);
            weatherValues.put(WorkContract.WorkEntry.COLUMN_COMPLETED, i%2);
            weatherValues.put(WorkContract.WorkEntry.COLUMN_JOB_OR_HOBBIE,i%3);
            returnContentValues[i] = weatherValues;
        }
        return returnContentValues;
    }

    // Student: Uncomment this test after you have completed writing the BulkInsert functionality
    // in your provider.  Note that this test will work with the built-in (default) provider
    // implementation, which just inserts records one-at-a-time, so really do implement the
    // BulkInsert ContentProvider function.
    public void testBulkInsert() {
        // first, let's create a location value




        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.


        // Now we can bulkInsert some weather.  In fact, we only implement BulkInsert for weather
        // entries.  With ContentProviders, you really only have to implement the features you
        // use, after all.
        ContentValues[] bulkInsertContentValues = createBulkInsertWorkValues();

        // Register a content observer for our bulk insert.
        TestUtilities.TestContentObserver workObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(WorkContract.WorkEntry.CONTENT_URI, true, workObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(WorkContract.WorkEntry.CONTENT_URI, bulkInsertContentValues);

        // Students:  If this fails, it means that you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in your BulkInsert
        // ContentProvider method.
        workObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(workObserver);

        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                WorkContract.WorkEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                WorkContract.WorkEntry.COLUMN_START_WEEK + " ASC"  // sort order == by DATE ASCENDING
        );

        // we should have as many records in the database as we've inserted
        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext() ) {
            TestUtilities.validateCurrentRecord("testBulkInsert.  Error validating WeatherEntry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }

    //testing change path picture

    public void testChangePathPicture(){
        ContentValues workValues=TestUtilities.createWorkValues();
        Uri workUri= WorkContract.WorkEntry.CONTENT_URI;
        mContext.getContentResolver().insert(workUri,workValues);
        workValues.put(WorkContract.WorkEntry.COLUMN_TASK_PICTURE, "newpath");
        PulsarUtils.changePicPath(workUri,workValues.getAsString(WorkContract.WorkEntry.COLUMN_NAME_OF_TASK),"newpath",mContext);
        Cursor updatedCursor=mContext.getContentResolver().query(WorkContract.WorkEntry.CONTENT_URI,null,null,null,null);
        TestUtilities.validateCursor("test change picturepath. Error Validating pic path update,",
                updatedCursor,workValues);
        updatedCursor.close();

    }


}
