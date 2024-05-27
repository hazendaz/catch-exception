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
package com.googlecode.catchexception.apis;

import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static com.googlecode.catchexception.apis.MyExceptionCustomAssertions.caughtException;
import static com.googlecode.catchexception.apis.MyExceptionCustomAssertions.then;

import com.googlecode.catchexception.MyException;

import org.junit.jupiter.api.Test;

/**
 * Tests custom exception assertions.
 */
class BDDCustomCatchExceptionTest {

    /**
     * Custom exception.
     *
     * @throws Exception the exception
     */
    @Test
    void customException() throws Exception {
        when(this::throwMyException);
        then(caughtException()).hasErrorCode(500);
    }

    /**
     * Throw my exception.
     */
    private void throwMyException() {
        throw new MyException(500);
    }

}
