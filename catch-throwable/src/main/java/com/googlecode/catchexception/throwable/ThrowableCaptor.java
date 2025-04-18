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
