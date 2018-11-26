package com.example.android.kvs;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.android.kvs.prefs.schemas.TestPrefs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class TestPrefsTest {

    private Context context;
    private TestPrefs prefs;

    @Before
    public void setup() {
        this.context = InstrumentationRegistry.getTargetContext();
        this.prefs = TestPrefs.get(context);
        prefs.clear();
    }

    @Test
    public void getSingletonInstance() {
        {
            TestPrefs prefs1 = new TestPrefs(context);
            TestPrefs prefs2 = new TestPrefs(context);
            assertThat(prefs1).isNotEqualTo(prefs2);
        }
        {
            TestPrefs prefs1 = TestPrefs.get(context);
            TestPrefs prefs2 = TestPrefs.get(context);
            assertThat(prefs1).isEqualTo(prefs2);
        }
    }

    @Test
    public void longWorks() {
        assertThat(prefs.hasLongValue()).isFalse();
        assertThat(prefs.getLongValue(99999999999L)).isEqualTo(99999999999L);

        prefs.putLongValue(-1L);
        assertThat(prefs.hasLongValue()).isTrue();
        assertThat(prefs.getLongValue()).isEqualTo(-1L);

        prefs.removeLongValue();
        assertThat(prefs.hasLongValue()).isFalse();
    }

    @Test
    public void stringWorks() {
        assertThat(prefs.hasStringValue()).isFalse();
        assertThat(prefs.getStringValue("abc")).isEqualTo("abc");

        prefs.putStringValue("def");
        assertThat(prefs.hasStringValue()).isTrue();
        assertThat(prefs.getStringValue()).isEqualTo("def");

        prefs.removeStringValue();
        assertThat(prefs.hasStringValue()).isFalse();
    }

    @Test
    public void intWorks() {
        assertThat(prefs.hasIntValue()).isFalse();
        assertThat(prefs.getIntValue(-1)).isEqualTo(-1);

        prefs.putIntValue(26);
        assertThat(prefs.hasIntValue()).isTrue();
        assertThat(prefs.getIntValue()).isEqualTo(26);

        prefs.removeIntValue();
        assertThat(prefs.hasIntValue()).isFalse();
    }

    @Test
    public void floatWorks(){
        assertThat(prefs.hasFloatValue()).isFalse();
        assertThat(prefs.getFloatValue(-1.0F)).isEqualTo(-1.0F);

        prefs.putFloatValue(40.0F);
        assertThat(prefs.hasFloatValue()).isTrue();
        assertThat(prefs.getFloatValue()).isEqualTo(40.0F);

        prefs.removeFloatValue();
        assertThat(prefs.hasFloatValue()).isFalse();
    }

    @Test
    public void booleanWorks() {
        assertThat(prefs.hasBooleanValue()).isFalse();
        assertThat(prefs.getBooleanValue(true)).isTrue();

        prefs.putBooleanValue(true);
        assertThat(prefs.hasBooleanValue()).isTrue();
        assertThat(prefs.getBooleanValue()).isTrue();

        prefs.removeBooleanValue();
        assertThat(prefs.hasBooleanValue()).isFalse();
    }


    @Test
    public void stringSetWorks() {
        assertThat(prefs.hasStringSetValue()).isFalse();
        assertThat(prefs.getStringSetValue(new HashSet<String>() {{
            add("Kotlin");
        }}).size()).isEqualTo(1);

        prefs.putStringSetValue(new HashSet<String>() {{
            add("JAVA");
            add("+");
            add("YOU");
        }});
        assertThat(prefs.hasStringSetValue()).isTrue();
        assertThat(prefs.getStringSetValue().size()).isEqualTo(3);
        assertThat(prefs.getStringSetValue().contains("JAVA")).isTrue();
        assertThat(prefs.getStringSetValue().contains("+")).isTrue();
        assertThat(prefs.getStringSetValue().contains("YOU")).isTrue();

        prefs.removeStringSetValue();
        assertThat(prefs.hasStringSetValue()).isFalse();
    }
}
