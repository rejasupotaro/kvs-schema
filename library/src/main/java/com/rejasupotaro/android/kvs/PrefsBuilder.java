package com.rejasupotaro.android.kvs;

import android.content.Context;

public interface PrefsBuilder<T extends PrefsSchema> {
    T build(Context context);
}
