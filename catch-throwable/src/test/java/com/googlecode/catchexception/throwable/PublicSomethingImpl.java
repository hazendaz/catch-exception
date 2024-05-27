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

/**
 * The Class PublicSomethingImpl.
 */
public class PublicSomethingImpl implements Something {

    @Override
    public void doNothing() {
        // Do Nothing
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
        throw new MyThrowable(500);
    }

}
