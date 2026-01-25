/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception;

/**
 * Code borrowed from famous Spock Framework.
 */
final class ExceptionUtil {

    /**
     * Prevents Instantiation of a new exception util.
     */
    private ExceptionUtil() {
        // Prevent instantiation
    }

    /**
     * Allows to throw an unchecked exception without declaring it in a throws clause.
     *
     * @param t
     *            exception to throw
     */
    public static void sneakyThrow(Exception t) {
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
    private static <T extends Exception> void doSneakyThrow(Exception t) throws T {
        throw (T) t;
    }

}
