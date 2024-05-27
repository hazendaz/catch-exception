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

import com.googlecode.catchexception.apis.internal.hamcrest.ExceptionMessageMatcher;
import com.googlecode.catchexception.apis.internal.hamcrest.ExceptionNoCauseMatcher;

import org.hamcrest.Matcher;

/**
 * Provides some Hamcrest {@link Matcher matchers} to match some {@link Exception exception} properties.
 * <p>
 * EXAMPLE: {@code
 * // given an empty list
 * List myList = new ArrayList();
 *
 * // when we try to get the first element of the list
 * catchException(myList).get(1);
 *
 * // then we expect an IndexOutOfBoundsException with message "Index: 1, Size: 0"
 * assertThat(caughtException(),
 * allOf(
 *   is(IndexOutOfBoundsException.class),
 *   hasMessage("Index: 1, Size: 0"),
 *   hasNoCause()
 * )
 * );
 * }
 * <p>
 * To combine the standard Hamcrest matchers, your custom matchers, these matchers, and other matcher collections (as
 * JUnitMatchers in a single class follow the instructions outlined in
 * <a href="http://code.google.com/p/hamcrest/wiki/Tutorial#Sugar_generation">Sugar generation</a>.
 */
public final class CatchExceptionHamcrestMatchers {

    /**
     * Prevent Instantiation of a new catch exception hamcrest matchers.
     */
    private CatchExceptionHamcrestMatchers() {
        // Preventing instantiation
    }

    /**
     * EXAMPLE: <code>assertThat(caughtException(), hasMessage("Index: 9, Size: 9"));</code>.
     *
     * @param <T>
     *            the exception subclass
     * @param expectedMessage
     *            the expected exception message
     *
     * @return Returns a matcher that matches an exception if it has the given message.
     */
    public static <T extends Exception> Matcher<T> hasMessage(String expectedMessage) {
        return new ExceptionMessageMatcher<>(expectedMessage);
    }

    /**
     * EXAMPLES: <code>assertThat(caughtException(), hasMessageThat(is("Index: 9, Size: 9")));
     *     assertThat(caughtException(), hasMessageThat(containsString("Index: 9"))); // using JUnitMatchers
     *     assertThat(caughtException(), hasMessageThat(containsPattern("Index: \\d+"))); // using Mockito's Find</code>.
     *
     * @param <T>
     *            the exception subclass
     * @param stringMatcher
     *            a string matcher
     *
     * @return Returns a matcher that matches an exception if the given string matcher matches the exception message.
     */
    public static <T extends Exception> Matcher<T> hasMessageThat(Matcher<String> stringMatcher) {
        return new ExceptionMessageMatcher<>(stringMatcher);
    }

    /**
     * EXAMPLE: <code>assertThat(caughtException(), hasNoCause());</code>.
     *
     * @param <T>
     *            the exception subclass
     *
     * @return Returns a matcher that matches the exception if it does not have a {@link Throwable#getCause() cause}.
     */
    public static <T extends Exception> Matcher<T> hasNoCause() {
        return new ExceptionNoCauseMatcher<>();
    }

}
