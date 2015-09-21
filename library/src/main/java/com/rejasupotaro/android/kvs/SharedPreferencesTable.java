package com.rejasupotaro.android.kvs;

import android.content.SharedPreferences;

import com.jakewharton.fliptables.FlipTable;

import java.util.Map;

public class SharedPreferencesTable {
    private static final String[] HEADER = {"Key", "Value", "Type"};

    private SharedPreferences sharedPreferences;
    private String name;
    private String path;

    public SharedPreferencesTable(SharedPreferences sharedPreferences, String name, String path) {
        this.sharedPreferences = sharedPreferences;
        this.name = name;
        this.path = path;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    private String[][] toArray() {
        String[][] data = new String[sharedPreferences.getAll().size()][3];

        int index = 0;
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            String type = entry.getValue().getClass().getSimpleName();

            data[index][0] = key;
            data[index][1] = value;
            data[index][2] = type;

            index++;
        }

        return data;
    }

    @Override
    public String toString() {
        return String.format("\n\n  name: %s\n  path: %s\n%s\n",
                name,
                path,
                FlipTable.of(HEADER, toArray()));
    }
}

