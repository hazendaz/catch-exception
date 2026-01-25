/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis;

import com.googlecode.catchexception.throwable.MyThrowable;

import org.assertj.core.api.AbstractThrowableAssert;

/**
 * The Class MyThrowableCustomAssert.
 */
public class MyThrowableCustomAssert extends AbstractThrowableAssert<MyThrowableCustomAssert, MyThrowable> {

    /**
     * Instantiates a new my throwable custom assert.
     *
     * @param actual
     *            the actual
     */
    protected MyThrowableCustomAssert(MyThrowable actual) {
        super(actual, MyThrowableCustomAssert.class);
    }

    /**
     * Checks for error code.
     *
     * @param errorCode
     *            the error code
     *
     * @return the my throwable custom assert
     */
    public MyThrowableCustomAssert hasErrorCode(int errorCode) {
        isNotNull();
        if (actual.getErrorCode() != errorCode) {
            failWithMessage("Expected myThrowable's errorCode to be <%s> but was <%s>", errorCode,
                    actual.getErrorCode());
        }
        return this;
    }

}
