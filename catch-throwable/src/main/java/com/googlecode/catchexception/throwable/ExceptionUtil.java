/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable;

/**
 * Code borrowed from famous Spock Framework.
 */
final class ExceptionUtil {

    /**
     * Prevent Instantiation of a new exception util.
     */
    private ExceptionUtil() {
        // Preventing instantiation
    }

    /**
     * Allows to throw an unchecked exception without declaring it in a throws clause.
     *
     * @param t
     *            throwable to throw
     */
    public static void sneakyThrow(Throwable t) {
        ExceptionUtil.doSneakyThrow(t);
    }

    /**
     * Do sneaky throw.
     *
     * @param <T>
     *            the generic type
     * @param t
     *            the t
     *
     * @throws T
     *             the t
     */
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void doSneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

}
