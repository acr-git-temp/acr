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

public class PLUMainGroupEditActivity extends ActionBarActivity {

    private Uri mUri;
    private int mState;
    private Cursor mCursor;

    EditText title;
    CheckBox checkBoxDeleted;

    static int REQUEST_CODE = 0;
    static int RESULTCODE_CANCEL = 0;
    static int RESULTCODE_OK = 1;

    private static final int STATE_EDIT = 0;
    private static final int STATE_INSERT = 1;

    private static final String[] PROJECTION = new String[] { ".id", "ID", "NAME", "DELETED" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_plumain_group_edit);
        setContentView(R.layout.activity_plumaingroup_edit);

        title = (EditText) findViewById(R.id.editTextLocationTitle);
        checkBoxDeleted = (CheckBox) findViewById(R.id.checkBoxDeleted);

        final Intent intent = getIntent();
        final String action = intent.getAction();

        if (Intent.ACTION_EDIT.equals(action)) {
            mState = STATE_EDIT;
            mUri = intent.getData();
            mCursor = getContentResolver().query(mUri,null ,null, null, null);
            mCursor.moveToFirst();
            title.setText(mCursor.getString(5));
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
        values.put("NAME", title.getText().toString());
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
