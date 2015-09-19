package com.rejasupotaro.android.kvs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesInfo {
    public static List<SharedPreferencesTable> getAll(Context context) {
        File sharedPrefsDir = getSharedPrefsDir(context);
        File[] sharedPrefsFiles = sharedPrefsDir.listFiles();
        if (sharedPrefsFiles == null || sharedPrefsFiles.length == 0) {
            return new ArrayList<>();
        }

        List<SharedPreferencesTable> sharedPreferencesTables = new ArrayList<>();
        for (File file : sharedPrefsFiles) {
            String filenameWithoutExtension = file.getName().split("\\.(?=[^\\.]+$)")[0]; // shared preferences name
            SharedPreferences sharedPreferences = context.getSharedPreferences(filenameWithoutExtension, Context.MODE_PRIVATE);
            SharedPreferencesTable sharedPreferencesTable = new SharedPreferencesTable(
                    sharedPreferences,
                    filenameWithoutExtension,
                    file.getAbsolutePath());
            sharedPreferencesTables.add(sharedPreferencesTable);
        }
        return sharedPreferencesTables;
    }

    public static File getSharedPrefsDir(Context context) {
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
