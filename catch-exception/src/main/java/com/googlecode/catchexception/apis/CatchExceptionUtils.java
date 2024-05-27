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
package com.googlecode.catchexception.apis;

import com.googlecode.catchexception.CatchException;
import com.googlecode.catchexception.ExceptionNotThrownAssertionError;

/**
 * The Class CatchExceptionUtils.
 */
final class CatchExceptionUtils {

    /**
     * Prevents Instantiation of a new catch exception utils.
     */
    private CatchExceptionUtils() {
        // Prevent instantiation
    }

    /**
     * Then thrown.
     *
     * @param actualExceptionClazz
     *            the actual exception clazz
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void thenThrown(Class actualExceptionClazz) {
        var e = CatchException.caughtException();
        if (e == null) {
            // no exception caught -> assertion failed
            throw new ExceptionNotThrownAssertionError(actualExceptionClazz);
        }
        if (!actualExceptionClazz.isAssignableFrom(CatchException.caughtException().getClass())) {
            // caught exception is of wrong type -> assertion failed
            throw new ExceptionNotThrownAssertionError(actualExceptionClazz, e);
        }
    }
}
