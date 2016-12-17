/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.example.android.apis.view;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.view.LayoutInflater;

import com.example.android.apis.R;

/**
 * An example of tabs that uses labels ({@link TabSpec#setIndicator(CharSequence)})
 * for its indicators and views by id from a layout file ({@link TabSpec#setContent(int)}).
 * 使用标签标签的例子（{@link则tabspec＃setIndicator（CharSequence的）}）
  *从布局文件的指标和视图ID（{@link则tabspec＃setContent（INT）}）。
 */
public class Tabs1 extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();
        
        LayoutInflater.from(this).inflate(R.layout.tabs1, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("标签1")   //设置选项卡标签
                .setContent(R.id.view1));   //设置选项卡内容Viewid
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("标签2")
                .setContent(R.id.view2));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("标签2")
                .setContent(R.id.view3));
    }
}
