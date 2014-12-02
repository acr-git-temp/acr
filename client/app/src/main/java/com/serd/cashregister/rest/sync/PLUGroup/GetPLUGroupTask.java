package com.serd.cashregister.rest.sync.PLUGroup;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

import com.serd.cashregister.db.DBHelper;
import com.serd.cashregister.providers.PluGroupProvider;
import com.serd.cashregister.rest.data.PLUGroup;
import com.serd.cashregister.rest.data.PLUGroups;
import com.serd.cashregister.rest.sync.ISynchronizerTask;
import com.serd.cashregister.rest.sync.Synchronizer;
import com.serd.cashregister.rest.generics.GetTemplate;
import com.serd.cashregister.rest.sync.TaskSettings;

import java.util.Iterator;

/**
 * Created by Tomas on 19. 11. 2014.
 */
public class GetPLUGroupTask extends ISynchronizerTask implements GetTemplate.Listener<PLUGroups> {
    public static final String TAG = "GetPLUGroupTask";
    Synchronizer mSynchronizer;

    public static void prepare(Synchronizer pSynchronizer)
    {
        pSynchronizer.addTask(new GetPLUGroupTask(pSynchronizer));
    }

    GetPLUGroupTask(Synchronizer pSynchronizer) {
        mSynchronizer = pSynchronizer;
        mTaskObject = new GetTemplate<PLUGroups>(PLUGroups.class, TaskSettings.baseurl + "PluGroup/");
        mTaskObject.registerListener(this);
        mTaskObject.registerErrorHandler(pSynchronizer);
    }

    public GetTemplate<PLUGroups> mTaskObject;
    public void execute() {mTaskObject.execute();}

    public void onGetResponse(PLUGroups pluGroups)
    {
        Iterator<PLUGroup> iterator = pluGroups.item.iterator();
        while (iterator.hasNext()) {

            PLUGroup pluGroup = iterator.next();
            int id = mSynchronizer.getDBHelper().Guid2Id(pluGroup.getId(), DBHelper.TABLE_NAME_PLUGROUP);
            ContentValues values = new ContentValues();
            if (id < 0) {
                //item does not exist yet
                values.put("ID", pluGroup.getId());
                values.put("NAME", pluGroup.getContent());
                values.put("SERVERTIMESTAMP", pluGroup.getTimestamp().replace('T', ' '));
                values.put("CLIENTTIMESTAMP", pluGroup.getTimestamp().replace('T', ' '));
                values.put("PLUMAINGROUP_ID", pluGroup.getPLUMainGroupId());
                values.put("DELETED", 0);
                mSynchronizer.getContext().getContentResolver().insert(PluGroupProvider.CONTENT_ID_URI_LOCATION, values);
            }
            else {
                //item already exists
                values.put("NAME", pluGroup.getContent());
                values.put("SERVERTIMESTAMP", pluGroup.getTimestamp().replace('T', ' '));
                values.put("CLIENTTIMESTAMP", pluGroup.getTimestamp().replace('T', ' '));
                values.put("PLUMAINGROUP_ID", pluGroup.getPLUMainGroupId());
                values.put("DELETED", pluGroup.getDeleted()?1:0);
                Uri noteUri = ContentUris.withAppendedId(PluGroupProvider.CONTENT_ID_URI_LOCATION, id);
                mSynchronizer.getContext().getContentResolver().update(noteUri, values, null, null);
            }
        }
        mSynchronizer.processQueue();
    }

}
