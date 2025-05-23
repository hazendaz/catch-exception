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
package com.googlecode.catchexception.apis.internal.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Creates a {@link Matcher matcher} that matches an exception with a certain message.
 *
 * @param <T>
 *            an exception subclass
 */
public class ExceptionMessageMatcher<T extends Exception> extends BaseMatcher<T> {

    /**
     * The string matcher that shall match exception message.
     */
    private Matcher<String> expectedMessageMatcher;

    /**
     * Instantiates a new exception message matcher.
     *
     * @param expectedMessage
     *            the expected exception message
     */
    public ExceptionMessageMatcher(String expectedMessage) {
        this.expectedMessageMatcher = CoreMatchers.is(expectedMessage);
    }

    /**
     * Instantiates a new exception message matcher.
     *
     * @param expectedMessageMatcher
     *            a string matcher that shall match the exception message
     */
    public ExceptionMessageMatcher(Matcher<String> expectedMessageMatcher) {
        this.expectedMessageMatcher = expectedMessageMatcher;
    }

    @Override
    public boolean matches(Object obj) {
        if (!(obj instanceof Exception)) {
            return false;
        }

        var exception = (Exception) obj;

        var foundMessage = exception.getMessage();

        return expectedMessageMatcher.matches(foundMessage);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has a message that ").appendDescriptionOf(expectedMessageMatcher);
    }

}
