/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis;

import static com.googlecode.catchexception.throwable.apis.BDDCatchThrowable.when;
import static com.googlecode.catchexception.throwable.apis.MyThrowableCustomAssertions.caughtThrowable;
import static com.googlecode.catchexception.throwable.apis.MyThrowableCustomAssertions.then;

import com.googlecode.catchexception.throwable.MyThrowable;

import org.junit.jupiter.api.Test;

/**
 * Tests custom throwable assertions.
 */
class BDDCustomCatchThrowableTest {

    /**
     * Custom exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void customException() throws Exception {
        when(this::throwMyThrowable);
        then(caughtThrowable()).hasErrorCode(500);
    }

    /**
     * Throw my throwable.
     */
    private void throwMyThrowable() {
        throw new MyThrowable(500);
    }

}
