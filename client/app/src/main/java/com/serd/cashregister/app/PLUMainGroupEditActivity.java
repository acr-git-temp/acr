package com.serd.cashregister.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class PLUMainGroupEditActivity extends ActionBarActivity {

    private Cursor mCursor;
    EditText textViewName;
    CheckBox checkBoxDeleted;

    static int RESULTCODE_CANCEL = 0;
    static int RESULTCODE_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumaingroup_edit);

        textViewName = (EditText) findViewById(R.id.editTextName);
        checkBoxDeleted = (CheckBox) findViewById(R.id.checkBoxDeleted);

        if (getIntent().getAction().equals(Intent.ACTION_EDIT)) {
            mCursor = getContentResolver().query(getIntent().getData(), null ,null, null, null);
            mCursor.moveToFirst();
            textViewName.setText(mCursor.getString(mCursor.getColumnIndex("NAME")));
            checkBoxDeleted.setVisibility(View.VISIBLE);
            checkBoxDeleted.setChecked(mCursor.getInt(mCursor.getColumnIndex("DELETED")) != 0);
        } else if (getIntent().getAction().equals(Intent.ACTION_INSERT)) {
            checkBoxDeleted.setVisibility(View.INVISIBLE);
            checkBoxDeleted.setChecked(false);
        }
    }


    public void okButtonClicked(View theButton){
        ContentValues values = new ContentValues();
        values.put("NAME", textViewName.getText().toString());
        values.put("DELETED", checkBoxDeleted.isChecked()?1:0);

        if (getIntent().getAction().equals(Intent.ACTION_EDIT)) {
            getContentResolver().update(getIntent().getData(), values, null, null);
        } else if (getIntent().getAction().equals(Intent.ACTION_INSERT)) {
            getContentResolver().insert(getIntent().getData(), values);
        }
        setResult(RESULTCODE_OK);
        finish();
    }

    public void cancelButtonClicked(View theButton){
        setResult(RESULTCODE_CANCEL);
        finish();
    }
}
