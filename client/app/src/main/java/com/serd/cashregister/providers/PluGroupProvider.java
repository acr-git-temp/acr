package com.serd.cashregister.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.serd.cashregister.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

public class PluGroupProvider extends ContentProvider {

    public static final String TAG = "PluGroupProvider";
    public static final String AUTHORITY = "com.serd.cashregister.PluGroupProvider";

    // The incoming URI matches specific URI pattern
    private static final int PluGroups = 1;
    private static final int PluGroup_id = 2;

    public static final int _ID_PATH_POSITION = 1;
    /**
     * The table name offered by this provider
     */
    public static final Uri CONTENT_ID_URI_LOCATION
            = Uri.parse("content://" + AUTHORITY + "/PluGroups/");

    /*
     * MIME type definitions
     */

    public static final String PluGroups_TYPE = "vnd.android.cursor.dir/vnd.google.plugroup";
    public static final String PluGroups_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.plugroup";


    /**
     * A UriMatcher instance
     */
    private static final UriMatcher sUriMatcher;

    private static HashMap<String, String> sPluGroupProjectionMap;

    /*
     * Defines a handle to the database helper object. The MainDatabaseHelper class is defined
     * in a following snippet.
     */

    DBHelper mOpenHelper;

    // Defines the database name


    public PluGroupProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;
        switch (sUriMatcher.match(uri)) {

            case PluGroups:
                throw new IllegalArgumentException("Bad argument");

            case PluGroup_id:
                SQLiteDatabase db = mOpenHelper.getWritableDatabase();
                String where = "_id = " + uri.getPathSegments().get(_ID_PATH_POSITION);
                rowsDeleted = db.delete(DBHelper.TABLE_NAME_PLUGROUP, where, null);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        /**
         * Chooses the MIME type based on the incoming URI pattern
         */
        switch (sUriMatcher.match(uri)) {

            // If the pattern is for notes or live folders, returns the general content type.
            case PluGroups:
                return PluGroups_TYPE;

            // If the pattern is for note IDs, returns the note ID content type.
            case PluGroup_id:
                return PluGroups_ITEM_TYPE;

            // If the URI pattern doesn't match any permitted patterns, throws an exception.
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {

        // Validates the incoming URI. Only the full provider URI is allowed for inserts.
        if (sUriMatcher.match(uri) == PluGroups) {

            // A map to hold the new record's values.
            ContentValues values;

            // If the incoming values map is not null, uses it for the new values.
            if (initialValues == null) {
                values = new ContentValues();
                values.put("NAME", "");
                //throw new IllegalArgumentException("No initial values for a new record in " + uri);
            } else {
                values = new ContentValues(initialValues);
            }
            if (!values.containsKey("ID"))
                values.put("ID", UUID.randomUUID().toString());

            // Opens the database object in "write" mode.
            SQLiteDatabase db = mOpenHelper.getWritableDatabase();

            // Performs the insert and returns the ID of the new note.
            long rowId = db.insert(DBHelper.TABLE_NAME_PLUGROUP, null, values);
            //db.close();

            // If the insert succeeded, the row ID exists.
            if (rowId > 0) {
                // Creates a URI with the note ID pattern and the new row ID appended to it.
                Uri noteUri = ContentUris.withAppendedId(CONTENT_ID_URI_LOCATION, rowId);

                // Notifies observers registered against this provider that the data changed.
                getContext().getContentResolver().notifyChange(noteUri, null);
                return noteUri;
            }


            // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
            throw new SQLException("Failed to insert row into " + uri);
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        /*
         * Creates a new helper object. This method always returns quickly.
         * Notice that the database itself isn't created or opened
         * until SQLiteOpenHelper.getWritableDatabase is called
         */
        mOpenHelper = new DBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
            // If the incoming URI is for notes, chooses the Notes projection
            case PluGroups:
                qb.setTables(DBHelper.TABLE_NAME_PLUGROUP);
                qb.setProjectionMap(sPluGroupProjectionMap);
                //qb.appendWhere("DELETED <> 1");
                break;

            /* If the incoming URI is for a single note identified by its ID, chooses the
             * note ID projection, and appends "_ID = <noteID>" to the where clause, so that
             * it selects that single note
             */
            case PluGroup_id:
            {
                qb.setTables(DBHelper.TABLE_NAME_PLUGROUP);
                qb.setProjectionMap(sPluGroupProjectionMap);
                String s = "_id = " + uri.getPathSegments().get(_ID_PATH_POSITION);
                qb.appendWhere(s);
            }
            break;

            default:
                // If the URI doesn't match any of the known patterns, throw an exception.
                throw new IllegalArgumentException("Unknown URI " + uri);
        }


        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // Tells the Cursor what URI to watch, so it knows when its source data changes
        int items = c.getCount();
        Log.d(TAG, "Items: " + items);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        String finalWhere;

        // Does the update based on the incoming URI pattern
        switch (sUriMatcher.match(uri)) {

            // If the incoming URI matches the general notes pattern, does the update based on
            // the incoming data.
            case PluGroups:

                // Does the update and returns the number of rows updated.
                count = db.update(
                        DBHelper.TABLE_NAME_PLUGROUP, // The database table name.
                        values,                   // A map of column names and new values to use.
                        where,                    // The where clause column names.
                        whereArgs                 // The where clause column values to select on.
                );
                break;

            // If the incoming URI matches a single note ID, does the update based on the incoming
            // data, but modifies the where clause to restrict it to the particular note ID.
            case PluGroup_id:
                // From the incoming URI, get the note ID
                String noteId = uri.getPathSegments().get(_ID_PATH_POSITION);

                /*
                 * Starts creating the final WHERE clause by restricting it to the incoming
                 * note ID.
                 */
                finalWhere = "_id = " + noteId;

                // If there were additional selection criteria, append them to the final WHERE
                // clause
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }

                if (!values.containsKey("CLIENTTIMESTAMP")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    values.put("CLIENTTIMESTAMP", sdf.format(new java.util.Date()));
                }

                // Does the update and returns the number of rows updated.
                count = db.update(DBHelper.TABLE_NAME_PLUGROUP, values, finalWhere, whereArgs);
                break;


            // If the incoming pattern is invalid, throws an exception.
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        /*Gets a handle to the content resolver object for the current context, and notifies it
         * that the incoming URI changed. The object passes this along to the resolver framework,
         * and observers that have registered themselves for the provider are notified.
         */
        getContext().getContentResolver().notifyChange(uri, null);
        //db.close();
        // Returns the number of rows updated.
        return count;
    }

    /**
     * A block that instantiates and sets static objects
     */
    static {

        /*
         * Creates and initializes the URI matcher
         */
        // Create a new instance
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add a pattern that routes URIs
        sUriMatcher.addURI(AUTHORITY, "PluGroups", PluGroups);
        sUriMatcher.addURI(AUTHORITY, "PluGroups/#", PluGroup_id);

        sPluGroupProjectionMap = new HashMap<String, String>();
        sPluGroupProjectionMap.put("_id", "_id");
        sPluGroupProjectionMap.put("ID", "ID");
        sPluGroupProjectionMap.put("PLUMAINGROUP_ID", "PLUMAINGROUP_ID");
        sPluGroupProjectionMap.put("TITLE", "TITLE");
        sPluGroupProjectionMap.put("SERVERTIMESTAMP", "SERVERTIMESTAMP");
        sPluGroupProjectionMap.put("CLIENTTIMESTAMP", "CLIENTTIMESTAMP");
        sPluGroupProjectionMap.put("DELETED", "DELETED");
    }
}

