/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2011-2026 the original author or authors
 */
package com.googlecode.catchexception;

/**
 * The Interface ThrowingCallable.
 */
@FunctionalInterface
public interface ThrowingCallable {

    /**
     * Call.
     *
     * @throws Exception
     *             the exception
     */
    void call() throws Exception;

}
