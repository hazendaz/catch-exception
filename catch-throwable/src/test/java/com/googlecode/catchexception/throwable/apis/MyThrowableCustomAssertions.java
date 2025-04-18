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
package com.googlecode.catchexception.throwable.apis;

import com.googlecode.catchexception.throwable.MyThrowable;

/**
 * The Class MyThrowableCustomAssertions.
 */
public class MyThrowableCustomAssertions extends BDDCatchThrowable {

    /**
     * Then.
     *
     * @param actual
     *            the actual
     *
     * @return the my throwable custom assert
     */
    public static MyThrowableCustomAssert then(MyThrowable actual) {
        return new MyThrowableCustomAssert(actual);
    }

    /**
     * Caught throwable.
     *
     * @return the my throwable
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public static MyThrowable caughtThrowable() {
        return caughtThrowable(MyThrowable.class);
    }

}
