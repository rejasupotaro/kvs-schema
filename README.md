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
example.putUserId("JAVA");
example.hasUserId(); // => true
example.getUserId(); // => JAVA
example.removeUserId();
example.hasUserId(); // => false
```

### Saved XML (when using PrefSchema)

```xml
root@android:/data/data/com.example.android.kvs/shared_prefs # cat example.xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
<string name="user_name">JAVA</string>
</map>
```

### Generated Code

```java
import android.content.Context;
public final class Example extends ExampleSchema {
  private final String name = "example";
  public Example(Context context) { init(context, name); }
  public int getUserId() { return getInt("user_id", userId); }
  public void putUserId(int userId) { putInt("user_id", userId); }
  public boolean hasUserId() { return has("user_id"); }
  public void removeUserId() { remove("user_id"); }
  public String getUserName() { return getString("user_name", userName); }
  public void putUserName(String userName) { putString("user_name", userName); }
  public boolean hasUserName() { return has("user_name"); }
  public void removeUserName() { remove("user_name"); }
}
```

### What's going on here?

KVS Schema runs as a standard annotation processor in javac.

Warnings
------------------
