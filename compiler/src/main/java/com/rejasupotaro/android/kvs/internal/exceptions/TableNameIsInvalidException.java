package com.rejasupotaro.android.kvs.internal.exceptions;

public class TableNameIsInvalidException extends RuntimeException {
    public TableNameIsInvalidException() {
        super();
    }

    public TableNameIsInvalidException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TableNameIsInvalidException(String detailMessage) {
        super(detailMessage);
    }

    public TableNameIsInvalidException(Throwable throwable) {
        super(throwable);
    }
}
