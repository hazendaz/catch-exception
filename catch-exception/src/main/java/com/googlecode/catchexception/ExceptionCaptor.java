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
