package com.serd.cashregister.app;


import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.serd.cashregister.providers.PluMainGroupProvider;

public class PLUMainGroupListActivity extends ListActivity {
    public static final String TAG = "PLUMainGroupListActivity";

    Cursor mCursor;
    android.widget.SimpleCursorAdapter mAdapter;
    LocalUriContentObserver mObserver;
    Boolean mShowDeleted = true;

    private static final String[] PROJECTION = new String[] { "NAME", "ID", "SERVERTIMESTAMP", "CLIENTTIMESTAMP", "_id", "DELETED" };

    class LocalUriContentObserver extends ContentObserver {

        public LocalUriContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumaingroup_list);

        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(PluMainGroupProvider.CONTENT_ID_URI_LOCATION);
        }

        mObserver = new LocalUriContentObserver(new Handler());
        mCursor = getApplicationContext().getContentResolver().query(PluMainGroupProvider.CONTENT_ID_URI_LOCATION, PROJECTION, mShowDeleted ? null : "DELETED <> 1", null, null);

        mAdapter = new  android.widget.SimpleCursorAdapter(this, R.layout.plumaingroup_list_layout, mCursor, PROJECTION,
                new int[] {R.id.textViewName, R.id.textViewId, R.id.textViewServerTimestamp, R.id.textViewClientTimestamp, R.id.textViewId, R.id.textViewDeleted});
        setListAdapter(mAdapter);

        getApplicationContext().getContentResolver().registerContentObserver(PluMainGroupProvider.CONTENT_ID_URI_LOCATION, true, mObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_plumaingroup_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()) {
            case R.id.menu_track_insert:
                startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
                break;
            case R.id.menu_track_delete:
                if (mShowDeleted) {
                    mShowDeleted = false;
                    Toast.makeText(getApplicationContext(), R.string.deletedItemsHidden, Toast.LENGTH_SHORT).show();
                    reload();
                }
                else {
                    mShowDeleted = true;
                    Toast.makeText(getApplicationContext(), R.string.deletedItemsVisible, Toast.LENGTH_SHORT).show();
                    reload();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), getListAdapter().getItemId(position));

        String action = getIntent().getAction();
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }

    private void reload() {
        mCursor = getApplicationContext().getContentResolver().query(PluMainGroupProvider.CONTENT_ID_URI_LOCATION, PROJECTION, mShowDeleted ? null : "DELETED <> 1", null, null);
        mAdapter.changeCursor(mCursor);
    }
}