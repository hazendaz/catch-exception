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
package com.googlecode.catchexception.throwable.matcher;

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
