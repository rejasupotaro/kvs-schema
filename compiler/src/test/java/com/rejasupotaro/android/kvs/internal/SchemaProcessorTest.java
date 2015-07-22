package com.rejasupotaro.android.kvs.internal;

import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class SchemaProcessorTest {
    @Test
    public void testProcessor() {
        JavaFileObject examplePrefsSchema = JavaFileObjects
                .forSourceString("ExamplePrefsSchema", "package com.example.android.kvs;"
                        + "import android.content.Context;\n" +
                        "\n" +
                        "import com.rejasupotaro.android.kvs.PrefSchema;\n" +
                        "import com.rejasupotaro.android.kvs.annotations.Key;\n" +
                        "import com.rejasupotaro.android.kvs.annotations.Table;\n" +
                        "\n" +
                        "@Table(\"example\")\n" +
                        "public abstract class ExamplePrefsSchema extends PrefSchema {\n" +
                        "    public static ExamplePrefs prefs;\n" +
                        "\n" +
                        "    @Key(\"int_value\")\n" +
                        "    protected int intValue;\n" +
                        "    @Key(\"long_value\")\n" +
                        "    protected long longValue;\n" +
                        "    @Key(\"float_value\")\n" +
                        "    protected float floatValue;\n" +
                        "    @Key(\"boolean_value\")\n" +
                        "    protected boolean booleanValue;\n" +
                        "    @Key(\"string_value\")\n" +
                        "    protected String stringValue = \"guest\";\n" +
                        "\n" +
                        "    public static synchronized ExamplePrefs get(Context context) {\n" +
                        "        if (prefs == null) {\n" +
                        "            prefs = new ExamplePrefs(context);\n" +
                        "        }\n" +
                        "        return prefs;\n" +
                        "    }\n" +
                        "}\n");

        assert_().about(javaSource())
                .that(examplePrefsSchema)
                .processedWith(new SchemaProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(
                        JavaFileObjects.forSourceString("ExamplePrefs",
                                "package com.example.android.kvs;\n" +
                                        "\n" +
                                        "import android.content.Context;\n" +
                                        "import android.content.SharedPreferences;\n" +
                                        "import java.lang.String;\n" +
                                        "\n" +
                                        "public final class ExamplePrefs extends ExamplePrefsSchema {\n" +
                                        "  public final String TABLE_NAME = \"example\";\n" +
                                        "\n" +
                                        "  ExamplePrefs(Context context) {\n" +
                                        "    init(context, TABLE_NAME);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  ExamplePrefs(SharedPreferences prefs) {\n" +
                                        "    init(prefs);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public int getIntValue() {\n" +
                                        "    return getInt(\"int_value\", intValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putIntValue(int intValue) {\n" +
                                        "    putInt(\"int_value\", intValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasIntValue() {\n" +
                                        "    return has(\"int_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeIntValue() {\n" +
                                        "    remove(\"int_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public long getLongValue() {\n" +
                                        "    return getLong(\"long_value\", longValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putLongValue(long longValue) {\n" +
                                        "    putLong(\"long_value\", longValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasLongValue() {\n" +
                                        "    return has(\"long_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeLongValue() {\n" +
                                        "    remove(\"long_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public float getFloatValue() {\n" +
                                        "    return getFloat(\"float_value\", floatValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putFloatValue(float floatValue) {\n" +
                                        "    putFloat(\"float_value\", floatValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasFloatValue() {\n" +
                                        "    return has(\"float_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeFloatValue() {\n" +
                                        "    remove(\"float_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean getBooleanValue() {\n" +
                                        "    return getBoolean(\"boolean_value\", booleanValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putBooleanValue(boolean booleanValue) {\n" +
                                        "    putBoolean(\"boolean_value\", booleanValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasBooleanValue() {\n" +
                                        "    return has(\"boolean_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeBooleanValue() {\n" +
                                        "    remove(\"boolean_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public String getStringValue() {\n" +
                                        "    return getString(\"string_value\", stringValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putStringValue(String stringValue) {\n" +
                                        "    putString(\"string_value\", stringValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasStringValue() {\n" +
                                        "    return has(\"string_value\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeStringValue() {\n" +
                                        "    remove(\"string_value\");\n" +
                                        "  }\n" +
                                        "}\n"
                        ));
    }
}
