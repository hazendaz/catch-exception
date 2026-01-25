/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
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
