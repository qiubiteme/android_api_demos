package com.example.android.apis.app;

import com.example.android.apis.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Example of various Intent flags to modify the activity stack.
 * 各种意向标志的例子来修改活动堆栈。
 */
public class IntentActivityFlags extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intent_activity_flags);

        // Watch for button clicks.当心按钮点击。
        Button button = (Button)findViewById(R.id.flag_activity_clear_task);
        button.setOnClickListener(mFlagActivityClearTaskListener);
        button = (Button)findViewById(R.id.flag_activity_clear_task_pi);
        button.setOnClickListener(mFlagActivityClearTaskPIListener);
    }

    /**
     * This creates an array of Intent objects representing the back stack
     * for a user going into the Views/Lists API demos.
     * 这将创建意向对象代表后退堆栈的数组
          *为进入视图/列表API演示的用户。
     */
//BEGIN_INCLUDE(intent_array)
    private Intent[] buildIntentsToViewsLists() {
        // We are going to rebuild our task with a new back stack.  This will
        // be done by launching an array of Intents, representing the new
        // back stack to be created, with the first entry holding the root
        // and requesting to reset the back stack.
        //我们要重建我们的任务，以崭新的回堆栈。 这会
        //通过启动意图的数组，表示新的做
        //回栈被创建，与第一项保持根
       //并请求重置回栈。
        Intent[] intents = new Intent[3];

        // First: root activity of ApiDemos.
        // This is a convenient way to make the proper Intent to launch and
        // reset an application's task.
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(this,
                com.example.android.apis.ApiDemos.class));

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(IntentActivityFlags.this, com.example.android.apis.ApiDemos.class);
        intent.putExtra("com.example.android.apis.Path", "Views");
        intents[1] = intent;

        intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(IntentActivityFlags.this, com.example.android.apis.ApiDemos.class);
        intent.putExtra("com.example.android.apis.Path", "Views/Lists");

        intents[2] = intent;
        return intents;
    }
//END_INCLUDE(intent_array)

    private OnClickListener mFlagActivityClearTaskListener = new OnClickListener() {
        public void onClick(View v) {
            startActivities(buildIntentsToViewsLists());
        }
    };

    private OnClickListener mFlagActivityClearTaskPIListener = new OnClickListener() {
        public void onClick(View v) {
            Context context = IntentActivityFlags.this;
//BEGIN_INCLUDE(pending_intent)
            PendingIntent pi = PendingIntent.getActivities(context, 0,
                    buildIntentsToViewsLists(), PendingIntent.FLAG_UPDATE_CURRENT);
//END_INCLUDE(pending_intent)
            try {
                pi.send();
            } catch (CanceledException e) {
                Log.w("IntentActivityFlags", "Failed sending PendingIntent", e);
            }
        }
    };
}
