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
package com.googlecode.catchexception.throwable;

import static com.googlecode.catchexception.throwable.CatchThrowable.catchThrowable;
import static com.googlecode.catchexception.throwable.CatchThrowable.caughtThrowable;
import static com.googlecode.catchexception.throwable.CatchThrowable.verifyThrowable;
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
 * Tests {@link CatchThrowable}.
 */
class CatchThrowableTest {

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
        ThrowableHolder.set(new HttpRetryException("detail", 0));
    }

    /**
     * Catch exception obj exc no exception thrown.
     */
    @Test
    void catchExceptionObjExcNoExceptionThrown() {

        catchThrowable(list::size, IndexOutOfBoundsException.class);
        assertNull(caughtThrowable());
    }

    /**
     * Catch exception throw error.
     */
    @Test
    void catchExceptionThrowError() {

        catchThrowable(() -> {
            throw new Error("ddd");
        });
        assertEquals(Error.class, caughtThrowable().getClass());
    }

    /**
     * Catch exception obj exc actual class thrown.
     */
    @Test
    void catchExceptionObjExcActualClassThrown() {

        // test for actual class
        catchThrowable(() -> list.get(0), IndexOutOfBoundsException.class);
        if (!expectedMessage.equals(caughtThrowable().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtThrowable().getMessage());
        }
    }

    /**
     * Catch exception obj exc sub class of expected thrown.
     */
    @Test
    void catchExceptionObjExcSubClassOfExpectedThrown() {

        // test for super class
        catchThrowable(() -> list.get(0), RuntimeException.class);
        if (!expectedMessage.equals(caughtThrowable().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtThrowable().getMessage());
        }
    }

    /**
     * Catch exception obj exc super class of expected thrown.
     */
    @Test
    void catchExceptionObjExcSuperClassOfExpectedThrown() {

        try {
            catchThrowable(() -> list.get(0), ArrayIndexOutOfBoundsException.class);
            fail("IndexOutOfBoundsException is expected (shall not be caught)");
        } catch (IndexOutOfBoundsException e) {
            assertNull(caughtThrowable());
        }
    }

    /**
     * Catch exception obj exc other class than expected thrown.
     */
    @Test
    void catchExceptionObjExcOtherClassThanExpectedThrown() {

        try {
            catchThrowable(() -> list.get(0), IllegalArgumentException.class);
            fail("IndexOutOfBoundsException is expected (shall not be caught)");
        } catch (IndexOutOfBoundsException e) {
            assertNull(caughtThrowable());
        }
    }

    /**
     * Catch exception obj exc missing argument exception.
     */
    @Test
    void catchExceptionObjExcMissingArgumentException() {

        // test validation of the arguments
        try {
            catchThrowable(list::size, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("throwableClazz must not be null", e.getMessage());
        }
    }

    /**
     * Catch exception obj exc missing argument object.
     */
    @Test
    void catchExceptionObjExcMissingArgumentObject() {

        try {
            catchThrowable(null, IllegalArgumentException.class);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    /**
     * Testverify throwable obj exc no exception thrown.
     */
    @Test
    void testverifyThrowable_ObjExc_noExceptionThrown() {

        try {
            verifyThrowable(list::size, IndexOutOfBoundsException.class);
            fail("ThrowableNotThrownAssertionError is expected");
        } catch (ThrowableNotThrownAssertionError e) {
            assertNull(caughtThrowable());
            assertEquals("Neither a throwable of type " + IndexOutOfBoundsException.class.getName()
                    + " nor another throwable was thrown", e.getMessage());
        }

    }

    /**
     * Testverify throwable obj exc actual class thrown.
     */
    @Test
    void testverifyThrowable_ObjExc_actualClassThrown() {

        // test for actual class
        verifyThrowable(() -> list.get(0), IndexOutOfBoundsException.class);
        if (!expectedMessage.equals(caughtThrowable().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtThrowable().getMessage());
        }
    }

    /**
     * Testverify throwable obj exc sub class of expected thrown.
     */
    @Test
    void testverifyThrowable_ObjExc_subClassOfExpectedThrown() {

        // test for super class
        verifyThrowable(() -> list.get(0), RuntimeException.class);
        if (!expectedMessage.equals(caughtThrowable().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtThrowable().getMessage());
        }
    }

    /**
     * Testverify throwable obj exc super class of expected thrown.
     */
    @Test
    void testverifyThrowable_ObjExc_superClassOfExpectedThrown() {

        // test for sub class
        try {
            verifyThrowable(() -> list.get(0), ArrayIndexOutOfBoundsException.class);
            fail("ThrowableNotThrownAssertionError is expected");
        } catch (ThrowableNotThrownAssertionError e) {
            assertNull(caughtThrowable());
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertEquals("Throwable of type " + ArrayIndexOutOfBoundsException.class.getName()
                        + " expected but was not thrown." + " Instead a throwable of type "
                        + IndexOutOfBoundsException.class + " with message '" + expectedMessage + "' was thrown.",
                        e.getMessage());
            }
        }
    }

    /**
     * Testverify throwable obj exc other class than expected thrown.
     */
    @Test
    void testverifyThrowable_ObjExc_otherClassThanExpectedThrown() {

        // test for other exception type
        try {
            verifyThrowable(() -> list.get(0), IllegalArgumentException.class);
            fail("ThrowableNotThrownAssertionError is expected");
        } catch (ThrowableNotThrownAssertionError e) {
            assertNull(caughtThrowable());
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertEquals("Throwable of type " + IllegalArgumentException.class.getName()
                        + " expected but was not thrown." + " Instead a throwable of type "
                        + IndexOutOfBoundsException.class + " with message '" + expectedMessage + "' was thrown.",
                        e.getMessage());
            }
        }

    }

    /**
     * Testverify throwable obj exc missing argument exception.
     */
    @Test
    void testverifyThrowable_ObjExc_missingArgument_Exception() {

        // test validation of the arguments
        try {
            verifyThrowable(list::size, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("throwableClazz must not be null", e.getMessage());
        }
    }

    /**
     * Testverify throwable obj exc missing argument object.
     */
    @Test
    void testverifyThrowable_ObjExc_missingArgument_Object() {

        try {
            verifyThrowable(null, IllegalArgumentException.class);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals("obj must not be null", e.getMessage());
        }
    }

    /**
     * Testverify throwable obj no exception thrown.
     */
    @Test
    void testverifyThrowable_Obj_noExceptionThrown() {

        List<String> myList = new ArrayList<>();

        // no exception thrown by size()
        try {
            verifyThrowable(myList::size);
            fail("ThrowableNotThrownAssertionError is expected");
        } catch (ThrowableNotThrownAssertionError e) {
            assertNull(caughtThrowable());
            assertEquals("Throwable expected but not thrown", e.getMessage());
        }
    }

    /**
     * Testverify throwable obj exception thrown.
     */
    @Test
    void testverifyThrowable_Obj_exceptionThrown() {

        List<String> myList = new ArrayList<>();

        verifyThrowable(() -> myList.get(0));
        if (!expectedMessage.equals(caughtThrowable().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtThrowable().getMessage());
        }
    }

    /**
     * Testverify throwable obj missing argument object.
     */
    @Test
    void testverifyThrowable_Obj_missingArgument_Object() {

        // test validation of the arguments
        try {
            verifyThrowable(null);
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
        catchThrowable(myList::size);
        assertNull(caughtThrowable());
    }

    /**
     * Catch exception obj exception thrown.
     */
    @Test
    void catchExceptionObjExceptionThrown() {

        List<String> myList = new ArrayList<>();

        catchThrowable(() -> myList.get(0));
        if (!expectedMessage.equals(caughtThrowable().getMessage())) {
            assertEquals(expectedMessageJdk9on, caughtThrowable().getMessage());
        }
    }

    /**
     * Catch exception obj missing argument object.
     */
    @Test
    void catchExceptionObjMissingArgumentObject() {

        // test validation of the arguments
        try {
            catchThrowable(null);
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
        catchThrowable(obj::dooo);
        assertTrue(caughtThrowable() instanceof MyThrowable);
    }

}
