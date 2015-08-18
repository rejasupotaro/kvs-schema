package com.example.android.kvs;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ActionBarActivity {
    @Bind(R.id.int_value_text)
    TextView intValueTextView;
    @Bind(R.id.long_value_text)
    TextView longValueTextView;
    @Bind(R.id.float_value_text)
    TextView floatValueTextView;
    @Bind(R.id.boolean_value_text)
    TextView booleanValueTextView;
    @Bind(R.id.string_value_text)
    TextView stringValueTextView;
    @Bind(R.id.string_set_text)
    TextView stringSetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

        prefs.putStringSet(new HashSet<String>() {{
            add("JAVA");
            add("+");
            add("YOU");
        }});
        Set<String> stringSet = prefs.getStringSet();
        stringSetTextView.setText(stringSet.toString());
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
