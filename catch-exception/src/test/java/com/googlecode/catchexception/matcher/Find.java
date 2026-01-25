/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.matcher;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Copied from Mockito internal.
 */
public class Find extends BaseMatcher<String> implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8895781429480404872L;

    /** The regex. */
    private final String regex;

    /**
     * Instantiates a new find.
     *
     * @param regex
     *            the regex
     */
    public Find(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean matches(Object actual) {
        return actual != null && Pattern.compile(regex).matcher((String) actual).find();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("find(\"" + regex.replace("\\", "\\\\") + "\")");
    }

}
