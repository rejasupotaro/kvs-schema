package com.rejasupotaro.android.kvs.internal.exceptions;

public class TableNameIsNotDefinedException extends RuntimeException {
    public TableNameIsNotDefinedException() {
        super();
    }

    public TableNameIsNotDefinedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TableNameIsNotDefinedException(String detailMessage) {
        super(detailMessage);
    }

    public TableNameIsNotDefinedException(Throwable throwable) {
        super(throwable);
    }
}

