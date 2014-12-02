package com.serd.cashregister.rest.sync.PLU;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

import com.serd.cashregister.db.DBHelper;
import com.serd.cashregister.providers.PluProvider;
import com.serd.cashregister.rest.data.PLU;
import com.serd.cashregister.rest.data.PLUs;
import com.serd.cashregister.rest.generics.GetTemplate;
import com.serd.cashregister.rest.sync.TaskSettings;
import com.serd.cashregister.rest.sync.ISynchronizerTask;
import com.serd.cashregister.rest.sync.Synchronizer;

import java.util.Iterator;

/**
 * Created by Tomas on 19. 11. 2014.
 */
public class GetPLUTask extends ISynchronizerTask implements GetTemplate.Listener<PLUs> {
    public static final String TAG = "GetPLUTask";
    Synchronizer mSynchronizer;

    public static void prepare(Synchronizer pSynchronizer)
    {
        pSynchronizer.addTask(new GetPLUTask(pSynchronizer));
    }

    GetPLUTask(Synchronizer pSynchronizer) {
        mSynchronizer = pSynchronizer;
        mTaskObject = new GetTemplate<PLUs>(PLUs.class, TaskSettings.baseurl + "Plu/");
        mTaskObject.registerListener(this);
        mTaskObject.registerErrorHandler(pSynchronizer);
    }

    public GetTemplate<PLUs> mTaskObject;
    public void execute() {mTaskObject.execute();}

    public void onGetResponse(PLUs plus)
    {
        Iterator<PLU> iterator = plus.item.iterator();
        while (iterator.hasNext()) {

            PLU plu = iterator.next();
            int id = mSynchronizer.getDBHelper().Guid2Id(plu.getId(), DBHelper.TABLE_NAME_PLUGROUP);
            ContentValues values = new ContentValues();
            values.put("NAME1", plu.getName1());
            values.put("NAME2", plu.getName2());
            values.put("NAME3", plu.getName3());
            values.put("PRICE1", plu.getPrice1());
            values.put("PRICE2", plu.getPrice2());
            values.put("PRICE3", plu.getPrice3());
            values.put("PLUGROUP_ID", plu.getPLUGroupId());
            //values.put("VAT_ID", plu.getVatId());
            values.put("EAN", plu.getEan());
            values.put("NOTE", plu.getNote());
            values.put("STATUS1", plu.getStatus1());
            values.put("STATUS2", plu.getStatus2());
            values.put("HALOLALO", plu.getHalolalo());
            values.put("LINKPLU1_ID", plu.getLinkPlu1Id());
            values.put("LINKPLU1QUANTITY", plu.getLinkPlu1Quantity());
            values.put("LINKPLU2_ID", plu.getLinkPlu2Id());
            values.put("LINKPLU2QUANTITY", plu.getLinkPlu2Quantity());
            values.put("COEFFICIENT", plu.getCoefficient());
            values.put("ORDERTYPE", plu.getOrderType());
            values.put("MENUVOL", plu.getMenuVol());
            values.put("MENUPAT", plu.getMenuPat());
            values.put("HAPPYHOUR", plu.getHappyHour());
            values.put("PICTOGRAM_ID", plu.getPictogramId());
            values.put("STATE", plu.getState());
            values.put("BLOCKED", plu.getBlocked());
            values.put("SOURCE_ID", plu.getSourceId());
            values.put("SOURCENUMSTOCK", plu.getSourceNumStock());
            values.put("SOURCESTOCK", plu.getSourceStock());
            values.put("SERVERTIMESTAMP", plu.getTimestamp().replace('T', ' '));
            values.put("CLIENTTIMESTAMP", plu.getTimestamp().replace('T', ' '));

            values.put("DELETED", 0);

            if (id < 0) {
                //item does not exist yet
                values.put("ID", plu.getId());
                mSynchronizer.getContext().getContentResolver().insert(PluProvider.CONTENT_ID_URI_LOCATION, values);
            }
            else {
                //item already exists
                Uri noteUri = ContentUris.withAppendedId(PluProvider.CONTENT_ID_URI_LOCATION, id);
                mSynchronizer.getContext().getContentResolver().update(noteUri, values, null, null);
            }
        }
        mSynchronizer.processQueue();
    }

}
