/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis;

import com.googlecode.catchexception.throwable.MyThrowable;

/**
 * The Class MyThrowableCustomAssertions.
 */
public class MyThrowableCustomAssertions extends BDDCatchThrowable {

    /**
     * Then.
     *
     * @param actual
     *            the actual
     *
     * @return the my throwable custom assert
     */
    public static MyThrowableCustomAssert then(MyThrowable actual) {
        return new MyThrowableCustomAssert(actual);
    }

    /**
     * Caught throwable.
     *
     * @return the my throwable
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public static MyThrowable caughtThrowable() {
        return caughtThrowable(MyThrowable.class);
    }

}
