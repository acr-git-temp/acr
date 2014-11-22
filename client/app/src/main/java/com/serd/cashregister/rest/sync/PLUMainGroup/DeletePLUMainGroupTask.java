package com.serd.cashregister.rest.sync.PLUMainGroup;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.serd.cashregister.db.DBHelper;
import com.serd.cashregister.providers.PluMainGroupProvider;
import com.serd.cashregister.rest.data.PLUMainGroup;
import com.serd.cashregister.rest.sync.ISynchronizerTask;
import com.serd.cashregister.rest.sync.Synchronizer;
import com.serd.cashregister.rest.generics.DeleteTemplate;
import com.serd.cashregister.rest.generics.TaskSettings;

public class DeletePLUMainGroupTask extends ISynchronizerTask implements DeleteTemplate.Listener<PLUMainGroup> {
    public static final String TAG = "DeletePLUMainGroupTask";

    Synchronizer mSynchronizer;

    private void prepare(Synchronizer  pSynchronizer){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DBHelper.TABLE_NAME_PLUMAINGROUP);
        qb.appendWhere("SERVERTIMESTAMP <> CLIENTTIMESTAMP AND DELETED = 1");
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

                PLUMainGroup grp = new PLUMainGroup(c.getString(1), c.getString(2), c.getString(4).replace(' ', 'T'),c.getString(5));
                pSynchronizer.addTask(new DeletePLUMainGroupTask(pSynchronizer, grp, grp.getId()));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    DeletePLUMainGroupTask(Synchronizer pSynchronizer, PLUMainGroup pData, String pId ) {
        mSynchronizer = pSynchronizer;
        mTaskObject = new DeleteTemplate<PLUMainGroup>(PLUMainGroup.class, TaskSettings.baseurl + "PluMainGroup/", pData, pId);
        mTaskObject.registerListener(this);
    }

    public DeleteTemplate<PLUMainGroup> mTaskObject;
    public void execute() {mTaskObject.execute();}

    public void onDeleteResponse(PLUMainGroup pluMainGroup)
    {
        int id =  mSynchronizer.getDBHelper().Guid2Id(pluMainGroup.getId(), DBHelper.TABLE_NAME_PLUMAINGROUP);
        if (id < 0) {
            //report an error
        }
        else {
            ContentValues values = new ContentValues();
            values.put("SERVERTIMESTAMP", pluMainGroup.getTimestamp().replace('T', ' '));
            Uri noteUri = ContentUris.withAppendedId(PluMainGroupProvider.CONTENT_ID_URI_LOCATION, id);
            mSynchronizer.getContext().getContentResolver().update(noteUri, values, null, null);
        }
        mSynchronizer.processQueue();
    }

}
