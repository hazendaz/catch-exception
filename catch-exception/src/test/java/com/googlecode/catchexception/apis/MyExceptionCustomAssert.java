/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.apis;

import com.googlecode.catchexception.MyException;

import org.assertj.core.api.AbstractThrowableAssert;

/**
 * The Class MyExceptionCustomAssert.
 */
public class MyExceptionCustomAssert extends AbstractThrowableAssert<MyExceptionCustomAssert, MyException> {

    /**
     * Instantiates a new my exception custom assert.
     *
     * @param actual
     *            the actual
     */
    protected MyExceptionCustomAssert(MyException actual) {
        super(actual, MyExceptionCustomAssert.class);
    }

    /**
     * Checks for error code.
     *
     * @param errorCode
     *            the error code
     *
     * @return the my exception custom assert
     */
    public MyExceptionCustomAssert hasErrorCode(int errorCode) {
        isNotNull();
        if (actual.getErrorCode() != errorCode) {
            failWithMessage("Expected myException's errorCode to be <%s> but was <%s>", errorCode,
                    actual.getErrorCode());
        }
        return this;
    }

}
