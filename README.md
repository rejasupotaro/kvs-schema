KVS Schema
==========

Code generation library to manage key-value data for Android.

How to use KVS Schema
----------


### Create Schema

```java
@Table("example")
public abstract class ExamplePrefsSchema extends PrefSchema {
    @Key("user_id") int userId;
    @Key("user_name") String userName = "guest";

    public static ExamplePrefs create(Context context) {
        return new ExamplePrefs(context);
    }
}
```

### Write And Read

```java
ExamplePrefs examplePrefs = ExampleSchema.create(context);
examplePrefs.putUserId(123);
examplePrefs.putUserName("JAVA");
examplePrefs.hasUserName(); // => true
examplePrefs.getUserName(); // => JAVA
examplePrefs.removeUserName();
examplePrefs.hasUserName(); // => false
```

### Saved XML (when using PrefSchema)

```xml
root@android:/data/data/com.example.android.kvs/shared_prefs # cat example.xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
<string name="user_id">123</string>
</map>
```

### Installation

Add dependencies your build.gradle

```groovy
apt 'com.rejasupotaro:kvs-schema-compiler:0.1.9'
compile 'com.rejasupotaro:kvs-schema-core:0.1.9'
```

### What's going on here?

KVS Schema runs as a standard annotation processor in javac.

For developers
----------

### Show version

```sh
$ ./gradlew version
```

### Bump version

```sh
$ ./gradlew bumpMajor
$ ./gradlew bumpMinor
$ ./gradlew bumpPatch
```

### Upload library

```sh
$ ./gradlew bintrayUpload
```