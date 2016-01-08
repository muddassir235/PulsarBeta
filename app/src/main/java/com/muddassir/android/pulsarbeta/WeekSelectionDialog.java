package com.muddassir.android.pulsarbeta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by admin on 3/23/2015.
 */
public class WeekSelectionDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Calendar calendar=new GregorianCalendar();
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
        String day = null;
        switch(dayOfWeek)
        {
            case 0:day="Sunday";break;
            case 1:day="Monday";break;
            case 2:day="Tuesday";break;
            case 3:day="Wednesday";break;
            case 4:day="Thursday";break;
            case 5:day="Friday";break;
            case 6:day="Saturday";break;
            default:
                Log.v("Muddassir","foo");
        }
        if(day=="Wednesday") {
            builder.setMessage("Today is " + day + ", half the week has past, when do you want to start?");
        }
        else if(day=="Thursday")
        {
            builder.setMessage("Today is " + day + ", more than half the week has past, when do you want to start?");
        }
        else{
            builder.setMessage("Today is " + day + ", this week is about to end, when do you want to start?");
        }
        builder.setTitle("When to start?");
        builder.setPositiveButton("This Week", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        mListener.onDialogPositiveClick(WeekSelectionDialog.this);
                    }
                })
                .setNegativeButton("Next Week", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        mListener.onDialogNegativeClick(WeekSelectionDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public interface SelectWeekDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    SelectWeekDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SelectWeekDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
