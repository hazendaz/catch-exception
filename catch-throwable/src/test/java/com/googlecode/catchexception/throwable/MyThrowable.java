/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable;

/**
 * The Class MyThrowable.
 */
public class MyThrowable extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The error code. */
    private final int errorCode;

    /**
     * Instantiates a new my throwable.
     *
     * @param errorCode
     *            the error code
     */
    public MyThrowable(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

}
