package com.example.android.kvs;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.int_value_text)
    TextView intValueTextView;
    @InjectView(R.id.long_value_text)
    TextView longValueTextView;
    @InjectView(R.id.float_value_text)
    TextView floatValueTextView;
    @InjectView(R.id.boolean_value_text)
    TextView booleanValueTextView;
    @InjectView(R.id.string_value_text)
    TextView stringValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupViews();
    }

    private void setupViews() {
        ExamplePrefs prefs = ExamplePrefsSchema.create(this);

        prefs.putIntValue(1);
        long intValue = prefs.getIntValue();
        intValueTextView.setText("" + intValue);

        prefs.putLongValue(1);
        long longValue = prefs.getLongValue();
        longValueTextView.setText("" + longValue);

        prefs.putFloatValue(1.0f);
        float floatValue = prefs.getFloatValue();
        floatValueTextView.setText("" + floatValue);

        prefs.putBooleanValue(true);
        boolean booleanValue = prefs.getBooleanValue();
        booleanValueTextView.setText("" + booleanValue);

        stringValueTextView.setText(prefs.getStringValue());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
