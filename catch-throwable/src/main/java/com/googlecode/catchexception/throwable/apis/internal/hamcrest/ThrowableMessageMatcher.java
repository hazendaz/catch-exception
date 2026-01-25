/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis.internal.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Creates a {@link Matcher matcher} that matches an throwable with a certain message.
 *
 * @param <T>
 *            an throwable subclass
 */
public class ThrowableMessageMatcher<T extends Throwable> extends BaseMatcher<T> {

    /**
     * The string matcher that shall match throwable message.
     */
    private Matcher<String> expectedMessageMatcher;

    /**
     * Instantiates a new throwable message matcher.
     *
     * @param expectedMessage
     *            the expected throwable message
     */
    public ThrowableMessageMatcher(String expectedMessage) {
        this.expectedMessageMatcher = CoreMatchers.is(expectedMessage);
    }

    /**
     * Instantiates a new throwable message matcher.
     *
     * @param expectedMessageMatcher
     *            a string matcher that shall match the throwable message
     */
    public ThrowableMessageMatcher(Matcher<String> expectedMessageMatcher) {
        this.expectedMessageMatcher = expectedMessageMatcher;
    }

    @Override
    public boolean matches(Object obj) {
        if (!(obj instanceof Throwable)) {
            return false;
        }

        var throwable = (Throwable) obj;

        var foundMessage = throwable.getMessage();

        return expectedMessageMatcher.matches(foundMessage);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has a message that ").appendDescriptionOf(expectedMessageMatcher);
    }

}
