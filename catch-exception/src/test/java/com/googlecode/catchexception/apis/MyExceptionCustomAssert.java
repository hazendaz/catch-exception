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

import com.googlecode.catchexception.MyException;

import org.assertj.core.api.AbstractThrowableAssert;

/**
 * The Class MyExceptionCustomAssert.
 */
public class MyExceptionCustomAssert extends AbstractThrowableAssert<MyExceptionCustomAssert, MyException> {

    /**
     * Instantiates a new my exception custom assert.
     *
     * @param actual
     *            the actual
     */
    protected MyExceptionCustomAssert(MyException actual) {
        super(actual, MyExceptionCustomAssert.class);
    }

    /**
     * Checks for error code.
     *
     * @param errorCode
     *            the error code
     *
     * @return the my exception custom assert
     */
    public MyExceptionCustomAssert hasErrorCode(int errorCode) {
        isNotNull();
        if (actual.getErrorCode() != errorCode) {
            failWithMessage("Expected myException's errorCode to be <%s> but was <%s>", errorCode,
                    actual.getErrorCode());
        }
        return this;
    }

}
