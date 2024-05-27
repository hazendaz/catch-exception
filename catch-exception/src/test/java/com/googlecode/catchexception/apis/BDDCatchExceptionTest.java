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
package com.googlecode.catchexception.apis;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.googlecode.catchexception.CatchException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link com.googlecode.catchexception.apis.BDDCatchException}.
 */
class BDDCatchExceptionTest {

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
        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            BDDAssertions.then(caughtException()).isInstanceOf(IndexOutOfBoundsException.class) //
                    .hasMessage("Index: 1, Size: 0") //
                    .hasNoCause();
        }

        // and BDDAssertions....
        BDDAssertions.then(Integer.valueOf(2)).isEqualTo(2);
        BDDAssertions.then(new Exception()).hasMessage(null);

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
        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            BDDAssertions.then((Throwable) CatchException.caughtException())
                    .isInstanceOf(IndexOutOfBoundsException.class) //
                    .hasMessage("Index: 1, Size: 0") //
                    .hasNoCause();
        }

        // and BDDAssertions....
        BDDAssertions.then(Integer.valueOf(2)).isEqualTo(2);
        BDDAssertions.then(new Exception()).hasMessage(null);
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
        BDDCatchException.thenThrown(IndexOutOfBoundsException.class);

        // test: caughtException() ==null
        when(() -> myList.get(0));
        try {
            BDDCatchException.thenThrown(IndexOutOfBoundsException.class);

        } catch (AssertionError e) {
            assertEquals("Neither an exception of type java.lang." + "IndexOutOfBoundsException nor another exception "
                    + "was thrown", e.getMessage());
        }

        // test: caughtException() is not IllegalArgumentException
        when(() -> myList.get(500));
        try {
            BDDCatchException.thenThrown(IllegalArgumentException.class);

        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on500)) {
                assertEquals("Exception of type java.lang.IllegalArgumentException"
                        + " expected but was not thrown. Instead an exception of"
                        + " type class java.lang.ArrayIndexOutOfBoundsException" + " with message '500' was thrown.",
                        e.getMessage());
            }
        }
    }

}
