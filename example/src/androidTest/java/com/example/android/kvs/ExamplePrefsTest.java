package com.example.android.kvs;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class ExamplePrefsTest {

    private ExamplePrefs prefs;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        prefs = ExamplePrefs.get(context);
    }

    @Test
    public void checkTypeLongWorks() {
        prefs.clear();
        assertThat(prefs.hasUserId()).isFalse();

        prefs.putUserId(99999999999L);
        assertThat(prefs.hasUserId()).isTrue();
        assertThat(prefs.getUserId()).isEqualTo(99999999999L);

        prefs.removeUserId();
        assertThat(prefs.hasUserId()).isFalse();
    }

    @Test
    public void checkTypeStringWorks() {
        prefs.clear();
        assertThat(prefs.hasUserName()).isFalse();
        assertThat(prefs.getUserName("guest")).isEqualTo("guest");

        prefs.putUserName("rejasupotaro");
        assertThat(prefs.hasUserName()).isTrue();
        assertThat(prefs.getUserName()).isEqualTo("rejasupotaro");

        prefs.removeUserName();
        assertThat(prefs.hasUserName()).isFalse();
    }

    @Test
    public void checkTypeIntWorks() {
        prefs.clear();
        assertThat(prefs.hasUserAge()).isFalse();

        prefs.putUserAge(26);
        assertThat(prefs.hasUserAge()).isTrue();
        assertThat(prefs.getUserAge()).isEqualTo(26);

        prefs.removeUserAge();
        assertThat(prefs.hasUserAge()).isFalse();
    }

    @Test
    public void checkTypeBooleanWorks() {
        prefs.clear();
        assertThat(prefs.hasGuestFlag()).isFalse();

        prefs.putGuestFlag(true);
        assertThat(prefs.hasGuestFlag()).isTrue();
        assertThat(prefs.getGuestFlag()).isTrue();

        prefs.removeGuestFlag();
        assertThat(prefs.hasGuestFlag()).isFalse();
    }


    @Test
    public void checkTypeStringSetWorks() {
        prefs.clear();
        assertThat(prefs.hasSearchHistory()).isFalse();

        prefs.putSearchHistory(new HashSet<String>() {{
            add("JAVA");
            add("+");
            add("YOU");
        }});
        assertThat(prefs.hasSearchHistory()).isTrue();
        assertThat(prefs.getSearchHistory().size()).isEqualTo(3);

        prefs.removeSearchHistory();
        assertThat(prefs.hasSearchHistory()).isFalse();
    }
}
