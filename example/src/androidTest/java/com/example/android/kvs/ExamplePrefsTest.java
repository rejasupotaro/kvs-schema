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
    public void longWorks() {
        assertThat(prefs.hasUserId()).isFalse();
        assertThat(prefs.getUserId(-1L)).isEqualTo(-1L);

        prefs.putUserId(99999999999L);
        assertThat(prefs.hasUserId()).isTrue();
        assertThat(prefs.getUserId()).isEqualTo(99999999999L);

        prefs.removeUserId();
        assertThat(prefs.hasUserId()).isFalse();
    }

    @Test
    public void stringWorks() {
        assertThat(prefs.hasUserName()).isFalse();
        assertThat(prefs.getUserName("guest")).isEqualTo("guest");

        prefs.putUserName("rejasupotaro");
        assertThat(prefs.hasUserName()).isTrue();
        assertThat(prefs.getUserName()).isEqualTo("rejasupotaro");

        prefs.removeUserName();
        assertThat(prefs.hasUserName()).isFalse();
    }

    @Test
    public void intWorks() {
        assertThat(prefs.hasUserAge()).isFalse();
        assertThat(prefs.getUserAge(-1)).isEqualTo(-1);

        prefs.putUserAge(26);
        assertThat(prefs.hasUserAge()).isTrue();
        assertThat(prefs.getUserAge()).isEqualTo(26);

        prefs.removeUserAge();
        assertThat(prefs.hasUserAge()).isFalse();
    }

    @Test
    public void floatWorks(){
        assertThat(prefs.hasProgress()).isFalse();
        assertThat(prefs.getProgress(-1.0F)).isEqualTo(-1.0F);

        prefs.putProgress(40.0F);
        assertThat(prefs.hasProgress()).isTrue();
        assertThat(prefs.getProgress()).isEqualTo(40.0F);

        prefs.removeProgress();
        assertThat(prefs.hasProgress()).isFalse();
    }

    @Test
    public void booleanWorks() {
        assertThat(prefs.hasGuestFlag()).isFalse();
        assertThat(prefs.getGuestFlag(true)).isTrue();

        prefs.putGuestFlag(true);
        assertThat(prefs.hasGuestFlag()).isTrue();
        assertThat(prefs.getGuestFlag()).isTrue();

        prefs.removeGuestFlag();
        assertThat(prefs.hasGuestFlag()).isFalse();
    }


    @Test
    public void stringSetWorks() {
        assertThat(prefs.hasSearchHistory()).isFalse();
        assertThat(prefs.getSearchHistory(new HashSet<String>() {{
            add("Kotlin");
        }}).size()).isEqualTo(1);

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
