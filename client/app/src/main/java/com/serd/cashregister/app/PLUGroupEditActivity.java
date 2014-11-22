package com.serd.cashregister.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.serd.cashregister.app.utils.UserInterfaceUtilities;
import com.serd.cashregister.providers.PluMainGroupProvider;

public class PLUGroupEditActivity extends ActionBarActivity {

    private Cursor mCursor;
    private EditText textViewName;
    private CheckBox checkBoxDeleted;
    private Spinner spinnerMainGroup;
    private Cursor cursorPluMainGroups;

    //RESULTCODE zatim neni potreba ale treba v budoucnu...
    static int RESULTCODE_CANCEL = 0;
    static int RESULTCODE_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugroup_edit);

        //Ziskej objekty pro jednotlive prvky UI
        textViewName = (EditText) findViewById(R.id.editTextName);
        checkBoxDeleted = (CheckBox) findViewById(R.id.checkBoxDeleted);
        spinnerMainGroup = (Spinner) findViewById(R.id.spinnerMainGroup);

        //...a dale budeme potrebovat kurzor na PluMainGroup
        String[] projectionPluMainGroup = {"_id", "ID", "NAME"};
        cursorPluMainGroups = getContentResolver().query(PluMainGroupProvider.CONTENT_ID_URI_LOCATION, projectionPluMainGroup, null, null, null);

        //Vytvorime a nastavime cursorAdapter tak aby zobrazoval PluMainGroup.NAME
        String[] from = {"NAME"};
        int[] to = new int[] { android.R.id.text1 };
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, cursorPluMainGroups, from, to);
        cursorAdapter.setStringConversionColumn(cursorPluMainGroups.getColumnIndex("NAME"));
        spinnerMainGroup.setAdapter(cursorAdapter);

        if (getIntent().getAction().equals(Intent.ACTION_EDIT)) {
            //Pokud polozku editujeme

            //ziskame z intentu (url) data
            mCursor = getContentResolver().query(getIntent().getData(), null ,null, null, null);
            mCursor.moveToFirst();

            //a nastavime vsem trem prvkum spravnou hodnotu
            textViewName.setText(mCursor.getString(mCursor.getColumnIndex("NAME")));
            checkBoxDeleted.setVisibility(View.VISIBLE);
            checkBoxDeleted.setChecked(mCursor.getInt(mCursor.getColumnIndex("DELETED")) != 0);
            String guidToBeFound = mCursor.getString(mCursor.getColumnIndex("PLUMAINGROUP_ID"));
            spinnerMainGroup.setSelection(UserInterfaceUtilities.Guid2Idx(cursorPluMainGroups, guidToBeFound));

        } else if (getIntent().getAction().equals(Intent.ACTION_INSERT)) {
            //pri insert nemusime delat nic
            //checkBoxDeleted.setVisibility(View.INVISIBLE);
            //checkBoxDeleted.setChecked(false);
        }
    }


    public void okButtonClicked(View theButton){

        //Vytvorime objekt jimz budeme predavat hodnoty
        ContentValues values = new ContentValues();

        //a nastavime hodnoty podle obsahu jednotlivych UI prvku
        values.put("NAME", textViewName.getText().toString());
        values.put("PLUMAINGROUP_ID", cursorPluMainGroups.getString(cursorPluMainGroups.getColumnIndex("ID")));
        values.put("DELETED", checkBoxDeleted.isChecked()?1:0);

        //a data odesleme na content provider
        if (getIntent().getAction().equals(Intent.ACTION_EDIT)) {
            getContentResolver().update(getIntent().getData(), values, null, null);
        }
        else if (getIntent().getAction().equals(Intent.ACTION_INSERT)) {
            getContentResolver().insert(getIntent().getData(), values);
        }

        //ulozime result code, a ukoncime dialog
        setResult(RESULTCODE_OK);
        finish();
    }

    public void cancelButtonClicked(View theButton){
        //ulozime result code, a ukoncime dialog
        setResult(RESULTCODE_CANCEL);
        finish();
    }
}
