/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception;

import java.lang.ref.WeakReference;

/**
 * Holds a caught exception {@link ThreadLocal per Thread}.
 */
final class ExceptionHolder {

    /**
     * Prevents Instantiation of a new exception holder.
     */
    private ExceptionHolder() {
        // Prevent instantiation
    }

    /**
     * The container for the most recently caught exception.
     * <p>
     * There is no need to use {@link WeakReference weak references} here as all the code is for testing so that we
     * don't have to care about memory leaks.
     */
    private static final ThreadLocal<Exception> caughtException = new ThreadLocal<>();

    /**
     * Saves the given exception in {@link #caughtException}.
     *
     * @param <E>
     *            the type of the caught exception
     * @param caughtException
     *            the caught exception
     */
    public static <E extends Exception> void set(E caughtException) {
        ExceptionHolder.caughtException.set(caughtException);
    }

    /**
     * Gets the.
     *
     * @param <E>
     *            the type of the caught exception
     *
     * @return Returns the caught exception. Returns null if there is no exception caught.
     */
    @SuppressWarnings("unchecked")
    public static <E extends Exception> E get() {
        return (E) caughtException.get();
    }

}
