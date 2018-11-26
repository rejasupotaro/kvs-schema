package com.example.android.kvs;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.android.kvs.models.User;
import com.example.android.kvs.prefs.schemas.TestWithSerializerPrefs;
import com.example.android.kvs.prefs.serializer.UserIntSerializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class TestWithSerializerPrefsTest {

    private TestWithSerializerPrefs prefs;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        this.prefs = TestWithSerializerPrefs.get(context);
        prefs.clear();
    }


    @Test
    public void serializeStringTypeWorks() {
        assertThat(prefs.hasUserString()).isFalse();

        User user = new User(99999999999L, "Smith", 32, true);
        prefs.putUserString(user);
        assertThat(prefs.hasUserString()).isTrue();
        User storedUser = prefs.getUserString();
        assertThat(storedUser.getId()).isEqualTo(99999999999L);
        assertThat(storedUser.getName()).isEqualTo("Smith");
        assertThat(storedUser.getAge()).isEqualTo(32);
        assertThat(storedUser.isGuest()).isTrue();

        prefs.removeUserString();
        assertThat(prefs.hasUserString()).isFalse();
    }

    @Test
    public void serializeIntTypeWorks() {
        assertThat(prefs.hasUserInt()).isFalse();

        User user = UserIntSerializer.USERS.get(1);
        prefs.putUserString(user);
        assertThat(prefs.hasUserString()).isTrue();
        User storedUser = prefs.getUserString();
        assertThat(storedUser.getId()).isEqualTo(2L);
        assertThat(storedUser.getName()).isEqualTo("John");
        assertThat(storedUser.getAge()).isEqualTo(28);
        assertThat(storedUser.isGuest()).isTrue();

        prefs.removeUserString();
        assertThat(prefs.hasUserString()).isFalse();
    }
}
