[WIP] KVS Schema
==========

Immutable code generation to store key-value data for Android.

Contents
------------------

- [Background](#background)
- [Design goals](#design-goals)
- [How to use KVS Schema](#how-to-use-kvs-schema)
- [Warnings](#warnings)
- [Alternatives](#alternatives)


Background
------------------

Design goals
------------------

How to use KVS Schema
------------------


### Create Schema

```java
@Table("example")
public abstract class ExampleSchema extends PrefSchema {
    @Key("user_id") int userId;
    @Key("user_name") String userName = "guest";

    public static Example create(Context context) {
        return new Example(context);
    }
}
```

### Write And Read

```java
Example example = ExampleSchema.create(context);
example.putUserId(123);
example.putUserName("JAVA");
example.hasUserName(); // => true
example.getUserName(); // => JAVA
example.removeUserName();
example.hasUserName(); // => false
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
apt 'com.rejasupotaro:kvs-schema-compiler:0.0.4:fat'
compile 'com.rejasupotaro:kvs-schema-core:0.0.4'
compile 'com.rejasupotaro:kvs-schema:0.0.4'
```

### What's going on here?

KVS Schema runs as a standard annotation processor in javac.

Warnings
------------------
