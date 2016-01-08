package com.muddassir.android.pulsarbeta.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class PulsarUtils {
	
	 PulsarUtils(){
		 
	 }
	 //use this int to start this week
	 public static int START_THIS_WEEK=0;
	 
	 //use this int to start next week
	 public static int START_NEXT_WEEK=1;
	 
	 //Get Calendar from Formatted String
     public static Calendar getCalendar(String Date){
    	 DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
    	 Date date=null;
    	 try {
			date=dateFormat.parse(Date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("String was not converted to Date");
			e.printStackTrace();
		}
    	 GregorianCalendar calendar=new GregorianCalendar();
    	 calendar.setTime(date);
    	 Calendar returnCalendar=calendar;
    	 return returnCalendar;
     }
     
     //Getting formatted String from Calendar
     public static String getFormattedString(Calendar calendar)
     {
    	 DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
    	 Date date=calendar.getTime();
    	 return dateFormat.format(date);
    	 
     }
     
     //Getting Current week from Date
     public static String getWeek(Calendar calendar) throws Exception
     {
    	 DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
    	 String returnString;
    	 switch(calendar.get(Calendar.DAY_OF_WEEK)){
    	 case Calendar.SUNDAY:{
    		 Date date=calendar.getTime();
    		 return dateFormat.format(date);
    		 
    	 }
    	 case Calendar.MONDAY:{
    		 calendar.add(Calendar.DAY_OF_MONTH, -1);
    		 Date date=calendar.getTime();
    		 return dateFormat.format(date);
    		 
    	 }
    	 case Calendar.TUESDAY:{
    		 calendar.add(Calendar.DAY_OF_MONTH, -2);
    		 Date date=calendar.getTime();
    		 return dateFormat.format(date);
    		 
    	 }
    	 case Calendar.WEDNESDAY:{
    		 calendar.add(Calendar.DAY_OF_MONTH, -3);
    		 Date date=calendar.getTime();
    		 return dateFormat.format(date);
    		 
    	 }
    	 case Calendar.THURSDAY:{
    		 calendar.add(Calendar.DAY_OF_MONTH, -4);
    		 Date date=calendar.getTime();
    		 return dateFormat.format(date);
    		 
    	 }
    	 case Calendar.FRIDAY:{
    		 calendar.add(Calendar.DAY_OF_MONTH, -5);
    		 Date date=calendar.getTime();
    		 return dateFormat.format(date);
    		 
    	 }
    	 case Calendar.SATURDAY:{
    		 calendar.add(Calendar.DAY_OF_MONTH, -6);
    		 Date date=calendar.getTime();
    		 return dateFormat.format(date);
    		 
    	 }
    	 default:{
    		 throw new Exception("Current week could not be determined");
    	 }
    	 }
     }
     
     //Use this function to determine whether to ask user 
     public static boolean determineAskUserForStart(Calendar calendar){
    	 if(calendar.get(Calendar.DAY_OF_WEEK)>=Calendar.WEDNESDAY){
    		 return true;
    	 }else{
    		 return false;
    	 }
     }
     //give it in its paramenters(The total Number of Hours required for task,
     //number of hours given, and weeks left) and it will tell you the 
     //Number of Hours to give this week
     public static int getHoursForThisWeek(int numberOfHoursRequired,int numberOfHoursGiven,int weeksLeft){
    	 int numberOfHoursToGive=numberOfHoursRequired-numberOfHoursGiven;
    	 if( ((numberOfHoursToGive)%weeksLeft)==0)
    	 {
    		 return numberOfHoursToGive/weeksLeft;
    	 }
    	 else
    	 {
    		 return numberOfHoursToGive/weeksLeft+1;
    	 }
    	 
     }
     
     
     //Use this Function to get the start week 
     public static String getStartWeek(int WHEN_TO_START,Calendar calendar) throws Exception
     {
    	 if(WHEN_TO_START==START_THIS_WEEK)
    	 {
    		 try {
				return getWeek(calendar);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Exception("Unable to determine week from Calendar object");
			}
    	 }
    	 else if(WHEN_TO_START==START_NEXT_WEEK)
    	 {
    		 switch(calendar.get(Calendar.DAY_OF_WEEK)){
    		 case Calendar.WEDNESDAY:{
    			 calendar.add(Calendar.DAY_OF_MONTH, 4);
    			 return getFormattedString(calendar);
    		 }
    		 case Calendar.THURSDAY:{
    			 calendar.add(Calendar.DAY_OF_MONTH, 3);
    			 return getFormattedString(calendar);
    		 }
    		 case Calendar.FRIDAY:{
    			 calendar.add(Calendar.DAY_OF_MONTH, 2);
    			 return getFormattedString(calendar);
    		 }
    		 case Calendar.SATURDAY:{
    			 calendar.add(Calendar.DAY_OF_MONTH, 1);
    			 return getFormattedString(calendar);
    		 }
    		 default:{
    			 throw new Exception("unable to determine next week");
    		 }
    		 }
    	 }
    	 else{
    		 throw new Exception("Start Week Was not determined check the WHEN_TO_START paramenter");
    	 }
     }
     
     //use this to determine the end week from the Start week, hours per week and total number of weeks
     public static String getEndWeek(String startWeek,int numberOfWeeks){
    	 Calendar calendar=getCalendar(startWeek);
    	 calendar.add(Calendar.DAY_OF_MONTH, numberOfWeeks*7);
    	 return getFormattedString(calendar);
     }
     
     public static String getCurrentWeek() throws Exception{
    	 Calendar calendar=new GregorianCalendar();
    	 try {
			return getWeek(calendar);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Unable to determine this week");
			
		}
     }





    //use this to get Content Values for a new Work
    public static ContentValues getNewContentValuesWork(int jobOrHobbie,String nameOfWork,String startWeek,String pathOfPicture,int hoursPerWeek,int numberOfWeeks) throws Exception {
        ContentValues values = new ContentValues();
        Log.v("Muddassir","THis is the start week gotten"+startWeek);
        if(jobOrHobbie==WorkContract.IS_A_JOB) {
            int timeToGive = hoursPerWeek * numberOfWeeks;
            String currentWeek;
            try {
                currentWeek = getCurrentWeek();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("unable to determine Current Week");
            }
            String endWeek = getEndWeek(startWeek, numberOfWeeks);
            Log.v("Muddassir","THis is the end week gotten"+endWeek);
            values.put(WorkContract.WorkEntry.COLUMN_NAME_OF_TASK, nameOfWork);
            values.put(WorkContract.WorkEntry.COLUMN_TASK_PICTURE, pathOfPicture);
            values.put(WorkContract.WorkEntry.COLUMN_START_WEEK, startWeek);
            values.put(WorkContract.WorkEntry.COLUMN_CURR_WEEK, currentWeek);
            values.put(WorkContract.WorkEntry.COLUMN_END_WEEK, endWeek);
            values.put(WorkContract.WorkEntry.COLUMN_TOTAL_TIME_REQUIRED, timeToGive);
            values.put(WorkContract.WorkEntry.COLUMN_TOTAL_TIME_GIVEN, 0);
            values.put(WorkContract.WorkEntry.COLUMN_TIME_GIVEN_THIS_WEEK, 0);
            values.put(WorkContract.WorkEntry.COLUMN_HOURS_PER_THIS_WEEK, hoursPerWeek);
            values.put(WorkContract.WorkEntry.COLUMN_COMPLETED, WorkContract.IS_INCOMPLETE);
            values.put(WorkContract.WorkEntry.COLUMN_JOB_OR_HOBBIE, WorkContract.IS_A_JOB);
            Log.v("Muddassir","VAlues: "+values.getAsString(WorkContract.WorkEntry.COLUMN_START_WEEK));
        }else{

            String currentWeek = getCurrentWeek();
            values.put(WorkContract.WorkEntry.COLUMN_NAME_OF_TASK, nameOfWork);
            values.put(WorkContract.WorkEntry.COLUMN_TASK_PICTURE, pathOfPicture);
            values.put(WorkContract.WorkEntry.COLUMN_START_WEEK, "not needed");
            values.put(WorkContract.WorkEntry.COLUMN_CURR_WEEK, currentWeek);
            values.put(WorkContract.WorkEntry.COLUMN_END_WEEK, "not needed");
            values.put(WorkContract.WorkEntry.COLUMN_TOTAL_TIME_REQUIRED, 0);
            values.put(WorkContract.WorkEntry.COLUMN_TOTAL_TIME_GIVEN, 0);
            values.put(WorkContract.WorkEntry.COLUMN_TIME_GIVEN_THIS_WEEK, 0);
            values.put(WorkContract.WorkEntry.COLUMN_HOURS_PER_THIS_WEEK, hoursPerWeek);
            values.put(WorkContract.WorkEntry.COLUMN_COMPLETED, WorkContract.IS_INCOMPLETE);
            values.put(WorkContract.WorkEntry.COLUMN_JOB_OR_HOBBIE, WorkContract.IS_A_HOBBIE);
            Log.v("Muddassir","VAlues: "+values.getAsString(WorkContract.WorkEntry.COLUMN_START_WEEK));
        }
        return values;
    }

    //use this function to add a new Hobbie to the database


    //use this function to add a new Work to the Table
    public  static void addNewWork(int jobOrHobbie,String nameOfWork,String startWeek,String pathOfPicture,int hoursPerWeek,int numberOfWeeks,Context context) throws Exception {
        ContentValues values;
        try {
            values=getNewContentValuesWork(jobOrHobbie,nameOfWork,startWeek,pathOfPicture,hoursPerWeek,numberOfWeeks);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("WORK ADD FAILED","Failed to add new work because content values weren't formed");
            throw new Exception("Failed to add new work because content values weren't formed");
        }
        context.getContentResolver().insert(WorkContract.WorkEntry.CONTENT_URI,values);
    }

    //change picture path
    public static void changePicPath(Uri uri,String nameOfTask,String newPicPath,Context context)
    {
        //Add projections and populate update values and pass it to the update Function
        ContentValues updatedValues=new ContentValues();
        if(context.getContentResolver().getType(uri)== WorkContract.WorkEntry.CONTENT_TYPE) {
            String nameOfWork = nameOfTask;
            updatedValues.put(WorkContract.WorkEntry.COLUMN_TASK_PICTURE, newPicPath);
            context.getContentResolver().update(
                    uri,
                    updatedValues,
                    WorkContract.WorkEntry.COLUMN_NAME_OF_TASK+"=?",
                    new String[]{nameOfWork}
            );

        }
    }
}
