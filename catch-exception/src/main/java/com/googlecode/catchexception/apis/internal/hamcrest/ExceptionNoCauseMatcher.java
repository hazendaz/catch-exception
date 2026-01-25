/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.apis.internal.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Creates a {@link Matcher matcher} that matches an exception that has no {@link Throwable#getCause() cause}.
 *
 * @param <T>
 *            an exception subclass
 */
public class ExceptionNoCauseMatcher<T extends Exception> extends BaseMatcher<T> {

    @Override
    public boolean matches(Object obj) {
        if (!(obj instanceof Exception)) {
            return false;
        }

        var exception = (Exception) obj;

        return exception.getCause() == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has no cause");
    }

}
