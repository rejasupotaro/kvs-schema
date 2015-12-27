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
                .forSourceString("ExamplePrefsSchema",
                        "package com.example.android.kvs;\n" +
                                "\n" +
                                "import android.content.Context;\n" +
                                "\n" +
                                "import com.rejasupotaro.android.kvs.annotations.Key;\n" +
                                "import com.rejasupotaro.android.kvs.annotations.Table;\n" +
                                "\n" +
                                "import java.util.Set;\n" +
                                "\n" +
                                "@Table(\"example\")\n" +
                                "public abstract class ExamplePrefsSchema {\n" +
                                "    public static ExamplePrefs prefs;\n" +
                                "\n" +
                                "    @Key(\"user_id\")\n" +
                                "    long userId;\n" +
                                "    @Key(\"user_name\")\n" +
                                "    String userName = \"guest\";\n" +
                                "    @Key(\"user_age\")\n" +
                                "    int userAge;\n" +
                                "    @Key(\"guest_flag\")\n" +
                                "    boolean guestFlag;\n" +
                                "    @Key(\"search_history\")\n" +
                                "    Set<String> searchHistory;\n" +
                                "\n" +
                                "    public static synchronized ExamplePrefs get(Context context) {\n" +
                                "        if (prefs == null) {\n" +
                                "            prefs = new ExamplePrefs(context);\n" +
                                "        }\n" +
                                "        return prefs;\n" +
                                "    }\n" +
                                "}");

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
                                        "import com.rejasupotaro.android.kvs.PrefsSchema;\n" +
                                        "import java.lang.String;\n" +
                                        "import java.util.HashSet;\n" +
                                        "import java.util.Set;\n" +
                                        "\n" +
                                        "public final class ExamplePrefs extends PrefsSchema {\n" +
                                        "  public static final String TABLE_NAME = \"example\";\n" +
                                        "\n" +
                                        "  ExamplePrefs(Context context) {\n" +
                                        "    init(context, TABLE_NAME);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  ExamplePrefs(SharedPreferences prefs) {\n" +
                                        "    init(prefs);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public long getUserId(long defaultValue) {\n" +
                                        "    return getLong(\"user_id\", defaultValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public long getUserId() {\n" +
                                        "    return getLong(\"user_id\", 0);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putUserId(long userId) {\n" +
                                        "    putLong(\"user_id\", userId);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasUserId() {\n" +
                                        "    return has(\"user_id\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeUserId() {\n" +
                                        "    remove(\"user_id\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public String getUserName() {\n" +
                                        "    return getString(\"user_name\", \"\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public String getUserName(String defaultValue) {\n" +
                                        "    return getString(\"user_name\", defaultValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putUserName(String userName) {\n" +
                                        "    putString(\"user_name\", userName);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasUserName() {\n" +
                                        "    return has(\"user_name\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeUserName() {\n" +
                                        "    remove(\"user_name\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public int getUserAge(int defaultValue) {\n" +
                                        "    return getInt(\"user_age\", defaultValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public int getUserAge() {\n" +
                                        "    return getInt(\"user_age\", 0);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putUserAge(int userAge) {\n" +
                                        "    putInt(\"user_age\", userAge);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasUserAge() {\n" +
                                        "    return has(\"user_age\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeUserAge() {\n" +
                                        "    remove(\"user_age\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean getGuestFlag(boolean defaultValue) {\n" +
                                        "    return getBoolean(\"guest_flag\", defaultValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean getGuestFlag() {\n" +
                                        "    return getBoolean(\"guest_flag\", false);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putGuestFlag(boolean guestFlag) {\n" +
                                        "    putBoolean(\"guest_flag\", guestFlag);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasGuestFlag() {\n" +
                                        "    return has(\"guest_flag\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeGuestFlag() {\n" +
                                        "    remove(\"guest_flag\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public Set<String> getSearchHistory() {\n" +
                                        "    return getStringSet(\"search_history\", new HashSet<String>());\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public Set<String> getSearchHistory(Set<String> defaultValue) {\n" +
                                        "    return getStringSet(\"search_history\", defaultValue);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void putSearchHistory(Set<String> searchHistory) {\n" +
                                        "    putStringSet(\"search_history\", searchHistory);\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public boolean hasSearchHistory() {\n" +
                                        "    return has(\"search_history\");\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public void removeSearchHistory() {\n" +
                                        "    remove(\"search_history\");\n" +
                                        "  }\n" +
                                        "}\n"
                        ));
    }
}
