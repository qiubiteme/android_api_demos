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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

/**
 * This demonstrates the basics of the Action Bar and how it interoperates with the
 * standard options menu. This demo is for informative purposes only; see ActionBarUsage for
 * an example of using the Action Bar in a more idiomatic manner.
 * 这证明了操作栏的基本知识，以及如何与互操作
  *标准选项菜单。此演示仅用于信息目的;看到ActionBarUsage为
  *更惯用的方式使用操作栏的一个例子。
 */
public class ActionBarMechanics extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The Action Bar is a window feature. The feature must be requested
        // before setting a content view. Normally this is set automatically
        // by your Activity's theme in your manifest. The provided system
        // theme Theme.WithActionBar enables this for you. Use it as you would
        // use Theme.NoTitleBar. You can add an Action Bar to your own themes
        // by adding the element <item name="android:windowActionBar">true</item>
        // to your style definition.
/*        //操作栏是一个窗口的功能。该功能必须要求
                //设置内容视图之前。通常这是自动设置
                //你的活动在您的清单中的主题。所提供的系统
                //主题Theme.WithActionBar使这个给你。使用它，你会
                //使用Theme.NoTitleBar。您可以添加一个操作栏，以自己的主题
                //加入元素<项目名称=“机器人：windowActionBar”>真正</项目>
                //你的风格定义。*/
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Menu items default to never show in the action bar. On most devices this means
        // they will show in the standard options menu panel when the menu button is pressed.
        // On xlarge-screen devices a "More" button will appear in the far right of the
        // Action Bar that will display remaining items in a cascading menu.
        //菜单项默认为从未在操作栏中显示。在大多数设备，这意味着
        //他们将在标准选项菜单面板显示按下菜单按钮时。
        //在XLARGE屏设备“更多”按钮会出现在最右边的
        //操作栏，将在级联菜单中显示剩余项目。
        menu.add("\n" + "普通项目");

        MenuItem actionItem = menu.add("Action Button");

        // Items that show as actions should favor the "if room" setting, which will
        // prevent too many buttons from crowding the bar. Extra items will show in the
        // overflow area.
        //物品显示为行动应该有利于“如果房”设置，这将
        //防止挤占了吧太多的按钮。额外的项目，将呈现
        //溢出区。
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        // Items that show as actions are strongly encouraged to use an icon.
        // These icons are shown without a text description, and therefore should
        // be sufficiently descriptive on their own.

        //物品显示为行动强烈建议使用的图标。
        //这些图标显示没有文本描述，因此应该
        //对自己充分说明。
        actionItem.setIcon(android.R.drawable.ic_menu_share);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "选定条目： " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
