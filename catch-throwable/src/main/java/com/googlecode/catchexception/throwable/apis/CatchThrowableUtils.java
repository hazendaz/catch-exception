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
package com.googlecode.catchexception.throwable.apis;

import com.googlecode.catchexception.throwable.CatchThrowable;
import com.googlecode.catchexception.throwable.ThrowableNotThrownAssertionError;

/**
 * The Class CatchThrowableUtils.
 */
final class CatchThrowableUtils {

    /**
     * Prevent Instantiation of a new catch throable utils.
     */
    private CatchThrowableUtils() {
        // Preventing instantiation
    }

    /**
     * Then thrown.
     *
     * @param actualThrowableClazz
     *            the actual throwable clazz
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void thenThrown(Class actualThrowableClazz) {
        var e = CatchThrowable.caughtThrowable();
        if (e == null) {
            // no throwable caught -> assertion failed
            throw new ThrowableNotThrownAssertionError(actualThrowableClazz);
        }
        if (!actualThrowableClazz.isAssignableFrom(CatchThrowable.caughtThrowable().getClass())) {
            // caught throwable is of wrong type -> assertion failed
            throw new ThrowableNotThrownAssertionError(actualThrowableClazz, e);
        }
    }

}
