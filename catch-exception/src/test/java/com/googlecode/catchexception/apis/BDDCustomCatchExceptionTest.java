/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.apis;

import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static com.googlecode.catchexception.apis.MyExceptionCustomAssertions.caughtException;
import static com.googlecode.catchexception.apis.MyExceptionCustomAssertions.then;

import com.googlecode.catchexception.MyException;

import org.junit.jupiter.api.Test;

/**
 * Tests custom exception assertions.
 */
class BDDCustomCatchExceptionTest {

    /**
     * Custom exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void customException() throws Exception {
        when(this::throwMyException);
        then(caughtException()).hasErrorCode(500);
    }

    /**
     * Throw my exception.
     */
    private void throwMyException() {
        throw new MyException(500);
    }

}
