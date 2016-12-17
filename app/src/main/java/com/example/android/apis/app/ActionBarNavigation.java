/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.apis.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.apis.R;

/**
 * This demonstrates implementing common navigation flows with the action bar.
 * 这表明实施通用的导航与操作栏中的流动。
 */
public class ActionBarNavigation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Turn on the up affordance.打开了启示。
        final ActionBar bar = getActionBar();
        bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);

        setContentView(R.layout.action_bar_navigation);
        TextView text = (TextView)findViewById(R.id.launchedfrom);
        if (getIntent().hasCategory(Intent.CATEGORY_SAMPLE_CODE)) {
            text.setText("这是从ApiDemos发动");
        } else {
            text.setText("这是从向上导航创建");
        }
    }

    public void onNewActivity(View button) {
        Intent intent = new Intent(this, ActionBarNavigationTarget.class);
        startActivity(intent);
    }

    public void onNewDocument(View button) {
        Intent intent = new Intent(this, ActionBarNavigationTarget.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(intent);
    }
}
