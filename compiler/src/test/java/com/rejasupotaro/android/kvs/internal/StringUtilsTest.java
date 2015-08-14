package com.rejasupotaro.android.kvs.internal;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
    @Test
    public void testCapitalize() {
        assertThat(StringUtils.capitalize("username")).isEqualTo("Username");
    }

    @Test
    public void testIsEmpty() {
        assertThat(StringUtils.isEmpty(null)).isTrue();
        assertThat(StringUtils.isEmpty("")).isTrue();
        assertThat(StringUtils.isEmpty("hoge")).isFalse();
    }

}
