package com.serd.cashregister.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.serd.cashregister.providers.PluProvider;
import com.serd.cashregister.rest.sync.Synchronizer;


public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonEditPLUMainGroup = (Button) findViewById(R.id.buttonEditPLUMainGroup);
        buttonEditPLUMainGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), com.serd.cashregister.app.PLUMainGroupListActivity.class);
                startActivity(intent);
            }
        });

        Button buttonEditPLUGroup = (Button) findViewById(R.id.buttonEditPLUGroup);
        buttonEditPLUGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), com.serd.cashregister.app.PLUGroupListActivity.class);
                startActivity(intent);
            }
        });

        Button buttonEditPLU = (Button) findViewById(R.id.buttonEditPLU);
        buttonEditPLU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), com.serd.cashregister.app.PLUListActivity.class);
                //startActivity(intent);
                Cursor c = getApplicationContext().getContentResolver().query(PluProvider.CONTENT_ID_URI_LOCATION, null, null, null, null);
                c.getCount();
                if (c.moveToFirst()) {
                    do {
                        for (Integer i = 0; i < c.getColumnCount(); i++) {
                            Log.d("PLU", "[" + c.getColumnName(i) + "] = " + c.getString(i));
                        }
                    }
                    while (c.moveToNext());
                }
            }
        });

        Button buttonSync = (Button) findViewById(R.id.buttonSync);
        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Synchronizer m = new Synchronizer(v.getContext());
                m.DoSync();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
