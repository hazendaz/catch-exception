/**
 * Copyright (C) 2011 rwoo@gmx.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.catchexception.apis;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.CompatibilityAssertions;

/**
 *
 * @author rwoo
 * @since 1.1.0
 * @see com.googlecode.catchexception.apis.BDDCatchException
 * @deprecated As of release 1.3.0, replaced by {@link com.googlecode.catchexception.apis.BDDCatchException()}
 */
@Deprecated
public class CatchExceptionAssertJ extends BDDCatchException {

    /**
     * Enables <a
     * href="https://github.com/joel-costigliola/assertj-core">AssertJ</a>
     * assertions about the caught exception.
     * <p>
     * EXAMPLE:
     * <code><pre class="prettyprint lang-java">// given an empty list
     List myList = new ArrayList();

     // when we try to get first element of the list
     when(myList).get(1);

     // then we expect an IndexOutOfBoundsException
     then(caughtException())
     .isInstanceOf(IndexOutOfBoundsException.class)
     .hasMessage("Index: 1, Size: 0")
     .hasMessageStartingWith("Index: 1")
     .hasMessageEndingWith("Size: 0")
     .hasMessageContaining("Size")
     .hasNoCause();
     </pre></code>
     *
     * @param actualException
     *            the value to be the target of the assertions methods.
     * @return Returns the created assertion object.
     * @see org.assertj.core.api.CompatibilityAssertions#assertThat(Throwable)
     * @deprecated As of release 1.3.0, replaced by {@link org.assertj.core.api.BDDAssertions#then(java.lang.Throwable}
     */
    public static AbstractThrowableAssert<?, ? extends Throwable> then(Exception actualException) {
        // delegate to AssertJ assertions
        return CompatibilityAssertions.assertThat(actualException);
    }


}
