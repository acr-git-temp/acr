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
            "NAME TEXT, " +
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
            "NAME TEXT, " +
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
            "VAT_ID TEXT, " +
            "DEPARTMENT_ID TEXT, " +
            "EAN INTEGER, " +
            "NAME1 TEXT, " +
            "NAME2 TEXT, " +
            "NAME3 TEXT, " +
            "PRICE1 REAL, " +
            "PRICE2 REAL, " +
            "PRICE3 REAL, " +
            "NOTE TEXT, " +
            "STATUS1 INTEGER, " +
            "STATUS2 INTEGER, " +
            "HALOLALO INTEGER, " +
            "LINKPLU1_ID TEXT, " +
            "LINKPLU1QUANTITY INTEGER, " +
            "LINKPLU2_ID TEXT, " +
            "LINKPLU2QUANTITY INTEGER, " +
            "COEFFICIENT REAL, " +
            "ORDERTYPE INTEGER, " +
            "MENUVOL INTEGER, " +
            "MENUPAT INTEGER, " +
            "HAPPYHOUR INTEGER, " +
            "PICTOGRAM_ID TEXT, " +
            "STATE INTEGER, " +
            "BLOCKED INTEGER, " +
            "SOURCE_ID TEXT, " +
            "SOURCENUMSTOCK INTEGER, " +
            "SOURCESTOCK INTEGER, " +
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

        AddTestingData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// Kills the table and existing data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLUMAINGROUP);
        onCreate(db);
    }

    public void AddTestingData(SQLiteDatabase db) {
        //Pridej radky do tabulku PLUMAINGROUP
        db.execSQL("INSERT INTO " + TABLE_NAME_PLUMAINGROUP + "(ID, NAME, SERVERTIMESTAMP, CLIENTTIMESTAMP, DELETED) " +
                "VALUES ('7f8294e2-ed08-46b8-8273-fa0c06ed175d', 'Jidlo'   , '01.01.1900 00:00', '01.01.1900 00:00', 0)");
        db.execSQL("INSERT INTO " + TABLE_NAME_PLUMAINGROUP + "(ID, NAME, SERVERTIMESTAMP, CLIENTTIMESTAMP, DELETED) " +
                "VALUES ('2d534d40-403d-4234-b92e-a11ce853426d', 'Napoje'  , '01.01.1900 00:00', '01.01.1900 00:00', 0)");

        //Pridej radky do tabulku PLUGROUP
        db.execSQL("INSERT INTO " + TABLE_NAME_PLUGROUP + "(ID, PLUMAINGROUP_ID, NAME, SERVERTIMESTAMP, CLIENTTIMESTAMP, DELETED) " +
                "VALUES ('f2112f50-3268-4fe0-afe9-407f761eea23', '2d534d40-403d-4234-b92e-a11ce853426d', 'Nealko'  , '01.01.1900 00:00', '01.01.1900 00:00', 0)");
        db.execSQL("INSERT INTO " + TABLE_NAME_PLUGROUP + "(ID, PLUMAINGROUP_ID, NAME, SERVERTIMESTAMP, CLIENTTIMESTAMP, DELETED) " +
                "VALUES ('b51d2c0d-43fb-4efd-92cd-8a78fc2f8886', '2d534d40-403d-4234-b92e-a11ce853426d', 'Alkohol'  , '01.01.1900 00:00', '01.01.1900 00:00', 0)");

        //Pridej radky do tabulku PLU
        db.execSQL("INSERT INTO " + TABLE_NAME_PLU + "(ID, PLUGROUP_ID, EAN, NAME1, PRICE1, SERVERTIMESTAMP, CLIENTTIMESTAMP, DELETED) " +
                "VALUES ('1dc3dcaf-30bf-4384-8b66-31dce7d65ced', 'f2112f50-3268-4fe0-afe9-407f761eea23', 100, 'CocaCola', 25 , '01.01.1900 00:00', '01.01.1900 00:00', 0)");
        db.execSQL("INSERT INTO " + TABLE_NAME_PLU + "(ID, PLUGROUP_ID, EAN, NAME1, PRICE1, SERVERTIMESTAMP, CLIENTTIMESTAMP, DELETED) " +
                "VALUES ('50689cb4-9b82-4aeb-b222-3cf767d15df4', 'f2112f50-3268-4fe0-afe9-407f761eea23', 101, 'Fanta', 26 , '01.01.1900 00:00', '01.01.1900 00:00', 0)");
        db.execSQL("INSERT INTO " + TABLE_NAME_PLU + "(ID, PLUGROUP_ID, EAN, NAME1, PRICE1, SERVERTIMESTAMP, CLIENTTIMESTAMP, DELETED) " +
                "VALUES ('73f156fc-eac5-49f6-ae55-538ead49869f', 'f2112f50-3268-4fe0-afe9-407f761eea23', 102, 'Sprite', 27 , '01.01.1900 00:00', '01.01.1900 00:00', 0)");

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

