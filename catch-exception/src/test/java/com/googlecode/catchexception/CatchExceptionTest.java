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
class CatchExceptionTest {

    /** The list. */
    private final List<String> list = new ArrayList<>();

    /**
     * The message of the exception thrown by {@code new ArrayList<String>().get(0) }.
     */
    private final String expectedMessage = "Index: 0, Size: 0";

    /**
     * The message of the exception thrown by {@code new ArrayList<String>().get(0) } for jdk9on.
     */
    private final String expectedMessageJdk9on = "Index 0 out of bounds for length 0";

    /**
     * Sets the up.
     */
    @BeforeEach
    void setUp() {
        // set any exception so that we have clear state before the test
        ExceptionHolder.set(new HttpRetryException("detail", 0));
    }

    /**
     * Catch exception obj exc no exception thrown.
     */
    @Test
    void catchExceptionObjExcNoExceptionThrown() {

        catchException(list::size, IndexOutOfBoundsException.class);
        assertNull(caughtException());
    }

    /**
     * Catch exception obj exc actual class thrown.
     */
    @Test
    void catchExceptionObjExcActualClassThrown() {

        // test for actual class
        catchException(() -> list.get(0), IndexOutOfBoundsException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    /**
     * Catch exception obj exc sub class of expected thrown.
     */
    @Test
    void catchExceptionObjExcSubClassOfExpectedThrown() {

        // test for super class
        catchException(() -> list.get(0), RuntimeException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    /**
     * Catch exception obj exc super class of expected thrown.
     */
    @Test
    void catchExceptionObjExcSuperClassOfExpectedThrown() {

        try {
            catchException(() -> list.get(0), ArrayIndexOutOfBoundsException.class);
            fail("IndexOutOfBoundsException is expected (shall not be caught)");
        } catch (IndexOutOfBoundsException e) {
            assertNull(caughtException());
        }
    }

    /**
     * Catch exception obj exc other class than expected thrown.
     */
    @Test
    void catchExceptionObjExcOtherClassThanExpectedThrown() {

        try {
            catchException(() -> list.get(0), IllegalArgumentException.class);
            fail("IndexOutOfBoundsException is expected (shall not be caught)");
        } catch (IndexOutOfBoundsException e) {
            assertNull(caughtException());
        }
    }

    /**
     * Catch exception obj exc missing argument exception.
     */
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

    /**
     * Catch exception obj exc missing argument object.
     */
    @Test
    void catchExceptionObjExcMissingArgumentObject() {

        try {
            catchException(null, IllegalArgumentException.class);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    /**
     * Verify exception obj exc no exception thrown.
     */
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

    /**
     * Verify exception obj exc actual class thrown.
     */
    @Test
    void verifyExceptionObjExcActualClassThrown() {

        // test for actual class
        verifyException(() -> list.get(0), IndexOutOfBoundsException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    /**
     * Verify exception obj exc sub class of expected thrown.
     */
    @Test
    void verifyExceptionObjExcSubClassOfExpectedThrown() {

        // test for super class
        verifyException(() -> list.get(0), RuntimeException.class);
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    /**
     * Verify exception obj exc super class of expected thrown.
     */
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

    /**
     * Verify exception obj exc other class than expected thrown.
     */
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

    /**
     * Verify exception obj exc missing argument exception.
     */
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

    /**
     * Verify exception obj exc missing argument object.
     */
    @Test
    void verifyExceptionObjExcMissingArgumentObject() {

        try {
            verifyException(null, IllegalArgumentException.class);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    /**
     * Verify exception obj no exception thrown.
     */
    @Test
    void verifyExceptionObjNoExceptionThrown() {

        List<String> myList = new ArrayList<>();

        // no exception thrown by size()
        try {
            verifyException(myList::size);
            fail("ExceptionNotThrownAssertionError is expected");
        } catch (ExceptionNotThrownAssertionError e) {
            assertNull(caughtException());
            assertEquals("Exception expected but not thrown", e.getMessage());
        }
    }

    /**
     * Verify exception obj exception thrown.
     */
    @Test
    void verifyExceptionObjExceptionThrown() {

        List<String> myList = new ArrayList<>();

        verifyException(() -> myList.get(0));
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    /**
     * Verify exception obj missing argument object.
     */
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

    /**
     * Catch exception obj no exception thrown.
     */
    @Test
    void catchExceptionObjNoExceptionThrown() {

        List<String> myList = new ArrayList<>();

        // no exception thrown by size()
        catchException(myList::size);
        assertNull(caughtException());
    }

    /**
     * Catch exception obj exception thrown.
     */
    @Test
    void catchExceptionObjExceptionThrown() {

        List<String> myList = new ArrayList<>();

        catchException(() -> myList.get(0));
        if (!expectedMessage.equals(caughtException().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtException().getMessage());
        }
    }

    /**
     * Catch exception obj missing argument object.
     */
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

    /**
     * Test protected.
     */
    @Test
    void testProtected() {
        var obj = new PublicSomethingImpl();
        catchException(obj::dooo);
        assertTrue(caughtException() instanceof MyException);
    }

}
