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

import com.googlecode.catchexception.throwable.apis.internal.hamcrest.ThrowableMessageMatcher;
import com.googlecode.catchexception.throwable.apis.internal.hamcrest.ThrowableNoCauseMatcher;

import org.hamcrest.Matcher;

/**
 * Provides some Hamcrest {@link Matcher matchers} to match some {@link Throwable throwable} properties.
 * <p>
 * EXAMPLE: <code>// given an empty list
List myList = new ArrayList();

// when we try to get the first element of the list
catchThrowable(myList).get(1);

// then we expect an IndexOutOfBoundsThrowable with message "Index: 1, Size: 0"
assertThat(caughtThrowable(),
  allOf(
    is(IndexOutOfBoundsThrowable.class),
    hasMessage("Index: 1, Size: 0"),
    hasNoCause()
  )
);</code>
 * <p>
 * To combine the standard Hamcrest matchers, your custom matchers, these matchers, and other matcher collections (as
 * JUnitMatchers in a single class follow the instructions outlined in
 * <a href="http://code.google.com/p/hamcrest/wiki/Tutorial#Sugar_generation">Sugar generation</a>.
 */
public final class CatchThrowableHamcrestMatchers {

    /**
     * Prevent Instantiation of a new catch throwable hamcrest matchers.
     */
    private CatchThrowableHamcrestMatchers() {
        // Preventing instantiation
    }

    /**
     * EXAMPLE: <code>assertThat(caughtThrowable(), hasMessage("Index: 9, Size: 9"));</code>.
     *
     * @param <T>
     *            the throwable subclass
     * @param expectedMessage
     *            the expected throwable message
     *
     * @return Returns a matcher that matches an throwable if it has the given message.
     */
    public static <T extends Throwable> Matcher<T> hasMessage(String expectedMessage) {
        return new ThrowableMessageMatcher<>(expectedMessage);
    }

    /**
     * EXAMPLES: <code>assertThat(caughtThrowable(), hasMessageThat(is("Index: 9, Size: 9")));
     *     assertThat(caughtThrowable(), hasMessageThat(containsString("Index: 9"))); // using JUnitMatchers
     *     assertThat(caughtThrowable(), hasMessageThat(containsPattern("Index: \\d+"))); // using Mockito's Find</code>.
     *
     * @param <T>
     *            the throwable subclass
     * @param stringMatcher
     *            a string matcher
     *
     * @return Returns a matcher that matches an throwable if the given string matcher matches the throwable message.
     */
    public static <T extends Throwable> Matcher<T> hasMessageThat(Matcher<String> stringMatcher) {
        return new ThrowableMessageMatcher<>(stringMatcher);
    }

    /**
     * EXAMPLE: <code>assertThat(caughtThrowable(), hasNoCause());</code>.
     *
     * @param <T>
     *            the throwable subclass
     *
     * @return Returns a matcher that matches the throwable if it does not have a {@link Throwable#getCause() cause}.
     */
    public static <T extends Throwable> Matcher<T> hasNoCause() {
        return new ThrowableNoCauseMatcher<>();
    }

}
