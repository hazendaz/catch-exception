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
package com.googlecode.catchexception.throwable.test.apis;

import static com.googlecode.catchexception.throwable.apis.BDDCatchThrowable.when;
import static com.googlecode.catchexception.throwable.test.apis.MyThrowableCustomAssertions.caughtThrowable;
import static com.googlecode.catchexception.throwable.test.apis.MyThrowableCustomAssertions.then;

import com.googlecode.catchexception.throwable.MyThrowable;

import org.junit.jupiter.api.Test;

/**
 * Tests custom throwable assertions.
 */
class BDDCustomCatchThrowableTest {

    /**
     * Custom exception.
     *
     * @throws Exception the exception
     */
    @Test
    void customException() throws Exception {

        when(this::throwMyThrowable);

        then(caughtThrowable()).hasErrorCode(500);
    }

    /**
     * Throw my throwable.
     */
    private void throwMyThrowable() {
        throw new MyThrowable(500);
    }
}
