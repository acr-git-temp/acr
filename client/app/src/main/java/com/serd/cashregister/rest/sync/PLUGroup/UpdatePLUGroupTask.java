package com.serd.cashregister.rest.sync.PLUGroup;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.serd.cashregister.db.DBHelper;
import com.serd.cashregister.providers.PluGroupProvider;
import com.serd.cashregister.rest.data.PLUGroup;
import com.serd.cashregister.rest.sync.ISynchronizerTask;
import com.serd.cashregister.rest.sync.Synchronizer;
import com.serd.cashregister.rest.sync.TaskSettings;
import com.serd.cashregister.rest.generics.UpdateTemplate;

public class UpdatePLUGroupTask extends ISynchronizerTask implements UpdateTemplate.Listener<PLUGroup> {
    public static final String TAG = "UpdatePLUGroupTask";
    Synchronizer mSynchronizer;

    public static void prepare(Synchronizer pSynchronizer){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DBHelper.TABLE_NAME_PLUGROUP);
        qb.appendWhere("SERVERTIMESTAMP <> CLIENTTIMESTAMP AND SERVERTIMESTAMP <> '1900-01-01 00:00:00'");
        SQLiteDatabase db = pSynchronizer.getDBHelper().getReadableDatabase();
        Cursor c = qb.query(db, null, null, null, null, null, null);

        // Tells the Cursor what URI to watch, so it knows when its source data changes
        int items = c.getCount();
        Log.d(TAG, "Items: " + items);

        if (c.moveToFirst())
        {
            do
            {
                for (Integer i=0;i<c.getColumnCount();i++)
                {
                    Log.d(TAG, "Column[" + i.toString() + "]: " + c.getString(i));
                }

                PLUGroup grp = new PLUGroup(c.getString(1), c.getString(2), c.getString(3), c.getString(5).replace(' ', 'T'),c.getString(6));
                pSynchronizer.addTask(new UpdatePLUGroupTask(pSynchronizer, grp));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    UpdatePLUGroupTask(Synchronizer pSynchronizer, PLUGroup pData ) {
        mSynchronizer = pSynchronizer;
        mTaskObject = new UpdateTemplate<PLUGroup>(PLUGroup.class, TaskSettings.baseurl + "PluGroup/", pData);
        mTaskObject.registerListener(this);
        mTaskObject.registerErrorHandler(pSynchronizer);
    }

    public UpdateTemplate<PLUGroup> mTaskObject;
    public void execute() {mTaskObject.execute();}

    public void onUpdateResponse(PLUGroup pluGroup)
    {
        int id =  mSynchronizer.getDBHelper().Guid2Id(pluGroup.getId(), DBHelper.TABLE_NAME_PLUGROUP);
        if (id < 0) {
            //report an error
        }
        else {
            ContentValues values = new ContentValues();
            values.put("SERVERTIMESTAMP", pluGroup.getTimestamp().replace('T', ' '));
            values.put("CLIENTTIMESTAMP", pluGroup.getTimestamp().replace('T', ' '));
            Uri noteUri = ContentUris.withAppendedId(PluGroupProvider.CONTENT_ID_URI_LOCATION, id);
            mSynchronizer.getContext().getContentResolver().update(noteUri, values, null, null);
        }
        mSynchronizer.processQueue();
    }

}
