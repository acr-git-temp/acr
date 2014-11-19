package com.serd.cashregister.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class PLUGroupEditActivity extends ActionBarActivity {

    private Uri mUri;
    private int mState;
    private Cursor mCursor;

    EditText title;
    EditText mainGroup;
    CheckBox checkBoxDeleted;

    static int REQUEST_CODE = 0;
    static int RESULTCODE_CANCEL = 0;
    static int RESULTCODE_OK = 1;

    private static final int STATE_EDIT = 0;
    private static final int STATE_INSERT = 1;

    private static final String[] PROJECTION = new String[] { ".id", "ID", "TITLE", "PLUMAINGROUP_ID", "DELETED" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugroup_edit);

        title = (EditText) findViewById(R.id.editTextLocationTitle);
        mainGroup = (EditText) findViewById(R.id.editTextMainGroup);
        checkBoxDeleted = (CheckBox) findViewById(R.id.checkBoxDeleted);

        final Intent intent = getIntent();
        final String action = intent.getAction();

        if (Intent.ACTION_EDIT.equals(action)) {
            mState = STATE_EDIT;
            mUri = intent.getData();
            mCursor = getContentResolver().query(mUri,null ,null, null, null);
            mCursor.moveToFirst();
            title.setText(mCursor.getString(6));
            mainGroup.setText(mCursor.getString(3));
            checkBoxDeleted.setVisibility(View.VISIBLE);
            checkBoxDeleted.setChecked(mCursor.getInt(2) == 1);
        } else if (Intent.ACTION_INSERT.equals(action)) {
            checkBoxDeleted.setVisibility(View.INVISIBLE);
            checkBoxDeleted.setChecked(false);
            mState = STATE_INSERT;
            mUri = intent.getData();
        }

    }


    public void okButtonClicked(View theButton){
        ContentValues values = new ContentValues();
        values.put("TITLE", title.getText().toString());
        values.put("PLUMAINGROUP_ID", mainGroup.getText().toString());
        values.put("DELETED", checkBoxDeleted.isChecked()?1:0);

        if (mState==STATE_EDIT) {
            getContentResolver().update(mUri, values, null, null);
            finish();
        }
        else if (mState==STATE_INSERT) {
            getContentResolver().insert(mUri, values);
            finish();
        }
    }

    public void cancelButtonClicked(View theButton){
        setResult(RESULTCODE_CANCEL);
        finish();
    }
}
