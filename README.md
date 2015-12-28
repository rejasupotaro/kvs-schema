KVS Schema
==========

KVS Schema is a code generation library to manage key-value data for Android.

I use SharedPreferences to save values. However, sometimes I forget key names and types.
This library helps you to manage key-value data. You can find the structure of pref file at a glance.

How to use
----------

### Create Schema

First, create a schema class. Class name should be `*Schema`.

```java
@Table("example")
public abstract class ExamplePrefsSchema {
    @Key("user_id") int userId;
    @Key("user_name") String userName;

    public static ExamplePrefs create(Context context) {
        return new ExamplePrefs(context);
    }
}
```

### Read and Write

Annotation processor generates `put*`, `get*`, `has*` and `remove*` methods. You can use these methods.

```java
ExamplePrefs prefs = ExamplePrefsSchema.create(context);
prefs.putUserId(123);
prefs.putUserName("Jack");
prefs.hasUserName(); // => true
prefs.getUserName(); // => Jack
prefs.removeUserName();
prefs.hasUserName(); // => false
```

### Supported types

kvs-schema supports these types for now.

- boolean
- String
- float
- int
- long
- String set

### Saved XML (the case of PrefSchema)

`@Table`'s value becomes SharedPreferences' name.

```xml
root@android:/data/data/com.example.android.kvs/shared_prefs # cat example.xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
<string name="user_id">123</string>
</map>
```

### Installation

This library is distributed by [JitPack](https://jitpack.io/).
Add dependencies your build.gradle

```groovy
apt 'com.github.rejasupotaro.kvs-schema:compiler:1.4.0'
compile 'com.github.rejasupotaro.kvs-schema:library:1.4.0'
```

Migration
----------

Even if you have already used SharedPreferences directly, migration is easy. KVS Schema simply maps the structure of SharedPreferences.

For example, if you are using default SharedPreferences like below,

```java
prefs = PreferenceManager.getDefaultSharedPreferences(this);
Editor editor = prefs.edit();
editor.putString("user_id", "1");
editor.putString("user_name", "rejasupotaro");
editor.apply();
```

your data is saved in `path/to/app/shared_prefs/package_name_preferences.xml`. The schema becomes below.

```java
@Table("package_name_preferences")
public abstract class ExamplePrefsSchema extends PrefSchema {
    public static ExamplePrefs prefs;

    @Key("user_id")
    int userId;
    @Key("user_name")
    String userName;
}
```

In addition, SharedPreferencesInfo may help you to migrate existing app. You can get existing SharedPreferences through `SharedPreferencesInfo.getAllPrefsAsTable`.

```java
List<SharedPreferencesTable> tables = SharedPreferencesInfo.getAll(this);
        for (SharedPreferencesTable table : tables) {
            Log.d("DEBUG", table.toString());
        }
```

You can see what kind of data is saved in your app like below.

```
   name: com.example.android.kvs_preferences
   path: /data/data/com.example.android.kvs/shared_prefs/com.example.android.kvs_preferences.xml
 ╔═══════════╤══════════════╤════════╗
 ║ Key       │ Value        │ Type   ║
 ╠═══════════╪══════════════╪════════╣
 ║ user_name │ rejasupotaro │ String ║
 ╟───────────┼──────────────┼────────╢
 ║ user_id   │ 1            │ String ║
 ╚═══════════╧══════════════╧════════╝
 ```

Development
----------

**Show version**

```sh
$ ./gradlew version
```

**Bump version**

```sh
$ ./gradlew bumpMajor
$ ./gradlew bumpMinor
$ ./gradlew bumpPatch
```

**Generate README**

```sh
$ ./gradlew genReadMe
```
