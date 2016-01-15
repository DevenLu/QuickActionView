# QuickActionView

[![Build Status](https://travis-ci.org/ovenbits/QuickActionView.svg?branch=master)](https://travis-ci.org/ovenbits/QuickActionView)

# Gradle Dependency

Easily reference the library in your Android projects using this dependency in your module's `build.gradle` file:

```Gradle
dependencies {
    compile 'com.ovenbits:quickactionview:1.0.0’
}
```

# Basic Usage

See the sample app for usage within a normal layout, as well as within a list using RecyclerView. In the most basic usage:

```java
View view = findViewById(R.id.your_view);
QuickActionView.make(this)
      .addActions(R.menu.actions)
      .register(view);
```
You can also create Actions at runtime:
```java
Drawable icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp);
String title = getString(R.string.action_favorite);
Action action = new Action(1337, icon, title);
QuickActionView.make(this)
        .addAction(action)
        //more configuring
        .register(view);
```

# Configuring the QuickActionView

QuickActionView can be customized globally, or on a per Action basis.
```java
QuickActionView.make(this)
                .addActions(R.menu.actions)
                .setOnActionSelectedListener(mQuickActionListener)
                .setBackgroundColor(Color.RED)
                .setTextColor(Color.BLUE)
                .setTextSize(30)
                .setScrimColor(Color.parseColor("#99FFFFFF"))
                //etc
                .register(view);
```

# Configuring Action Items

Use the `QuickActionConfig` builder to create custom configurations for each action item you create.
```java
//Give one of the quick actions custom colors
Action.Config actionConfig = new Action.Config()
        .setBackgroundColor(Color.BLUE)
        .setTextColor(Color.MAGENTA);

QuickActionView.make(this)
        .addActions(R.menu.actions)
        //the custom Action.Cofig will only apply to the addToCart action
        .setActionConfig(actionConfig, R.id.action_add_to_cart)
        .setActionsInAnimator(popAnimator)
        .setActionsOutAnimator(popAnimator)
        .register(findViewById(R.id.custom_parent));
quickActionView.setQuickActionConfig(R.id.actionAddToCart, quickActionConfig);
```

# Customizing Animations

You can even customize the animation performed when the actions come into view and go out of view. `FadeAnimator` `PopAnimator` and `SlideFromCenterAnimator` are three pre-built animators, and all you need to do to create your own is implement `ActionsInAnimator`, `ActionsOutAnimator`
and call `setActionsInAnimator` and `setActionsOutAnimator` respectively.

# Listening for Events

You can hook into the interesting events a QuickActionView has:
```java
QuickActionView.make(this)
                .addActions(R.menu.actions)
                .setOnActionSelectedListener(mQuickActionListener)
                .setOnShowListener(mQuickActionShowListener)
                .setOnDismissListener(mQuickActionDismissListener)
                .setOnActionHoverChangedListener(mOnActionHoverChangedListener)
                .register(view);
```

See the sample for more detail.

License
--------

    Copyright 2015 Oven Bits, LLC

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
