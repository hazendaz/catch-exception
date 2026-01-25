/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception;

/**
 * The Class ExceptionCaptor.
 */
final class ExceptionCaptor {

    /**
     * Prevents Instantiation of a new exception captor.
     */
    private ExceptionCaptor() {
        // Prevent instantiation
    }

    /**
     * Capture throwable.
     *
     * @param exceptionThrower
     *            the exception thrower
     *
     * @return the exception
     */
    public static Exception captureThrowable(ThrowingCallable exceptionThrower) {
        try {
            exceptionThrower.call();
            // not exception was thrown
            return null;
        } catch (Exception caught) {
            return caught;
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(
                    "Throwable is not supported by CatchException library, " + "use CatchThrowable instead");
        }
    }

}
