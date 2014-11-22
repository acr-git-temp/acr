package com.serd.cashregister.app.utils;

import android.database.Cursor;

/**
 * Created by Tomas on 22. 11. 2014.
 */
public class UserInterfaceUtilities {

    public static int Guid2Idx(Cursor mCursor, String guidToBeFound) {
        mCursor.moveToFirst();
        int idx = 0;
        do {
            String guid = mCursor.getString(mCursor.getColumnIndex("ID"));
            if (guid.equals(guidToBeFound)) {
                return idx;
            }
            idx++;
        }
        while (mCursor.moveToNext());
        return -1;
    }

}
