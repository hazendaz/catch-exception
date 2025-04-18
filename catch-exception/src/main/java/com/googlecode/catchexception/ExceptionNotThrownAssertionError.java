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

import java.io.Serializable;

/**
 * Thrown if a method has not thrown an exception of the expected type.
 */
public class ExceptionNotThrownAssertionError extends AssertionError {

    /**
     * See {@link Serializable}.
     */
    private static final long serialVersionUID = 7044423604241785057L;

    /**
     * Use this constructor if neither an exception of the expected type nor another exception is thrown.
     *
     * @param <E>
     *            the type of the exception that is not thrown.
     * @param clazz
     *            the type of the exception that is not thrown.
     */
    public <E extends Exception> ExceptionNotThrownAssertionError(Class<E> clazz) {
        super(clazz == Exception.class ? "Exception expected but not thrown"
                : "Neither an exception of type " + clazz.getName() + " nor another exception was thrown");
    }

    /**
     * Use this constructor if an exception of another than the expected type is thrown.
     *
     * @param <E>
     *            the type of the exception that is not thrown.
     * @param clazz
     *            the type of the exception that is not thrown.
     * @param e
     *            the exception that has been thrown instead of the expected one.
     */
    public <E extends Exception> ExceptionNotThrownAssertionError(Class<E> clazz, Exception e) {
        super("Exception of type " + clazz.getName() + " expected but was not thrown. "
                + "Instead an exception of type " + e.getClass() + " with message '" + e.getMessage()
                + "' was thrown.");
    }

}
