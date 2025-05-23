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
