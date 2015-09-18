package com.rejasupotaro.android.kvs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedPreferencesInfo {
    public static List<SharedPreferencesTable> getAllPrefsAsTable(Context context) {
        HashMap<String, SharedPreferences> sharedPreferencesMap = getAllPrefs(context);
        if (sharedPreferencesMap.isEmpty()) {
            return new ArrayList<>();
        }

        List<SharedPreferencesTable> sharedPreferencesTables = new ArrayList<>();
        for (Map.Entry<String, SharedPreferences> entry : sharedPreferencesMap.entrySet()) {
            SharedPreferencesTable sharedPreferencesTable = new SharedPreferencesTable(entry.getKey(), entry.getValue());
            sharedPreferencesTables.add(sharedPreferencesTable);
        }
        return sharedPreferencesTables;
    }

    public static HashMap<String, SharedPreferences> getAllPrefs(Context context) {
        File sharedPrefsDir = getPrefsDir(context);
        File[] sharedPrefsFiles = sharedPrefsDir.listFiles();
        if (sharedPrefsFiles == null || sharedPrefsFiles.length == 0) {
            return new HashMap<>();
        }

        HashMap<String, SharedPreferences> sharedPreferencesMap = new HashMap<>();
        for (File file : sharedPrefsFiles) {
            String filenameWithoutExtension = file.getName().split("\\.(?=[^\\.]+$)")[0];
            SharedPreferences prefs = context.getSharedPreferences(filenameWithoutExtension, Context.MODE_PRIVATE);
            sharedPreferencesMap.put(filenameWithoutExtension, prefs);
        }
        return sharedPreferencesMap;
    }

    public static File getPrefsDir(Context context) {
        return new File(getApplicationDataDir(context), "/shared_prefs");
    }

    public static File getApplicationDataDir(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return new File(packageInfo.applicationInfo.dataDir);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("Package name not found: ", e);
        }
    }
}
