package com.serd.cashregister.rest.sync;

import android.content.Context;
import android.widget.Toast;

import com.serd.cashregister.app.R;
import com.serd.cashregister.db.DBHelper;
import com.serd.cashregister.rest.sync.PLU.GetPLUTask;
import com.serd.cashregister.rest.sync.PLUGroup.GetPLUGroupTask;
import com.serd.cashregister.rest.sync.PLUGroup.InsertPLUGroupTask;
import com.serd.cashregister.rest.sync.PLUGroup.UpdatePLUGroupTask;
import com.serd.cashregister.rest.sync.PLUMainGroup.GetPLUMainGroupTask;
import com.serd.cashregister.rest.sync.PLUMainGroup.InsertPLUMainGroupTask;
import com.serd.cashregister.rest.sync.PLUMainGroup.UpdatePLUMainGroupTask;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Tomas on 15. 11. 2014.
 */
public class Synchronizer
{

    public static final String TAG = "Synchronizer";

    private Context mContext;
    private DBHelper mDBHelper;
    private Queue<ISynchronizerTask> mTasks;

    public Synchronizer(Context context)
    {
        mContext = context;
        mDBHelper = new DBHelper(context);
        mTasks = new LinkedList<ISynchronizerTask>();
    }

    public void DoSync() {
        /*
        1. Delete items marked as (SERVERTIMESTAMP <> CLIENTTIMESTAMP) AND (DELETED)
        2. Insert new items       (SERVERTIMESTAMP = 0) AND (NOT DELETED)
        3. Update modified items  (SERVERTIMESTAMP <> 0) AND (SERVERTIMESTAMP <> CLIENTTIMESTAMP) AND (NOT DELETED)
         */

        mTasks.clear();

        //collect tasks to do
        //DeletePLUMainGroupItems();
        //DeletePLUGroupItems();

        InsertPLUMainGroupTask.prepare(this);
        InsertPLUGroupTask.prepare(this);
        UpdatePLUMainGroupTask.prepare(this);
        UpdatePLUGroupTask.prepare(this);
        GetPLUMainGroupTask.prepare(this);
        GetPLUGroupTask.prepare(this);
        GetPLUTask.prepare(this);

        //start queue processing
        processQueue();
    }


    public Context getContext() {
        return mContext;
    }

    public DBHelper getDBHelper() {
        return mDBHelper;
    }

    public void addTask(ISynchronizerTask task)
    {
        mTasks.add(task);
    }

    public void processQueue()
    {
        ISynchronizerTask task = mTasks.poll();
        if (task == null)
        {
            //we are done
            Toast.makeText(mContext, R.string.synchronizationComplete, Toast.LENGTH_SHORT).show();
            return;
        }

        task.execute();
    }
}
