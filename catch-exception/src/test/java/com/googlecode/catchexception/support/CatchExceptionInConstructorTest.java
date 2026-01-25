/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.support;

import static com.googlecode.catchexception.CatchException.verifyException;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

/**
 * Demonstrates how to catch exceptions that are thrown by constructors.
 */
class CatchExceptionInConstructorTest {

    /**
     * The Class Thing.
     */
    private static class Thing {

        /**
         * Instantiates a new thing.
         *
         * @param data
         *            the data
         */
        public Thing(String data) {
            throw new IllegalArgumentException("bad data: " + data);
        }
    }

    /**
     * Exception thrown in constructor.
     */
    @Test
    void exceptionThrownInConstructor() {

        Supplier<Thing> builder = () -> new Thing("baddata");
        verifyException(builder::get, IllegalArgumentException.class);
    }

}
