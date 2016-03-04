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

    private Context context;
    private ExamplePrefs prefs;

    @Before
    public void setup() {
        this.context = InstrumentationRegistry.getTargetContext();
        this.prefs = ExamplePrefs.get(context);
        prefs.clear();
    }

    @Test
    public void getSingletonInstance() {
        {
            ExamplePrefs prefs1 = new ExamplePrefs(context);
            ExamplePrefs prefs2 = new ExamplePrefs(context);
            assertThat(prefs1).isNotEqualTo(prefs2);
        }
        {
            ExamplePrefs prefs1 = ExamplePrefs.get(context);
            ExamplePrefs prefs2 = ExamplePrefs.get(context);
            assertThat(prefs1).isEqualTo(prefs2);
        }
    }

    @Test
    public void checkTypeLongWorks() {
        assertThat(prefs.hasUserId()).isFalse();

        prefs.putUserId(99999999999L);
        assertThat(prefs.hasUserId()).isTrue();
        assertThat(prefs.getUserId()).isEqualTo(99999999999L);

        prefs.removeUserId();
        assertThat(prefs.hasUserId()).isFalse();
    }

    @Test
    public void checkTypeStringWorks() {
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
        assertThat(prefs.hasUserAge()).isFalse();

        prefs.putUserAge(26);
        assertThat(prefs.hasUserAge()).isTrue();
        assertThat(prefs.getUserAge()).isEqualTo(26);

        prefs.removeUserAge();
        assertThat(prefs.hasUserAge()).isFalse();
    }

    @Test
    public void checkTypeBooleanWorks() {
        assertThat(prefs.hasGuestFlag()).isFalse();

        prefs.putGuestFlag(true);
        assertThat(prefs.hasGuestFlag()).isTrue();
        assertThat(prefs.getGuestFlag()).isTrue();

        prefs.removeGuestFlag();
        assertThat(prefs.hasGuestFlag()).isFalse();
    }


    @Test
    public void checkTypeStringSetWorks() {
        assertThat(prefs.hasSearchHistory()).isFalse();

        prefs.putSearchHistory(new HashSet<String>() {{
            add("JAVA");
            add("+");
            add("YOU");
        }});
        assertThat(prefs.hasSearchHistory()).isTrue();
        assertThat(prefs.getSearchHistory().size()).isEqualTo(3);
        assertThat(prefs.getSearchHistory().contains("JAVA")).isTrue();
        assertThat(prefs.getSearchHistory().contains("+")).isTrue();
        assertThat(prefs.getSearchHistory().contains("YOU")).isTrue();

        prefs.removeSearchHistory();
        assertThat(prefs.hasSearchHistory()).isFalse();
    }
}
