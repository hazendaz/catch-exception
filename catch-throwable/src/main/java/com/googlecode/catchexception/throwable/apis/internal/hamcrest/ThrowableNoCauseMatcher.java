/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis.internal.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Creates a {@link Matcher matcher} that matches an throwable that has no {@link Throwable#getCause() cause}.
 *
 * @param <T>
 *            an throwable subclass
 */
public class ThrowableNoCauseMatcher<T extends Throwable> extends BaseMatcher<T> {

    @Override
    public boolean matches(Object obj) {
        if (!(obj instanceof Throwable)) {
            return false;
        }

        var throwable = (Throwable) obj;

        return throwable.getCause() == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has no cause");
    }

}
