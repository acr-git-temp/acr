package com.serd.cashregister.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by Tomas on 15. 11. 2014.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Defines the database name
    public static final String DBNAME = "serd";

    public static final String TABLE_NAME_PLUMAINGROUP = "PluMainGroup";
    public static final String TABLE_NAME_PLUGROUP = "PluGroup";
    public static final String TABLE_NAME_PLU = "Plu";

    // A string that defines the SQL statement for creating a table
    private static final String SQL_CREATE_PLUMAINGROUP = "CREATE TABLE " +
            TABLE_NAME_PLUMAINGROUP +
            "( " +
            "'_id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID TEXT, " +
            "TITLE TEXT, " +
            "SERVERTIMESTAMP TEXT," +
            "CLIENTTIMESTAMP TEXT," +
            "DELETED NUMBER" +
            " )";

    private static final String SQL_CREATE_PLUGROUP = "CREATE TABLE " +
            TABLE_NAME_PLUGROUP +
            "( " +
            "'_id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID TEXT, " +
            "PLUMAINGROUP_ID TEXT, " +
            "TITLE TEXT, " +
            "SERVERTIMESTAMP TEXT," +
            "CLIENTTIMESTAMP TEXT," +
            "DELETED NUMBER" +
            " )";

    private static final String SQL_CREATE_PLU = "CREATE TABLE " +
            TABLE_NAME_PLU +
            "( " +
            "'_id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID TEXT, " +
            "PLUGROUP_ID TEXT, " +
            "DPH_ID TEXT" +
            "ZS_ID TEXT" +
            "EAN INTEGER" +
            "TITLE1 TEXT, " +
            "TITLE2 TEXT, " +
            "TITLE3 TEXT, " +
            "PRICE1 REAL, " +
            "PRICE2 REAL, " +
            "PRICE3 REAL, " +
            "NOTE TEXT, " +
            "STATUS1 INTEGER, " +
            "STATUS2 INTEGER, " +
            "HALO_LALO INTEGER, " +
            "LINK_PLU1_ID TEXT, " +
            "LINK_PLU1_COUNT INTEGER, " +
            "LINK_PLU2_ID TEXT, " +
            "LINK_PLU2_COUNT INTEGER, " +
            "COEFFICIENT REAL, " +
            "ORDER_TYPE INTEGER, " +
            "MENU_VOL INTEGER, " +
            "MENU_PAT INTEGER, " +
            "HAPPY_HOUR INTEGER, " +
            "PICTURE_ID TEXT, " +
            "STATE INTEGER, " +
            "BLOCKING INTEGER, " +
            "SOURCE_ID TEXT, " +
            "SOURCE_NUMBER INTEGER, " +
            "SOURCE_WAREHOUSE INTEGER, " +
            "SERVERTIMESTAMP TEXT," +
            "CLIENTTIMESTAMP TEXT," +
            "DELETED NUMBER" +
            " )";

    /*
     * Instantiates an open helper for the provider's SQLite data repository
     * Do not do database creation and upgrade here.
     */
    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    /*
     * Creates the data repository. This is called when the provider attempts to open the
     * repository and SQLite reports that it doesn't exist.
     */
    public void onCreate(SQLiteDatabase db) {

        // Creates the main table
        db.execSQL(SQL_CREATE_PLUMAINGROUP);
        db.execSQL(SQL_CREATE_PLUGROUP);
        db.execSQL(SQL_CREATE_PLU);

        //Nejsem schopen rozlisit update ze serveru (beze zmeny timestamp) a z klienta (nutna zmena timestamp)
        /*db.execSQL("CREATE TRIGGER TR_" + TABLE_NAME_PLUMAINGROUP + "_AU " +
                " AFTER UPDATE " +
                " ON " + TABLE_NAME_PLUMAINGROUP + " " +
                " WHEN NEW.TITLE <> OLD.TITLE OR NEW.DELETED <> OLD.DELETED" +
                " WHEN NEW.CLIENTTIMESTAMP IS NULL " +
                " BEGIN " +
                " UPDATE " + TABLE_NAME_PLUMAINGROUP + " SET CLIENTTIMESTAMP = datetime('now','localtime') " +
                " WHERE _id = NEW._id; " +
                " END;");*/

        db.execSQL("CREATE TRIGGER TR_" + TABLE_NAME_PLUMAINGROUP + "_AI_CLIENTTIME " +
                " AFTER INSERT " +
                " ON " + TABLE_NAME_PLUMAINGROUP + " " +
                " WHEN NEW.CLIENTTIMESTAMP IS NULL " +
                " BEGIN " +
                " UPDATE " + TABLE_NAME_PLUMAINGROUP + " SET CLIENTTIMESTAMP = datetime('now','localtime') WHERE _id = NEW._id;  " +
                " END;");

        db.execSQL("CREATE TRIGGER TR_" + TABLE_NAME_PLUMAINGROUP + "_AI_SERVERTIME " +
                " AFTER INSERT " +
                " ON " + TABLE_NAME_PLUMAINGROUP + " " +
                " WHEN NEW.SERVERTIMESTAMP IS NULL " +
                " BEGIN " +
                " UPDATE " + TABLE_NAME_PLUMAINGROUP + " SET SERVERTIMESTAMP = '1900-01-01 00:00:00' WHERE _id = NEW._id; " +
                " END;");

        db.execSQL("CREATE TRIGGER TR_" + TABLE_NAME_PLUGROUP + "_AI_CLIENTTIME " +
                " AFTER INSERT " +
                " ON " + TABLE_NAME_PLUGROUP + " " +
                " WHEN NEW.CLIENTTIMESTAMP IS NULL " +
                " BEGIN " +
                " UPDATE " + TABLE_NAME_PLUGROUP + " SET CLIENTTIMESTAMP = datetime('now','localtime') WHERE _id = NEW._id;  " +
                " END;");

        db.execSQL("CREATE TRIGGER TR_" + TABLE_NAME_PLUGROUP + "_AI_SERVERTIME " +
                " AFTER INSERT " +
                " ON " + TABLE_NAME_PLUGROUP + " " +
                " WHEN NEW.SERVERTIMESTAMP IS NULL " +
                " BEGIN " +
                " UPDATE " + TABLE_NAME_PLUGROUP + " SET SERVERTIMESTAMP = '1900-01-01 00:00:00' WHERE _id = NEW._id; " +
                " END;");

        db.execSQL("CREATE TRIGGER TR_" + TABLE_NAME_PLU + "_AI_CLIENTTIME " +
                " AFTER INSERT " +
                " ON " + TABLE_NAME_PLU + " " +
                " WHEN NEW.CLIENTTIMESTAMP IS NULL " +
                " BEGIN " +
                " UPDATE " + TABLE_NAME_PLU + " SET CLIENTTIMESTAMP = datetime('now','localtime') WHERE _id = NEW._id;  " +
                " END;");

        db.execSQL("CREATE TRIGGER TR_" + TABLE_NAME_PLU + "_AI_SERVERTIME " +
                " AFTER INSERT " +
                " ON " + TABLE_NAME_PLU + " " +
                " WHEN NEW.SERVERTIMESTAMP IS NULL " +
                " BEGIN " +
                " UPDATE " + TABLE_NAME_PLU + " SET SERVERTIMESTAMP = '1900-01-01 00:00:00' WHERE _id = NEW._id; " +
                " END;");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// Kills the table and existing data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLUMAINGROUP);
        onCreate(db);
    }

    public void AddTestingData() {
        SQLiteDatabase db = getWritableDatabase();
    }

    public int Guid2Id(String guid, String tableName) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        qb.appendWhere("ID = ");
        qb.appendWhereEscapeString(guid);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = qb.query(db, null, null, null, null, null, null);

        // Tells the Cursor what URI to watch, so it knows when its source data changes
        if (c.getCount() != 1) {
            return -1;
        }
        c.moveToFirst();
        return c.getInt(0);
    }
}

