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
@Kvs("example")
public abstract class SampleSchema extends PrefSchema {
    @Key("user_id") int userId;
    @Key("user_name") String userName;

    public static Example create(Context context) {
        return new Example(context);
    }
}
```

### Write And Read

```java
Example example = ExampleSchema.create(context);
example.putUserId("JAVA");
example.getUserId(); // => JAVA
```

### What's going on here?

KVS Schema runs as a standard annotation processor in javac.

Warnings
------------------
