/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.apis;

import com.googlecode.catchexception.MyException;

/**
 * The Class MyExceptionCustomAssertions.
 */
public class MyExceptionCustomAssertions extends BDDCatchException {

    /**
     * Then.
     *
     * @param actual
     *            the actual
     *
     * @return the my exception custom assert
     */
    public static MyExceptionCustomAssert then(MyException actual) {
        return new MyExceptionCustomAssert(actual);
    }

    /**
     * Caught exception.
     *
     * @return the my exception
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public static MyException caughtException() {
        return caughtException(MyException.class);
    }

}
