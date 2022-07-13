/**
 * Copyright (C) 2011 rwoo@gmx.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.catchexception.test.apis;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.thenThrown;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.googlecode.catchexception.CatchException;

/**
 * Tests {@link com.googlecode.catchexception.apis.BDDCatchException}.
 *
 * @author rwoo
 */
@SuppressWarnings("javadoc")
public class BDDCatchExceptionTest {

    /**
     * The message of the exception thrown by {@code new ArrayList<String>().get(0) } for jdk9on.
     */
    private final String expectedMessageJdk9on = "Index 1 out of bounds for length 0";

    /**
     * The message of the exception thrown for jdk9on for 500.
     */
    private final String expectedMessageJdk9on500 = "Index 500 out of bounds for length 9";

    @SuppressWarnings("rawtypes")
    @Test
    public void testThen() {
        // given an empty list
        List myList = new ArrayList();

        // when we try to get first element of the list
        when(() -> myList.get(1));

        // then we expect an IndexOutOfBoundsException
        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            then(caughtException())
                .isInstanceOf(IndexOutOfBoundsException.class) //
                .hasMessage("Index: 1, Size: 0") //
                .hasNoCause();
        }

        // and BDDAssertions....
        then(Integer.valueOf(2)).isEqualTo(2);
        then(new Exception()).hasMessage(null);

    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testAssertJThen() {
        // given an empty list
        List myList = new ArrayList();

        // when we try to get first element of the list
        when(() -> myList.get(1));

        // then we expect an IndexOutOfBoundsException
        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            then((Throwable) CatchException.caughtException())
                .isInstanceOf(IndexOutOfBoundsException.class) //
                .hasMessage("Index: 1, Size: 0") //
                .hasNoCause();
        }

        // and BDDAssertions....
        then(Integer.valueOf(2)).isEqualTo(2);
        then(new Exception()).hasMessage(null);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testThenThrown() {

        // given a list with nine elements
        List myList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // when we try to get the 500th element
        when(() -> myList.get(500));

        // then we expect an IndexOutOfBoundsException
        thenThrown(IndexOutOfBoundsException.class);

        // test: caughtException() ==null
        when(() -> myList.get(0));
        try {
            thenThrown(IndexOutOfBoundsException.class);

        } catch (AssertionError e) {
            assertEquals("Neither an exception of type java.lang."
                    + "IndexOutOfBoundsException nor another exception "
                    + "was thrown", e.getMessage());
        }

        // test: caughtException() is not IllegalArgumentException
        when(() -> myList.get(500));
        try {
            thenThrown(IllegalArgumentException.class);

        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on500)) {
                assertEquals("Exception of type java.lang.IllegalArgumentException"
                    + " expected but was not thrown. Instead an exception of"
                    + " type class java.lang.ArrayIndexOutOfBoundsException"
                    + " with message '500' was thrown.", e.getMessage());
            }
        }
    }
}
