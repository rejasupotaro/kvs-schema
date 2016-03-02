package com.example.android.kvs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefsBuilder;

public class ExamplePrefsBuilder implements PrefsBuilder<ExamplePrefs> {
    @Override
    public ExamplePrefs build(Context context) {
        return new ExamplePrefs(context);
    }
}
