package com.rejasupotaro.android.kvs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPreferencesInfo {

    public static void dump(Context context) {
        List<SharedPreferences> sharedPreferencesList = getAllSharedPreferences(context);

        for (SharedPreferences sharedPreferences : sharedPreferencesList) {
            for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                String keyName = entry.getKey();
                String valueType = entry.getValue().getClass().getSimpleName();
                Log.d("DEBUG", String.format("%s %s", keyName, valueType));
            }
        }
    }

    public static List<SharedPreferences> getAllSharedPreferences(Context context) {
        File sharedPrefsDir = getSharedPrefsDir(context);
        File[] sharedPrefsFiles = sharedPrefsDir.listFiles();
        if (sharedPrefsFiles == null || sharedPrefsFiles.length == 0) {
            return new ArrayList<>();
        }

        List<SharedPreferences> sharedPreferencesList = new ArrayList<>();
        for (File file : sharedPrefsFiles) {
            String filenameWithoutExtension = file.getName().split("\\.(?=[^\\.]+$)")[0];
            Log.e("DEBUG", filenameWithoutExtension);

            SharedPreferences prefs = context.getSharedPreferences(filenameWithoutExtension, Context.MODE_PRIVATE);
            sharedPreferencesList.add(prefs);
        }
        return sharedPreferencesList;
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
