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
package com.googlecode.catchexception;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.CatchException.verifyException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CatchException}.
 */
@SuppressWarnings("javadoc")
class CatchExceptionTest {

    private final List<String> list = new ArrayList<>();

    /**
     * The message of the exception thrown by {@code new ArrayList<String>().get(0) }.
     */
    private final String expectedMessage = "Index: 0, Size: 0";

    /**
     * The message of the exception thrown by {@code new ArrayList<String>().get(0) } for jdk9on.
     */
    private final String expectedMessageJdk9on = "Index 0 out of bounds for length 0";

    @BeforeEach
    void setUp() {
        // set any exception so that we have clear state before the test
        ExceptionHolder.set(new HttpRetryException("detail", 0));
    }

    @Test
    void catchExceptionObjExcNoExceptionThrown() {

        catchException(list::size, IndexOutOfBoundsException.class);
        assertNull(caughtException());
    }

    @Test
    void catchExceptionObjExcActualClassThrown() {

        // test for actual class
        catchException(() -> list.get(0), IndexOutOfBoundsException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    @Test
    void catchExceptionObjExcSubClassOfExpectedThrown() {

        // test for super class
        catchException(() -> list.get(0), RuntimeException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    @Test
    void catchExceptionObjExcSuperClassOfExpectedThrown() {

        try {
            catchException(() -> list.get(0), ArrayIndexOutOfBoundsException.class);
            fail("IndexOutOfBoundsException is expected (shall not be caught)");
        } catch (IndexOutOfBoundsException e) {
            assertNull(caughtException());
        }
    }

    @Test
    void catchExceptionObjExcOtherClassThanExpectedThrown() {

        try {
            catchException(() -> list.get(0), IllegalArgumentException.class);
            fail("IndexOutOfBoundsException is expected (shall not be caught)");
        } catch (IndexOutOfBoundsException e) {
            assertNull(caughtException());
        }
    }

    @Test
    void catchExceptionObjExcMissingArgumentException() {

        // test validation of the arguments
        try {
            catchException(() -> list.get(0), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("exceptionClazz must not be null", e.getMessage());
        }
    }

    @Test
    void catchExceptionObjExcMissingArgumentObject() {

        try {
            catchException(null, IllegalArgumentException.class);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    @Test
    void verifyExceptionObjExcNoExceptionThrown() {

        try {
            verifyException(list::size, IndexOutOfBoundsException.class);
            fail("ExceptionNotThrownAssertionError is expected");
        } catch (ExceptionNotThrownAssertionError e) {
            assertNull(caughtException());
            assertEquals("Neither an exception of type " + IndexOutOfBoundsException.class.getName()
                    + " nor another exception was thrown", e.getMessage());
        }

    }

    @Test
    void verifyExceptionObjExcActualClassThrown() {

        // test for actual class
        verifyException(() -> list.get(0), IndexOutOfBoundsException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    @Test
    void verifyExceptionObjExcSubClassOfExpectedThrown() {

        // test for super class
        verifyException(() -> list.get(0), RuntimeException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    @Test
    void verifyExceptionObjExcSuperClassOfExpectedThrown() {

        // test for sub class
        try {
            verifyException(() -> list.get(0), ArrayIndexOutOfBoundsException.class);
            fail("ExceptionNotThrownAssertionError is expected");
        } catch (ExceptionNotThrownAssertionError e) {
            assertNull(caughtException());
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertEquals("Exception of type " + ArrayIndexOutOfBoundsException.class.getName()
                        + " expected but was not thrown." + " Instead an exception of type "
                        + IndexOutOfBoundsException.class + " with message '" + expectedMessage + "' was thrown.",
                        e.getMessage());
            }
        }
    }

    @Test
    void verifyExceptionObjExcOtherClassThanExpectedThrown() {

        // test for other exception type
        try {
            verifyException(() -> list.get(0), IllegalArgumentException.class);
            fail("ExceptionNotThrownAssertionError is expected");
        } catch (ExceptionNotThrownAssertionError e) {
            assertNull(caughtException());
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertEquals("Exception of type " + IllegalArgumentException.class.getName()
                        + " expected but was not thrown." + " Instead an exception of type "
                        + IndexOutOfBoundsException.class + " with message '" + expectedMessage + "' was thrown.",
                        e.getMessage());
            }
        }
    }

    // fixme
    @Test
    void verifyExceptionObjExcMissingArgumentException() {

        // test validation of the arguments
        try {
            verifyException(() -> list.get(0), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("exceptionClazz must not be null", e.getMessage());
        }
    }

    @Test
    void verifyExceptionObjExcMissingArgumentObject() {

        try {
            verifyException(null, IllegalArgumentException.class);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    @Test
    void verifyExceptionObjNoExceptionThrown() {

        List<String> list = new ArrayList<>();

        // no exception thrown by size()
        try {
            verifyException(list::size);
            fail("ExceptionNotThrownAssertionError is expected");
        } catch (ExceptionNotThrownAssertionError e) {
            assertNull(caughtException());
            assertEquals("Exception expected but not thrown", e.getMessage());
        }
    }

    @Test
    void verifyExceptionObjExceptionThrown() {

        List<String> list = new ArrayList<>();

        verifyException(() -> list.get(0));
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    @Test
    void verifyExceptionObjMissingArgumentObject() {

        // test validation of the arguments
        try {
            verifyException(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    @Test
    void catchExceptionObjNoExceptionThrown() {

        List<String> list = new ArrayList<>();

        // no exception thrown by size()
        catchException(list::size);
        assertNull(caughtException());
    }

    @Test
    void catchExceptionObjExceptionThrown() {

        List<String> list = new ArrayList<>();

        catchException(() -> list.get(0));
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    @Test
    void catchExceptionObjMissingArgumentObject() {

        // test validation of the arguments
        try {
            catchException(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    @Test
    void testProtected() {
        PublicSomethingImpl obj = new PublicSomethingImpl();
        catchException(obj::dooo);
        assertTrue(caughtException() instanceof MyException);
    }

}
