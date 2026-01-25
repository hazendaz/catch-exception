/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception;

/**
 * The Class PublicSomethingImpl.
 */
public class PublicSomethingImpl implements Something {

    @Override
    public void doNothing() {
        // Do nothing
    }

    @Override
    public void doThrow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void doThrowNoSuchMethodError() {
        throw new NoSuchMethodError("==testCatchException==");
    }

    @Override
    public void doThrowAssertionError() {
        throw new AssertionError(123);
    }

    /**
     * Dooo.
     */
    protected void dooo() {
        throw new MyException(404);
    }

}
