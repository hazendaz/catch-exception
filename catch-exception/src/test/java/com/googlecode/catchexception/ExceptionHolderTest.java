/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ExceptionHolder}.
 */
class ExceptionHolderTest {

    /**
     * Caught exception is null.
     */
    @Test
    void caughtExceptionIsNull() {
        ExceptionHolder.set(null);
        assertNull(ExceptionHolder.get());
    }

}
