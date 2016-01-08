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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manages a local database for weather data.
 */
public class WorkDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "work.db";

    public WorkDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creating the Work Table
        //has one _ID column and six other columns for various functions of the app
        final String SQL_CREATE_WORK_TABLE = "CREATE TABLE " + WorkContract.WorkEntry.TABLE_NAME + " (" +
                WorkContract.WorkEntry._ID + " INTEGER PRIMARY KEY," +
                WorkContract.WorkEntry.COLUMN_NAME_OF_TASK + " TEXT UNIQUE NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_TASK_PICTURE + " TEXT NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_TIME_GIVEN_THIS_WEEK + " REAL NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_TOTAL_TIME_GIVEN + " INTEGER NOT NULL, " +
                WorkContract.WorkEntry.COLUMN_HOURS_PER_THIS_WEEK+" INTEGER NOT NULL, "+
                WorkContract.WorkEntry.COLUMN_TOTAL_TIME_REQUIRED+" INTEGER NOT NULL, "+
                WorkContract.WorkEntry.COLUMN_START_WEEK+" TEXT NOT NULL, "+
                WorkContract.WorkEntry.COLUMN_CURR_WEEK+" TEXT NOT NULL, "+
                WorkContract.WorkEntry.COLUMN_END_WEEK+" TEXT NOT NULL, "+
                WorkContract.WorkEntry.COLUMN_COMPLETED+" INTEGER NOT NULL, "+
                WorkContract.WorkEntry.COLUMN_JOB_OR_HOBBIE+" INTEGER NOT NULL "+
                " );";


        sqLiteDatabase.execSQL(SQL_CREATE_WORK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkContract.WorkEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
