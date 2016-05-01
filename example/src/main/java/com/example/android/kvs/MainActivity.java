package com.example.android.kvs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.kvs.models.User;
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
        User user = new User(1, "Smith", 32, true);

        ExamplePrefs prefs = ExamplePrefs.get(this);

        prefs.putUserId(user.getId());
        long id = prefs.getUserId();
        userIdTextView.setText("" + id);

        prefs.putGuestFlag(user.isGuest());
        boolean isGuest = prefs.getGuestFlag();
        guestFlagTextView.setText("" + isGuest);

        prefs.putUserName(user.getName());
        userNameTextView.setText(prefs.getUserName());

        prefs.putUserAge(user.getAge());
        long age = prefs.getUserAge();
        userAgeTextView.setText("" + age);

        prefs.putSearchHistory(new HashSet<String>() {{
            add("turkey gravy");
            add("pork stake");
            add("banana cake");
        }});
        Set<String> searchHistory = prefs.getSearchHistory();
        searchHistoryTextView.setText(searchHistory.toString());

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
