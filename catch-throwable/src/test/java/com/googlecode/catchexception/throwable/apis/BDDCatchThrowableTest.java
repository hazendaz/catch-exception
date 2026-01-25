/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis;

import static com.googlecode.catchexception.throwable.apis.BDDCatchThrowable.caughtThrowable;
import static com.googlecode.catchexception.throwable.apis.BDDCatchThrowable.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link com.googlecode.catchexception.throwable.apis.BDDCatchThrowable}.
 */
class BDDCatchThrowableTest {

    /**
     * The message of the exception thrown by {@code new ArrayList<String>().get(0) } for jdk9on.
     */
    private final String expectedMessageJdk9on = "Index 1 out of bounds for length 0";

    /**
     * The message of the exception thrown for jdk9on for 500.
     */
    private final String expectedMessageJdk9on500 = "Index 500 out of bounds for length 9";

    /**
     * Then.
     */
    @SuppressWarnings("rawtypes")
    @Test
    void then() {
        // given an empty list
        List myList = new ArrayList();

        // when we try to get first element of the list
        when(() -> myList.get(1));

        // then we expect an IndexOutOfBoundsException
        if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
            BDDAssertions.then(caughtThrowable()) //
                    .isInstanceOf(IndexOutOfBoundsException.class) //
                    .hasMessage("Index: 1, Size: 0") //
                    .hasNoCause();
        }

    }

    /**
     * Assert J then.
     */
    @SuppressWarnings("rawtypes")
    @Test
    void assertJThen() {
        // given an empty list
        List myList = new ArrayList();

        // when we try to get first element of the list
        when(() -> myList.get(1));

        // then we expect an IndexOutOfBoundsException
        if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
            BDDAssertions.then(caughtThrowable()).isInstanceOf(IndexOutOfBoundsException.class) //
                    .hasMessage("Index: 1, Size: 0") //
                    .hasNoCause();
        }

    }

    /**
     * Then thrown.
     */
    @SuppressWarnings("rawtypes")
    @Test
    void thenThrown() {

        // given a list with nine elements
        List myList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // when we try to get the 500th element
        when(() -> myList.get(500));

        // then we expect an IndexOutOfBoundsException
        BDDCatchThrowable.thenThrown(IndexOutOfBoundsException.class);

        // test: caughtThrowable() ==null
        when(() -> myList.get(0));
        try {
            BDDCatchThrowable.thenThrown(IndexOutOfBoundsException.class);

        } catch (AssertionError e) {
            assertEquals("Neither a throwable of type java.lang." + "IndexOutOfBoundsException nor another throwable "
                    + "was thrown", e.getMessage());
        }

        // test: caughtThrowable() is not IllegalArgumentException
        when(() -> myList.get(500));
        try {
            BDDCatchThrowable.thenThrown(IllegalArgumentException.class);

        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on500)) {
                assertEquals("Throwable of type java.lang.IllegalArgumentException"
                        + " expected but was not thrown. Instead a throwable of"
                        + " type class java.lang.ArrayIndexOutOfBoundsException" + " with message '500' was "
                        + "thrown.", e.getMessage());
            }
        }

    }

}
