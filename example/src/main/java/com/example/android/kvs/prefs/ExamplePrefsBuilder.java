package com.example.android.kvs.prefs;

import android.content.Context;

import com.example.android.kvs.prefs.schemas.ExamplePrefs;
import com.rejasupotaro.android.kvs.PrefsBuilder;

public class ExamplePrefsBuilder implements PrefsBuilder<ExamplePrefs> {
    @Override
    public ExamplePrefs build(Context context) {
        return new ExamplePrefs(context);
    }
}
