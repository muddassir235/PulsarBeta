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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the work and hobbie database.
 */
public class WorkContract {
	//To Prevent Someone from accidentally instantiating the contract 
	// giving it an empty contructor

    public static final int IS_A_JOB=0;
    public static final int IS_A_HOBBIE=1;

    public static final int IS_INCOMPLETE=0;
    public static final int IS_COMPLETE=1;
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.muddassir.android.pulsarbeta";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //this path will be added to the base uri, so that we can get to the work table in the SQLite Database
    public static final String PATH_WORK = "work";


    //Inner Class that defines the contest of the Work DataBase
    public static final class WorkEntry implements BaseColumns {

     //the path needed to get to the work table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK).build();

     //type of a set of tuples from the database
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORK;

     //type of one tuple from the database
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORK;

        // Table name
        public static final String TABLE_NAME = "work";

        //name of the task
        public static final String COLUMN_NAME_OF_TASK = "name_of_task";

        //hours per week to be given to that task
        public static final String COLUMN_HOURS_PER_THIS_WEEK = "hours_per_this_week";

        //picture for motivation
        public static final String COLUMN_TASK_PICTURE = "task_picture";

        //Start Week
        public static final String COLUMN_START_WEEK="start_week";
        
        //Current Week 
        public static final String COLUMN_CURR_WEEK="curr_week";
        
        //End Week 
        public static final String COLUMN_END_WEEK="end_week";
        
        //Total Time Required
        public static final String COLUMN_TOTAL_TIME_REQUIRED="time_required";
        
        //time given
        public static final String COLUMN_TOTAL_TIME_GIVEN="time_given";

        //time so far given to the task in the current week
        public static final String COLUMN_TIME_GIVEN_THIS_WEEK = "time_this_week";

        //whether the task has been completed or not
        public static final String COLUMN_COMPLETED="completed";

        //whether it is a hobbie or a job
        public static final String COLUMN_JOB_OR_HOBBIE="job_or_hobbie";

        //returns uri for the work table
        public static Uri buildWorkUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //name of task is returned
        public static String getNameOfWorkFromUri(Uri uri) {
            String nameOfTask=uri.getLastPathSegment();
            return nameOfTask;
        }

        //making a specific uri
        public static Uri buildWorkName(String Name) {
            return CONTENT_URI.buildUpon().appendPath(Name).build();
        }
    }
}
