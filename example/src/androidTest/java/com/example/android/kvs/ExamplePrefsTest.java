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
        prefs = ExamplePrefsSchema.get(context);
    }

    @Test
    public void checkTypeLongWorks() {
        prefs.clear();
        assertFalse(prefs.hasUserId());

        prefs.putUserId(99999999999L);
        assertTrue(prefs.hasUserId());
        assertThat(prefs.getUserId(), is(99999999999L));

        prefs.removeUserId();
        assertFalse(prefs.hasUserId());
    }

    @Test
    public void checkTypeStringWorks() {
        prefs.clear();
        assertFalse(prefs.hasUserName());
        assertThat(prefs.getUserName("guest"), is("guest"));

        prefs.putUserName("rejasupotaro");
        assertTrue(prefs.hasUserName());
        assertThat(prefs.getUserName(), is("rejasupotaro"));

        prefs.removeUserName();
        assertFalse(prefs.hasUserName());
    }

    @Test
    public void checkTypeIntWorks() {
        prefs.clear();
        assertFalse(prefs.hasUserAge());

        prefs.putUserAge(26);
        assertTrue(prefs.hasUserAge());
        assertThat(prefs.getUserAge(), is(26));

        prefs.removeUserAge();
        assertFalse(prefs.hasUserAge());
    }

    @Test
    public void checkTypeBooleanWorks() {
        prefs.clear();
        assertFalse(prefs.hasGuestFlag());

        prefs.putGuestFlag(true);
        assertTrue(prefs.hasGuestFlag());
        assertThat(prefs.getGuestFlag(), is(true));

        prefs.removeGuestFlag();
        assertFalse(prefs.hasGuestFlag());
    }


    @Test
    public void checkTypeStringSetWorks() {
        prefs.clear();
        assertFalse(prefs.hasSearchHistory());

        prefs.putSearchHistory(new HashSet<String>() {{
            add("JAVA");
            add("+");
            add("YOU");
        }});
        assertTrue(prefs.hasSearchHistory());
        assertThat(prefs.getSearchHistory().size(), is(3));

        prefs.removeSearchHistory();
        assertFalse(prefs.hasSearchHistory());
    }
}
