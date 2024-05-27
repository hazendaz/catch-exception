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
package com.googlecode.catchexception.test.apis;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasMessage;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasMessageThat;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasNoCause;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers;
import com.googlecode.catchexception.matcher.Find;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CatchExceptionHamcrestMatchers}.
 */
class CatchExceptionHamcrestMatchersTest {

    /**
     * The message of the exception thrown by {@code new ArrayList<String>().get(0) } for jdk9on.
     */
    private final String expectedMessageJdk9on = "Index 9 out of bounds for length 9";

    /**
     * Setup.
     */
    @BeforeEach
    void setup() {
        List<String> fellowshipOfTheRing = new ArrayList<>();
        // let's do some team building :)
        fellowshipOfTheRing.add("frodo");
        fellowshipOfTheRing.add("sam");
        fellowshipOfTheRing.add("merry");
        fellowshipOfTheRing.add("pippin");
        fellowshipOfTheRing.add("gandalf");
        fellowshipOfTheRing.add("legolas");
        fellowshipOfTheRing.add("gimli");
        fellowshipOfTheRing.add("aragorn");
        fellowshipOfTheRing.add("boromir");

        catchException(() -> fellowshipOfTheRing.get(9));
    }

    /**
     * Assert message.
     *
     * @param foundMessage
     *            the found message
     * @param expectedExpectedPart
     *            the expected expected part
     * @param expectedGotPart
     *            the expected got part
     */
    private void assertMessage(String foundMessage, String expectedExpectedPart, String expectedGotPart) {

        var foundParts = foundMessage.split("(?=but:)");
        assertEquals(2, foundParts.length, "split of foundMessage did not work: " + foundMessage);
        var foundExpectedPart = foundParts[0].trim();
        var foundGotPart = foundParts[1].trim();
        assertEquals(expectedExpectedPart, foundExpectedPart);
        assertEquals(expectedGotPart, foundGotPart);
    }

    /**
     * Matcher instance of.
     */
    @Test
    void matcherInstanceOf() {

        assertThat(caughtException(), instanceOf(IndexOutOfBoundsException.class));

        assertThat(caughtException(), isA(Exception.class));

        try {
            assertThat(caughtException(), instanceOf(IllegalArgumentException.class));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), "Expected: an instance of java.lang.IllegalArgumentException",
                        "but: <java.lang.IndexOutOfBoundsException: Index: 9, Size: 9> is a java.lang.IndexOutOfBoundsException");
            }
        }
    }

    /**
     * Contains pattern.
     *
     * @param regex
     *            the regex
     *
     * @return the org.hamcrest. matcher
     */
    private static org.hamcrest.Matcher<String> containsPattern(String regex) {
        return new Find(regex);
    }

    /**
     * Learningtest matcher has message find regex.
     */
    @Test
    void learningtestMatcher_hasMessage_findRegex() {

        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtException(), hasMessageThat(containsPattern("Index: \\d+")));
        }

        try {
            assertThat(caughtException(), hasMessageThat(containsPattern("Index : \\d+")));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            // OK
        }
    }

    /**
     * Matcher has message equal by string.
     */
    @Test
    void matcherHasMessageEqualByString() {

        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtException(), hasMessage("Index: 9, Size: 9"));
        }

        try {
            assertThat(caughtException(), hasMessage("something went wrong"));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), "Expected: has a message that is \"something went wrong\"",
                        "but: was <java.lang.IndexOutOfBoundsException: Index: 9, Size: 9>");
            }
        }
    }

    /**
     * Matcher has message equal by string matcher.
     */
    @Test
    void matcherHasMessageEqualByStringMatcher() {

        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtException(), hasMessageThat(is("Index: 9, Size: 9")));
        }

        try {
            assertThat(caughtException(), hasMessageThat(is("something went wrong")));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), "Expected: has a message that is \"something went wrong\"",
                        "but: was <java.lang.IndexOutOfBoundsException: Index: 9, Size: 9>");
            }
        }
    }

    /**
     * Matcher has message contains by string matcher.
     */
    @Test
    void matcherHasMessageContainsByStringMatcher() {

        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtException(), hasMessageThat(is(containsString("Index: 9"))));
        }

        try {
            assertThat(caughtException(), hasMessageThat(is(containsString("Index: 8"))));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), "Expected: has a message that is a string containing \"Index: 8\"",
                        "but: was <java.lang.IndexOutOfBoundsException: Index: 9, Size: 9>");
            }
        }
    }

    /**
     * Matcher has no cause.
     */
    @Test
    void matcherHasNoCause() {

        assertThat(caughtException(), hasNoCause());

        try {
            assertThat(new RuntimeException(caughtException()), hasNoCause());
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), //
                        "Expected: has no cause", "but: was <java.lang.RuntimeException: "
                                + "java.lang.IndexOutOfBoundsException:" + " Index: 9, Size: 9>");
            }
        }
    }

    /**
     * Matcher all of.
     */
    @Test
    void matcherAllOf() {

        if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtException(), allOf( //
                    instanceOf(IndexOutOfBoundsException.class), //
                    hasMessage("Index: 9, Size: 9"), //
                    hasNoCause() //
            ));
        }

        try {
            assertThat(caughtException(), allOf( //
                    instanceOf(IndexOutOfBoundsException.class), //
                    hasMessage("something went wrong"), //
                    hasNoCause() //
            ));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!caughtException().getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), "Expected: " //
                        + "(an instance of java.lang.IndexOutOfBoundsException" //
                        + " and has a message that is \"something went wrong\"" //
                        + " and has no cause)",
                        "but: has a message that is \"something went wrong\" was <java.lang.IndexOutOfBoundsException: Index: 9, Size: 9>");
            }
        }

    }

}
