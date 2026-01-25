/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable.apis;

import static com.googlecode.catchexception.throwable.CatchThrowable.catchThrowable;
import static com.googlecode.catchexception.throwable.CatchThrowable.caughtThrowable;
import static com.googlecode.catchexception.throwable.apis.CatchThrowableHamcrestMatchers.hasMessage;
import static com.googlecode.catchexception.throwable.apis.CatchThrowableHamcrestMatchers.hasMessageThat;
import static com.googlecode.catchexception.throwable.apis.CatchThrowableHamcrestMatchers.hasNoCause;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.googlecode.catchexception.throwable.matcher.Find;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CatchThrowableHamcrestMatchers}.
 */
class CatchThrowableHamcrestMatchersTest {

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

        catchThrowable(() -> fellowshipOfTheRing.get(9));
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

        assertThat(caughtThrowable(), instanceOf(IndexOutOfBoundsException.class));

        assertThat(caughtThrowable(), isA(Throwable.class));

        try {
            assertThat(caughtThrowable(), instanceOf(IllegalArgumentException.class));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!e.getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), "Expected: an instance of java.lang.IllegalArgumentException",
                        "but: <java.lang.IndexOutOfBoundsException: Index: 9, Size: 9> is a java.lang"
                                + ".IndexOutOfBoundsException");
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

        if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtThrowable(), hasMessageThat(containsPattern("Index: \\d+")));
        }

        try {
            assertThat(caughtThrowable(), hasMessageThat(containsPattern("Index : \\d+")));
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

        if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtThrowable(), hasMessage("Index: 9, Size: 9"));
        }

        try {
            assertThat(caughtThrowable(), hasMessage("something went wrong"));
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

        if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtThrowable(), hasMessageThat(is("Index: 9, Size: 9")));
        }

        try {
            assertThat(caughtThrowable(), hasMessageThat(is("something went wrong")));
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

        if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtThrowable(), hasMessageThat(is(containsString("Index: 9"))));
        }

        try {
            assertThat(caughtThrowable(), hasMessageThat(is(containsString("Index: 8"))));
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

        assertThat(caughtThrowable(), hasNoCause());

        try {
            assertThat(new RuntimeException(caughtThrowable()), hasNoCause());
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

        if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
            assertThat(caughtThrowable(), allOf( //
                    instanceOf(IndexOutOfBoundsException.class), //
                    hasMessage("Index: 9, Size: 9"), //
                    hasNoCause() //
            ));
        }

        try {
            assertThat(caughtThrowable(), allOf( //
                    instanceOf(IndexOutOfBoundsException.class), //
                    hasMessage("something went wrong"), //
                    hasNoCause() //
            ));
            throw new RuntimeException("AssertionError expected");
        } catch (AssertionError e) {
            if (!caughtThrowable().getMessage().contains(expectedMessageJdk9on)) {
                assertMessage(e.getMessage(), "Expected: " //
                        + "(an instance of java.lang.IndexOutOfBoundsException" //
                        + " and has a message that is \"something went wrong\"" //
                        + " and has no cause)",
                        "but: has a message that is \"something went wrong\" was <java.lang.IndexOutOfBoundsException: "
                                + "Index: 9, Size: 9>");
            }
        }

    }

}
