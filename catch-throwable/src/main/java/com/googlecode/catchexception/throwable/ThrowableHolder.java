/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable;

import java.lang.ref.WeakReference;

/**
 * Holds a caught throwable {@link ThreadLocal per Thread}.
 */
final class ThrowableHolder {

    /**
     * Prevent Instantiation of a new throwable holder.
     */
    private ThrowableHolder() {
        // Preventing instantiation
    }

    /**
     * The container for the most recently caught throwable.
     * <p>
     * There is no need to use {@link WeakReference weak references} here as all the code is for testing so that we
     * don't have to care about memory leaks.
     */
    private static final ThreadLocal<Throwable> caughtThrowable = new ThreadLocal<>();

    /**
     * Saves the given throwable in {@link #caughtThrowable}.
     *
     * @param <E>
     *            the type of the caught throwable
     * @param caughtThrowable
     *            the caught throwable
     */
    public static <E extends Throwable> void set(E caughtThrowable) {
        ThrowableHolder.caughtThrowable.set(caughtThrowable);
    }

    /**
     * Gets the.
     *
     * @param <E>
     *            the type of the caught throwable
     *
     * @return Returns the caught throwable. Returns null if there is no throwable caught.
     */
    @SuppressWarnings("unchecked")
    public static <E extends Throwable> E get() {
        return (E) caughtThrowable.get();
    }

}
