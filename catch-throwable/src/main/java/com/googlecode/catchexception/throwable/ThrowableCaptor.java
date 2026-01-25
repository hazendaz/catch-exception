/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable;

/**
 * The Class ThrowableCaptor.
 */
final class ThrowableCaptor {

    /**
     * Prevent Instantiation of a new throwable captor.
     */
    private ThrowableCaptor() {
        // Preventing instantiation
    }

    /**
     * Capture throwable.
     *
     * @param throwableThrower
     *            the throwable thrower
     *
     * @return the throwable
     */
    public static Throwable captureThrowable(ThrowingCallable throwableThrower) {
        try {
            throwableThrower.call();
            // not exception was thrown
            return null;
        } catch (Throwable caught) {
            return caught;
        }
    }

}
