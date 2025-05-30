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
import com.googlecode.catchexception.ThrowingCallable;

/**
 * Supports <a href="http://en.wikipedia.org/wiki/Behavior_Driven_Development">BDD</a>-like approach to catch and verify
 * exceptions (<i>given/when/then</i>).
 * <p>
 * EXAMPLE: {@code
 *
 * import static org.assertj.core.api.BDDAssertions.then;
 *
 * // given an empty list
 * List myList = new ArrayList();
 *
 * // when we try to get the first element of the list
 * when(myList).get(1);
 *
 * // then we expect an IndexOutOfBoundsException
 * then(caughtException())
 *     .isInstanceOf(IndexOutOfBoundsException.class)
 *     .hasMessage("Index: 1, Size: 0")
 *     .hasNoCause();
 *
 * // then we expect an IndexOutOfBoundsException (alternatively)
 * thenThrown(IndexOutOfBoundsException.class);
 * }
 */
public class BDDCatchException {

    /**
     * When.
     *
     * @param actor
     *            The instance that shall be proxied. Must not be <code>null</code>.
     *
     * @see com.googlecode.catchexception.CatchException#catchException(ThrowingCallable)
     */
    public static void when(ThrowingCallable actor) {
        CatchException.catchException(actor);
    }

    /**
     * Returns the exception caught during the last call in the current thread. This is useful for returning business
     * specific exception to specific assertions.
     *
     * @return Returns the exception caught during the last call in the current thread - if the call was made through a
     *         proxy that has been created via {@link #when(ThrowingCallable)} . Returns null when no exception was
     *         caught.
     */
    public static Exception caughtException() {
        return CatchException.caughtException();
    }

    /**
     * Caught exception.
     *
     * @param <E>
     *            the element type
     * @param caughtExceptionType
     *            the caught exception type
     *
     * @return the e
     *
     * @deprecated Use CatchException.caughtException() instead as there is no usage of caughtExceptionType
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public static <E extends Exception> E caughtException(Class<E> caughtExceptionType) {
        return CatchException.caughtException(caughtExceptionType);
    }

    /**
     * Throws an assertion if no exception is thrown or if an exception of an unexpected type is thrown.
     * <p>
     * EXAMPLE: {@code
     *
     * // given a list with nine members
     * List myList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
     *
     * // when we try to get the 500th member of the fellowship
     * when(myList).get(500);
     *
     * // then we expect an IndexOutOfBoundsException
     * thenThrown(IndexOutOfBoundsException.class);
     * }
     *
     * @param actualExceptionClazz
     *            the expected type of the caught exception.
     */
    @SuppressWarnings("rawtypes")
    public static void thenThrown(Class actualExceptionClazz) {
        CatchExceptionUtils.thenThrown(actualExceptionClazz);
    }

}
