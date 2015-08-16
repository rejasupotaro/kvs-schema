package com.example.android.kvs;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExamplePrefsTest {

    private ExamplePrefs prefs;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        prefs = ExamplePrefsSchema.create(context);
    }

    @Test
    public void checkWhetherTypeIntWorks() {
        prefs.clear();
        assertFalse(prefs.hasIntValue());

        prefs.putIntValue(999);
        assertTrue(prefs.hasIntValue());
        assertThat(prefs.getIntValue(), is(999));

        prefs.removeIntValue();
        assertFalse(prefs.hasIntValue());
    }

    @Test
    public void checkWhetherTypeLongWorks() {
        prefs.clear();
        assertFalse(prefs.hasLongValue());

        prefs.putLongValue(99999999999L);
        assertTrue(prefs.hasLongValue());
        assertThat(prefs.getLongValue(), is(99999999999L));

        prefs.removeLongValue();
        assertFalse(prefs.hasLongValue());
    }

    @Test
    public void checkWhetherTypeFloatWorks() {
        prefs.clear();
        assertFalse(prefs.hasFloatValue());

        prefs.putFloatValue(99.99f);
        assertTrue(prefs.hasFloatValue());
        assertThat(prefs.getFloatValue(), is(99.99f));

        prefs.removeFloatValue();
        assertFalse(prefs.hasFloatValue());
    }

    @Test
    public void checkWhetherTypeBooleanWorks() {
        prefs.clear();
        assertFalse(prefs.hasBooleanValue());

        prefs.putBooleanValue(true);
        assertTrue(prefs.hasBooleanValue());
        assertThat(prefs.getBooleanValue(), is(true));

        prefs.removeBooleanValue();
        assertFalse(prefs.hasBooleanValue());
    }

    @Test
    public void checkWhetherTypeStringWorks() {
        prefs.clear();
        assertFalse(prefs.hasStringValue());
        assertThat(prefs.getStringValue(), is("guest"));

        prefs.putStringValue("Java");
        assertTrue(prefs.hasStringValue());
        assertThat(prefs.getStringValue(), is("Java"));

        prefs.removeStringValue();
        assertFalse(prefs.hasStringValue());
    }

    @Test
    public void checkWhetherTypeStringSetWorks() {
        prefs.clear();
        assertFalse(prefs.hasStringSet());

        prefs.putStringSet(new HashSet<String>() {{
            add("JAVA");
            add("+");
            add("YOU");
        }});
        assertTrue(prefs.hasStringSet());
        assertThat(prefs.getStringSet().size(), is(3));

        prefs.removeStringSet();
        assertFalse(prefs.hasStringSet());
    }
}
