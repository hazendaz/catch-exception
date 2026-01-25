/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.apis;

import com.googlecode.catchexception.CatchException;
import com.googlecode.catchexception.ExceptionNotThrownAssertionError;

/**
 * The Class CatchExceptionUtils.
 */
final class CatchExceptionUtils {

    /**
     * Prevents Instantiation of a new catch exception utils.
     */
    private CatchExceptionUtils() {
        // Prevent instantiation
    }

    /**
     * Then thrown.
     *
     * @param actualExceptionClazz
     *            the actual exception clazz
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void thenThrown(Class actualExceptionClazz) {
        var e = CatchException.caughtException();
        if (e == null) {
            // no exception caught -> assertion failed
            throw new ExceptionNotThrownAssertionError(actualExceptionClazz);
        }
        if (!actualExceptionClazz.isAssignableFrom(CatchException.caughtException().getClass())) {
            // caught exception is of wrong type -> assertion failed
            throw new ExceptionNotThrownAssertionError(actualExceptionClazz, e);
        }
    }

}
