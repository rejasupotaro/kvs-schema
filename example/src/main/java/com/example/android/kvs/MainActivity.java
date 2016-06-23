package com.example.android.kvs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.kvs.databinding.ActivityMainBinding;
import com.example.android.kvs.models.User;
import com.example.android.kvs.prefs.schemas.ExamplePrefs;
import com.rejasupotaro.android.kvs.SharedPreferencesInfo;
import com.rejasupotaro.android.kvs.SharedPreferencesTable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        User user = new User(1, "Smith", 32, true);

        ExamplePrefs prefs = ExamplePrefs.get(this);

        prefs.putUserId(user.getId());
        long id = prefs.getUserId();
        binding.userIdText.setText("" + id);

        prefs.putGuestFlag(user.isGuest());
        boolean isGuest = prefs.getGuestFlag();
        binding.guestFlagText.setText("" + isGuest);

        prefs.putUserName(user.getName());
        binding.userNameText.setText(prefs.getUserName());

        prefs.putUserAge(user.getAge());
        long age = prefs.getUserAge();
        binding.userAgeText.setText("" + age);

        prefs.putSearchHistory(new HashSet<String>() {{
            add("turkey gravy");
            add("pork stake");
            add("banana cake");
        }});
        Set<String> searchHistory = prefs.getSearchHistory();
        binding.searchHistoryText.setText(searchHistory.toString());

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
