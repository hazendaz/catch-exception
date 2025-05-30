/*
 * Copyright 2011-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
