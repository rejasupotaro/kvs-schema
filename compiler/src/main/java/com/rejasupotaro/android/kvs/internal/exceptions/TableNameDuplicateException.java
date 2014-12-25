package com.rejasupotaro.android.kvs.internal.exceptions;

public class TableNameDuplicateException extends RuntimeException {
    public TableNameDuplicateException() {
        super();
    }

    public TableNameDuplicateException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TableNameDuplicateException(String detailMessage) {
        super(detailMessage);
    }

    public TableNameDuplicateException(Throwable throwable) {
        super(throwable);
    }
}
