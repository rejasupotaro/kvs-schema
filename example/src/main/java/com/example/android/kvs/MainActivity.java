package com.example.android.kvs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.kvs.prefs.schemas.ExamplePrefs;
import com.rejasupotaro.android.kvs.SharedPreferencesInfo;
import com.rejasupotaro.android.kvs.SharedPreferencesTable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.user_id_text)
    TextView userIdTextView;
    @Bind(R.id.user_name_text)
    TextView userNameTextView;
    @Bind(R.id.user_age_text)
    TextView userAgeTextView;
    @Bind(R.id.guest_flag_text)
    TextView guestFlagTextView;
    @Bind(R.id.search_history_text)
    TextView searchHistoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        ExamplePrefs prefs = ExamplePrefs.get(this);

        prefs.putUserId(1L);
        long id = prefs.getUserId();
        userIdTextView.setText("" + id);

        prefs.putGuestFlag(false);
        boolean isGuest = prefs.getGuestFlag();
        guestFlagTextView.setText("" + isGuest);

        prefs.putUserName("Smith");
        userNameTextView.setText(prefs.getUserName());

        prefs.putUserAge(32);
        long age = prefs.getUserAge();
        userAgeTextView.setText("" + age);

        prefs.putSearchHistory(new HashSet<String>() {{
            add("turkey gravy");
            add("pork stake");
            add("banana cake");
        }});
        Set<String> languages = prefs.getSearchHistory();
        searchHistoryTextView.setText(languages.toString());

        List<SharedPreferencesTable> tables = SharedPreferencesInfo.getAll(this);
        for (SharedPreferencesTable table : tables) {
            Log.d("DEBUG", table.toString());
        }
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
