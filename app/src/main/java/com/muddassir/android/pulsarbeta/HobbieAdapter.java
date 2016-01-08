package com.muddassir.android.pulsarbeta;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * {@link com.muddassir.android.pulsarbeta.HobbieAdapter} exposes a list of hobbies
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class HobbieAdapter extends CursorAdapter {
    public HobbieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    //This adapter will be used to make a list view of Hobbies from the data base

    private final int VIEW_TYPE_HOBBIE=1;
    private final int VIEW_TYPE_WORK=0;
    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getItemViewType(int workOrJob){
        return (workOrJob== 0)?VIEW_TYPE_WORK:VIEW_TYPE_HOBBIE;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view;
        int viewType=getItemViewType(cursor.getPosition());
        int layoutId=R.layout.task_list_item;
       view= LayoutInflater.from(context).inflate(layoutId,parent,false);
       ViewHolder viewHolder=new ViewHolder(view);
       view.setTag(viewHolder);
       return view;

    }


    /* 
        This is where we fill-in the views with the contents of the cursor. 
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view 
        // we'll keep the UI functional with a simple (and slow!) binding. 
        ViewHolder viewHolder=(ViewHolder) view.getTag();
        String name=cursor.getString(PulsarMain.PulsarMainFragment.COLUMN_NAME);
        String picPath=cursor.getString(PulsarMain.PulsarMainFragment.COLUMN_PIC_PATH);
        int numberOfHours=cursor.getInt(PulsarMain.PulsarMainFragment.COLUMN_HOURS_PER_WEEK);
        double timeGivenThisWeek=cursor.getDouble(PulsarMain.PulsarMainFragment.COLUMN_TIME_GIVEN_THIS_WEEK);
        int totalTimeGiven=cursor.getInt(PulsarMain.PulsarMainFragment.COLUMN_TOTAL_TIME_GIVEN);
        int jobOrHobbie=cursor.getInt(PulsarMain.PulsarMainFragment.COLUMN_JOB_OR_HOBBIE);
        int totalTimeRequired=cursor.getInt(PulsarMain.PulsarMainFragment.COLUMN_TOTAL_TIME_REQUIRED);
        String startWeek=cursor.getString(PulsarMain.PulsarMainFragment.COLUMN_START_WEEK);
        Log.v("Muddassir","Start Week String"+startWeek);
        String currentWeek=cursor.getString(PulsarMain.PulsarMainFragment.COLUMN_CURRENT_WEEK);
        String endWeek=cursor.getString(PulsarMain.PulsarMainFragment.COLUMN_END_WEEK);
        int isComplete=cursor.getInt(PulsarMain.PulsarMainFragment.COLUMN_IS_COMPLETED);
        String HobbieText=
                jobOrHobbie+" - "+
                name+" - "+
                picPath+" - "+
                numberOfHours+" - "+
                timeGivenThisWeek+" - "+
                totalTimeGiven+" - "+
                totalTimeRequired+" - "+
                startWeek+" - "+
                currentWeek+" - "+
                endWeek+" - "+
                isComplete;
        viewHolder.tv.setText(HobbieText);

//        TextView tv = (TextView)view;
//      tv.setText(convertCursorRowToUXFormat(cursor));
    }
    public static class ViewHolder{
        public final TextView tv;
        public ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.item_text_work);
        }
    }
} 