/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis;

import com.googlecode.catchexception.throwable.CatchThrowable;
import com.googlecode.catchexception.throwable.ThrowableNotThrownAssertionError;

/**
 * The Class CatchThrowableUtils.
 */
final class CatchThrowableUtils {

    /**
     * Prevent Instantiation of a new catch throable utils.
     */
    private CatchThrowableUtils() {
        // Preventing instantiation
    }

    /**
     * Then thrown.
     *
     * @param actualThrowableClazz
     *            the actual throwable clazz
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void thenThrown(Class actualThrowableClazz) {
        var e = CatchThrowable.caughtThrowable();
        if (e == null) {
            // no throwable caught -> assertion failed
            throw new ThrowableNotThrownAssertionError(actualThrowableClazz);
        }
        if (!actualThrowableClazz.isAssignableFrom(CatchThrowable.caughtThrowable().getClass())) {
            // caught throwable is of wrong type -> assertion failed
            throw new ThrowableNotThrownAssertionError(actualThrowableClazz, e);
        }
    }

}
