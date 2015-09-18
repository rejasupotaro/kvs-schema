package com.rejasupotaro.android.kvs;

import android.content.SharedPreferences;

import com.jakewharton.fliptables.FlipTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPreferencesTable {
    private static final String[] HEADER = {"Key", "Type"};

    private String name;
    private List<Row> rows = new ArrayList<>();

    public SharedPreferencesTable(String name, SharedPreferences sharedPreferences) {
        this.name = name;

        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String keyName = entry.getKey();
            String valueType = entry.getValue().getClass().getSimpleName();
            rows.add(new Row(keyName, valueType));
        }
    }

    public String getName() {
        return name;
    }

    public List<Row> getRows() {
        return rows;
    }

    private String[][] toArray(List<Row> rows) {
        String[][] data = new String[rows.size()][2];
        for (int i = 0; i < rows.size(); i++) {
            Row column = rows.get(i);
            data[i][0] = column.getKey();
            data[i][1] = column.getValue();
        }
        return data;
    }

    @Override
    public String toString() {
        return String.format("\n\n  name: %s\n%s\n",
                name,
                FlipTable.of(HEADER, toArray(rows)));
    }

    public static class Row {
        private String key;
        private String value;

        public Row(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}

