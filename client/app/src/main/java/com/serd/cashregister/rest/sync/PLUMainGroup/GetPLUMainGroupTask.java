package com.serd.cashregister.rest.sync.PLUMainGroup;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

import com.serd.cashregister.db.DBHelper;
import com.serd.cashregister.providers.PluMainGroupProvider;
import com.serd.cashregister.rest.data.PLUMainGroup;
import com.serd.cashregister.rest.data.PLUMainGroups;
import com.serd.cashregister.rest.sync.ISynchronizerTask;
import com.serd.cashregister.rest.sync.Synchronizer;
import com.serd.cashregister.rest.generics.GetTemplate;
import com.serd.cashregister.rest.sync.TaskSettings;

import java.util.Iterator;

/**
 * Created by Tomas on 19. 11. 2014.
 */
public class GetPLUMainGroupTask extends ISynchronizerTask implements GetTemplate.Listener<PLUMainGroups> {
    public static final String TAG = "GetPLUMainGroupTask";
    Synchronizer mSynchronizer;

    public static void prepare(Synchronizer pSynchronizer)
    {
        pSynchronizer.addTask(new GetPLUMainGroupTask(pSynchronizer));
    }

    GetPLUMainGroupTask(Synchronizer pSynchronizer) {
        mSynchronizer = pSynchronizer;
        mTaskObject = new GetTemplate<PLUMainGroups>(PLUMainGroups.class, TaskSettings.baseurl + "PluMainGroup/");
        mTaskObject.registerListener(this);
        mTaskObject.registerErrorHandler(pSynchronizer);
    }

    public GetTemplate<PLUMainGroups> mTaskObject;
    public void execute() {mTaskObject.execute();}

    public void onGetResponse(PLUMainGroups pluMainGroups)
    {
        Iterator<PLUMainGroup> iterator = pluMainGroups.item.iterator();
        while (iterator.hasNext()) {

            PLUMainGroup pluMainGroup = iterator.next();
            int id = mSynchronizer.getDBHelper().Guid2Id(pluMainGroup.getId(), DBHelper.TABLE_NAME_PLUMAINGROUP);
            ContentValues values = new ContentValues();
            if (id < 0) {
                //item does not exist yet
                values.put("ID", pluMainGroup.getId());
                values.put("NAME", pluMainGroup.getContent());
                values.put("SERVERTIMESTAMP", pluMainGroup.getTimestamp().replace('T', ' '));
                values.put("CLIENTTIMESTAMP", pluMainGroup.getTimestamp().replace('T', ' '));
                values.put("DELETED", 0);
                mSynchronizer.getContext().getContentResolver().insert(PluMainGroupProvider.CONTENT_ID_URI_LOCATION, values);
            }
            else {
                //item already exists
                values.put("NAME", pluMainGroup.getContent());
                values.put("SERVERTIMESTAMP", pluMainGroup.getTimestamp().replace('T', ' '));
                values.put("CLIENTTIMESTAMP", pluMainGroup.getTimestamp().replace('T', ' '));
                values.put("DELETED", pluMainGroup.getDeleted()?1:0);
                Uri noteUri = ContentUris.withAppendedId(PluMainGroupProvider.CONTENT_ID_URI_LOCATION, id);
                mSynchronizer.getContext().getContentResolver().update(noteUri, values, null, null);
            }
        }
        mSynchronizer.processQueue();
    }

}
