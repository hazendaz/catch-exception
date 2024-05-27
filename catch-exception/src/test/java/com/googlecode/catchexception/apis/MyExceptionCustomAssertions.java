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

/**
 * The Class MyExceptionCustomAssertions.
 */
public class MyExceptionCustomAssertions extends BDDCatchException {

    /**
     * Then.
     *
     * @param actual
     *            the actual
     *
     * @return the my exception custom assert
     */
    public static MyExceptionCustomAssert then(MyException actual) {
        return new MyExceptionCustomAssert(actual);
    }

    /**
     * Caught exception.
     *
     * @return the my exception
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public static MyException caughtException() {
        return caughtException(MyException.class);
    }

}
