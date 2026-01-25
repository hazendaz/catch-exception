/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception.throwable;

/**
 * The Interface Something.
 */
public interface Something {

    /**
     * Do nothing.
     */
    void doNothing();

    /**
     * Do throw.
     */
    void doThrow();

    /**
     * Do throw no such method error.
     */
    void doThrowNoSuchMethodError();

    /**
     * Do throw assertion error.
     */
    void doThrowAssertionError();

}
